import { Component, EventEmitter, Input, OnInit, Output, ViewChild, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule, NgForm } from '@angular/forms';
import { CategoriesService } from 'src/app/services/categories/categories.service';
import { CategoryModel } from 'src/app/typings/models/category.model';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { LoadingModalComponent } from 'src/app/shared/components/loading-modal/loading-modal.component';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';

@Component({
  selector: 'category-form-popup',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    DialogModule,
    InputTextModule,
    ButtonModule,
    RouterModule,
    ConfirmDialogModule,
    LoadingModalComponent
  ],
  providers: [
    CategoriesService,
    ConfirmationService,
    UserDetailsService
  ],
  styleUrls: [
    './category-form-popup.component.scss'
  ],

  template: `
    <p-dialog [(visible)]="showFormPopup" [modal]="true" [styleClass]="class" [draggable]="false" (onHide)="closePopup()">
      <div class="container-fluid bg-white">

        <div class="row mb-5">
          <h3 class="text-center">{{isDetailsForm ? "Detalhes da Categoria" : "Cadastrar Categoria"}}</h3>
        </div>

        <div class="row">
          <form class="d-flex flex-column gap-4" #categoryForm="ngForm">

            <div class="d-flex flex-column">
              <label for="nameInput" class="fw-bold">Nome da Categoria</label>
              <input id="nameInput" pInputText [(ngModel)]="category.name" name="name" required placeholder="Digite o nome da categoria."/>
            </div>

            <div class="d-flex align-items-center justify-content-end gap-2">
              <p-button *ngIf="!isDetailsForm" [disabled]="categoryForm.invalid!" styleClass="bg-teste" label="Cadastrar" (onClick)="openSaveCategoryConfirmModal()"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && category.active" styleClass="bg-teste" label="Editar" (onClick)="enableEditing()"></p-button>
              <p-button *ngIf="isDetailsForm && isEditing && category.active" styleClass="bg-teste" label="Salvar Modificações" (onClick)="openUpdateCategoryConfirmModal()"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && category.active && userIsAdmin()" styleClass="bg-teste" label="Inativar Categoria" (onClick)="openChangeActiveStatusConfirmModal(false)"></p-button>
              <p-button *ngIf="isDetailsForm && !isEditing && !category.active && userIsAdmin()" styleClass="bg-teste" label="Ativar Categoria" (onClick)="openChangeActiveStatusConfirmModal(true)"></p-button>
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
  `,
})
export class CategoryFormPopupComponent implements OnInit {

  @Input()
  showFormPopup!: boolean
  @Output()
  showFormPopupChange: EventEmitter<any> = new EventEmitter();

  @Input()
  class!: string

  categoriesService: CategoriesService = inject(CategoriesService);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router)
  confirmationService: ConfirmationService = inject(ConfirmationService);
  userDetailsService: UserDetailsService = inject(UserDetailsService);

  isDetailsForm: boolean = false;
  isEditing: boolean = false;
  showLoadingModal: boolean = false;

  @ViewChild("categoryForm", {static: true})
  categoryForm!: NgForm;

  category: CategoryModel = {
    id: '',
    name: '',
    active: true
  }

  constructor(){}

  ngOnInit(): void {
    const categoryId = this.activatedRoute.snapshot.queryParams["id"];

    if(categoryId){
      this.getCategoryDetails(categoryId);
      this.isDetailsForm = true;
    }
  }

  saveCategory(){
    this.showLoadingModal = true;

    this.categoriesService.saveCategory(this.category).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal("Categoria cadastrada com sucesso!");
      },

      error: (error: HttpErrorResponse) => {
        console.error(error);
      }
    })
  }

  openSaveCategoryConfirmModal(){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Cadastrar Categoria",
      message: "Tem certeza que deseja cadastrar essa categoria?",
      accept: () => this.saveCategory(),
    })
  }

  updateCategory(){
    this.showLoadingModal = true;

    this.categoriesService.updateCategory(this.category).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal("Categoria atualizada com sucesso!");
      }
    })
  }

  openUpdateCategoryConfirmModal(){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Categoria",
      message: "Tem certeza que deseja atualizar essa categoria?",
      accept: () => this.updateCategory(),
    })
  }

  changeCategoryActiveStatus(newActiveStatus: boolean): void {
    if(!this.isDetailsForm) return;

    this.showLoadingModal = true;

    this.categoriesService.changeCategoryActiveStatus(this.category.id!, newActiveStatus).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal(`Categoria ${newActiveStatus == true ? 'ativada' : 'inativada'} com sucesso!`);
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
        this.showLoadingModal = false;
        this.showGenericModal(`Ocorreu um erro ao ${newActiveStatus == true ? 'ativar' : 'inativar'} a categoria! Por favor, entre em contato com um técnico ou tente novamente mais tarde.`, 'Erro');
      }
    })

  }

  openChangeActiveStatusConfirmModal(newActiveStatus: boolean){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Categoria",
      message: `Tem certeza que deseja ${newActiveStatus == true ? 'ativar' : 'inativar'} essa categoria?`,
      accept: () => this.changeCategoryActiveStatus(newActiveStatus),
    })
  }

  getCategoryDetails(categoryId: string){
    this.categoriesService.getCategoryById(categoryId).subscribe({
      next: (response) => {
        this.category = response;
        this.categoryForm.form.disable();
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
      }
    })
  }

  enableEditing(){
    this.categoryForm.form.enable();
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
}
