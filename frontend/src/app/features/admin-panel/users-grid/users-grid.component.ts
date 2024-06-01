import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { UsersService } from 'src/app/services/users/users.service';
import { UserModel } from 'src/app/typings/models/user.model';
import { UserFormPopupComponent } from './user-form-popup/user-form-popup.component';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';

@Component({
  selector: 'users-grid',
  standalone: true,
  imports: [
    CommonModule,
    UserFormPopupComponent,
    TableModule,
    ButtonModule
  ],
  providers: [
    UsersService
  ],
  styleUrls: ['./users-grid.component.scss'],

  template: `
    <section class="container-fluid">

      <div *ngIf="userDetailsService.userIsAdmin()" class="row justify-content-end mb-3">
        <p-button class="w-auto" label="Cadastrar usuário" (onClick)="showFormPopup = true" />
      </div>

      <user-form-popup class="col-11 col-sm-10 col-md-8 col-lg-6 col-xxl-4" *ngIf="showFormPopup" [(showFormPopup)]="showFormPopup" />

      <div class="row">
        <p-table
          [value]="usersData"
          [lazy]="true"
          (onLazyLoad)="getUsersPaginated($event)"
          [showCurrentPageReport]="true"
          [paginator]="true"
          [loading]="showGridLoading"
          [rows]="10"
          [totalRecords]="totalUsers"
          [rowsPerPageOptions]="[5, 10, 15]"
          [rowHover]="true"
          class="px-0 shadow">

          <ng-template pTemplate="header">
            <tr>
              <th class="bg-dark-subtle">Nome</th>
              <th class="bg-dark-subtle">E-mail</th>
              <th class="bg-dark-subtle">Permissionamento</th>
              <th class="bg-dark-subtle">Status</th>
            </tr>
          </ng-template>

          <ng-template pTemplate="body" let-user>
            <tr class="border-top border-secondary-subtle cursor-pointer" (click)="openDetails(user.id)">
              <td>{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.role.name }}</td>
              <td>
                <div class="badge rounded-pill p-2" [ngClass]="{'bg-success': user.active, 'bg-danger': !user.active}">
                  {{ user.active ? 'Ativo' : 'Inativo' }}
                </div>
              </td>
            </tr>
          </ng-template>
        </p-table>
      </div>
    </section>
  `
})
export class UsersGridComponent implements OnInit {

  usersService: UsersService = inject(UsersService);
  router: Router = inject(Router);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  userDetailsService: UserDetailsService = inject(UserDetailsService);


  usersData!: Array<UserModel>;
  totalUsers!: number;
  showGridLoading: boolean = false;

  showFormPopup: boolean = false;

  constructor(){}

  ngOnInit(): void {
    const userId = this.activatedRoute.snapshot.queryParams["id"];

    if(userId){
      this.showFormPopup = true;
    }
  }

  getUsersPaginated(tableEvent: TableLazyLoadEvent){
    this.showGridLoading = true;

    const page: number = tableEvent.first! / tableEvent.rows!;
    const size: number = tableEvent.rows!

    this.usersService.getUsersPaginated(page, size).subscribe({
      next: (response) => {
        this.usersData = response.content;
        this.totalUsers = response.totalElements

        this.showGridLoading = false;
      }
    })
  }

  openDetails(userId: string){
    this.router.navigate([], {queryParams: {id: userId}, queryParamsHandling: "merge"});
    this.showFormPopup = true;
  }

}
