import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild, inject } from '@angular/core';

import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RolesService } from 'src/app/services/roles/roles.service';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';
import { UsersService } from 'src/app/services/users/users.service';
import { LoadingModalComponent } from 'src/app/shared/components/loading-modal/loading-modal.component';
import { RoleModel } from 'src/app/typings/models/role.model';
import { UserModel } from 'src/app/typings/models/user.model';

@Component({
  selector: 'user-form-popup',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    PasswordModule,
    InputMaskModule,
    CalendarModule,
    DropdownModule,
    RouterModule,
    ConfirmDialogModule,
    LoadingModalComponent
  ],
  providers: [
    UsersService,
    RolesService,
    ConfirmationService,
    UserDetailsService
  ],
  styleUrls: ['./user-form-popup.component.scss'],

  template: `
    <p-dialog [(visible)]="showFormPopup" [modal]="true" [styleClass]="class" [draggable]="false" (onHide)="closePopup()">
      <div class="container-fluid bg-white">

        <div class="row mb-4">
          <h3 class="text-center">{{isDetailsForm ? "Detalhes do Usuário" : "Cadastrar Usuário"}}</h3>
        </div>

        <div class="row">
          <form class="d-flex flex-column gap-4" #userForm="ngForm">

            <div class="d-flex flex-column">
              <label for="nameInput" class="fw-bold">Nome</label>
              <input pInputText id="nameInput" [(ngModel)]="user.name" name="name" type="text" [required]="true" placeholder="Digite o nome do usuário"/>
            </div>

            <div class="d-flex flex-column">
              <label for="emailInput" class="fw-bold">Email</label>
              <input pInputText id="emailInput" [(ngModel)]="user.email" name="email" type="email" [email]="true" [required]="true" placeholder="Digite o e-mail do usuário"/>
            </div>

            <div class="d-flex flex-column" *ngIf="!isDetailsForm">
              <label for="passwordInput" class="fw-bold">Senha</label>
              <p-password inputId="passwordInput" [(ngModel)]="user.password" name="password" [toggleMask]="true" [required]="true" placeholder="Digite a senha do usuário"/>
            </div>

            <div class="d-flex flex-column">
              <label for="phoneInput" class="fw-bold">Telefone</label>
              <p-inputMask mask="(99) 99999-9999" inputId="phoneInput" [(ngModel)]="user.phoneNumber" name="phoneNumber" placeholder="(99) 99999-9999" [required]="true" placeholder="Digite o telefone do usuário"/>
            </div>

            <div class="d-flex flex-column">
              <label for="dateBirthInput" class="fw-bold">Data de Nascimento</label>
              <p-calendar inputId="dateBirthInput" dateFormat="dd/mm/yy" [maxDate]="maxDateBirth" [defaultDate]="maxDateBirth" [(ngModel)]="user.dateBirth" name="dateBirth" [showIcon]="true" [required]="true" placeholder="Digite a data de nascimento do usuário"/>
            </div>

            <div class="d-flex flex-column">
              <label for="roleInput" class="fw-bold">Permissionamento</label>
              <p-dropdown inputId="roleInput" [options]="roleOptions" [(ngModel)]="user.role" name="role" optionLabel="name" optionValue="id" [required]="true" placeholder="Selecione o permissionamento do usuário"/>
            </div>

            <div class="d-flex align-items-center justify-content-end gap-2">
              <p-button *ngIf="!isDetailsForm && userIsAdmin()" [disabled]="userForm.invalid!" styleClass="bg-teste" label="Cadastrar" (onClick)="openSaveUserConfirmModal()"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && user.active && (userIsAdmin() || isUser())" styleClass="bg-teste" label="Editar" (onClick)="enableEditing()"></p-button>
              <p-button *ngIf="isDetailsForm && isEditing && user.active && (userIsAdmin() || isUser())" styleClass="bg-teste" label="Salvar Modificações" (onClick)="openUpdateUserConfirmModal()"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && user.active && userIsAdmin()" styleClass="bg-teste" label="Inativar Usuário(a)" (onClick)="openChangeActiveStatusConfirmModal(false)"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && !user.active && userIsAdmin()" styleClass="bg-teste" label="Ativar Usuário(a)" (onClick)="openChangeActiveStatusConfirmModal(true)"></p-button>
            </div>

          </form>
        </div>
      </div>

      <p-confirmDialog
        key="confirmModal"
        acceptLabel="Sim"
        acceptButtonStyleClass="btn btn-success"
        rejectLabel="Não"
        rejectButtonStyleClass="btn btn-danger me-3"
        styleClass="col-md-10">
      </p-confirmDialog>

      <p-confirmDialog
        key="genericModal"
        [closable]="false"
        acceptLabel="Ok"
        acceptButtonStyleClass="btn btn-primary"
        rejectButtonStyleClass="d-none"
        styleClass="col-md-10">
      </p-confirmDialog>

      <loading-modal styleClass="col-md-10" [showModal]="showLoadingModal" />

    </p-dialog>
  `
})
export class UserFormPopupComponent implements OnInit {

  @Input({required: true})
  showFormPopup!: boolean;
  @Output()
  showFormPopupChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Input()
  class!: string;

  usersService: UsersService = inject(UsersService);
  rolesService: RolesService = inject(RolesService);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  userDetailsService: UserDetailsService = inject(UserDetailsService);

  roleOptions!: Array<RoleModel>;
  maxDateBirth: Date = new Date(new Date().setFullYear(new Date().getFullYear() - 18));

  isDetailsForm: boolean = false;
  isEditing: boolean = false;
  showLoadingModal: boolean = false;

  user: UserModel = {
    id: "",
    name: "",
    email: "",
    password: "",
    phoneNumber: "",
    dateBirth: this.maxDateBirth,
    role: "",
    active: true
  };

  @ViewChild("userForm", {static: true})
  userForm!: NgForm;

  constructor(){}

  ngOnInit(): void {
    this.getRoleOptions();

    const userId = this.activatedRoute.snapshot.queryParams["id"];

    if(userId){
      this.getUserDetails(userId);
      this.isDetailsForm = true;
    }

  }

  getRoleOptions(){
    this.rolesService.getAllRoles().subscribe({
      next: (response) => {
        this.roleOptions = response;
      }
    })
  }

  saveUser(){
    this.showLoadingModal = true;

    this.usersService.saveUser(this.user).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal("Usuário cadastrado com sucesso!");
      }
    })
  }
  openSaveUserConfirmModal(){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Cadastrar Usuário",
      message: "Tem certeza que deseja cadastrar esse usuário?",
      accept: () => this.saveUser(),
    })
  }

  updateUser(){
    this.showLoadingModal = true;

    this.usersService.updateUser(this.user).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal("Usuário atualizado com sucesso!");
      }
    })
  }

  openUpdateUserConfirmModal(){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Usuário",
      message: "Tem certeza que deseja atualizar esse usuário?",
      accept: () => this.updateUser(),
    })
  }

  changeUserActiveStatus(newActiveStatus: boolean): void {
    if(!this.isDetailsForm) return;

    this.showLoadingModal = true;

    this.usersService.changeUserActiveStatus(this.user.id!, newActiveStatus).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal(`Usuário(a) ${newActiveStatus == true ? 'ativado(a)' : 'inativado(a)'} com sucesso!`);
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
        this.showLoadingModal = false;
        this.showGenericModal(`Ocorreu um erro ao ${newActiveStatus == true ? 'ativar' : 'inativar'} o usuário! Por favor, entre em contato com um técnico ou tente novamente mais tarde.`, 'Erro');
      }
    })

  }

  openChangeActiveStatusConfirmModal(newActiveStatus: boolean){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Categoria",
      message: `Tem certeza que deseja ${newActiveStatus == true ? 'ativar' : 'inativar'} esse usuário(a)?`,
      accept: () => this.changeUserActiveStatus(newActiveStatus),
    })
  }

  getUserDetails(userId: string){
    this.usersService.getUserById(userId).subscribe({
      next: (response: UserModel) => {
        this.user = response;
        this.user.role = (response.role as RoleModel).id;
        this.user.dateBirth = new Date(response.dateBirth as string);
        this.userForm.form.disable();
      }
    })
  }

  enableEditing(){
    this.userForm.form.enable();
    this.isEditing = true;
  }

  closePopup(): void {
    this.router.navigate([], {queryParams: {id: null}, queryParamsHandling: 'merge'}).then(() => window.location.reload())
    this.showFormPopup = false;
    this.showFormPopupChange.emit(this.showFormPopup);
  }

  showGenericModal(message: string, header: string = "Inventory Management"){
    this.confirmationService.confirm({
      key: "genericModal",
      header: header,
      message: message,
      accept: () => this.closePopup(),
    })
  }

  userIsAdmin(): boolean {
    return this.userDetailsService.userIsAdmin();
  }

  isUser(): boolean {
    return this.user.id == this.userDetailsService.getUserId();
  }

}
