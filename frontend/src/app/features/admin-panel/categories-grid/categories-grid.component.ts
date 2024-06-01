import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { CategoriesService } from 'src/app/services/categories/categories.service';
import { CategoryModel } from 'src/app/typings/models/category.model';
import { CategoryFormPopupComponent } from './category-form-popup/category-form-popup.component';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';

@Component({
  selector: 'categories-grid',
  standalone: true,
  imports: [
    CommonModule,
    CategoryFormPopupComponent,
    TableModule,
    ButtonModule,
    RouterModule
  ],
  styleUrls: ['./categories-grid.component.scss'],

  template: `
    <section class="container-fluid">

      <div *ngIf="userDetailsService.userIsAdmin()" class="row justify-content-end mb-3">
        <p-button class="w-auto" label="Cadastrar categoria" (onClick)="showFormPopup = true" />
      </div>

      <category-form-popup class="col-11 col-sm-10 col-md-8 col-lg-6 col-xxl-4" *ngIf="showFormPopup" [(showFormPopup)]="showFormPopup" />

      <div class="row">
        <p-table
          [value]="categories"
          [paginator]="true"
          [lazy]="true"
          (onLazyLoad)="getCategoriesPaginated($event)"
          [rows]="10"
          [showCurrentPageReport]="true"
          [rowsPerPageOptions]="[10, 25, 50]"
          [loading]="showGridLoading"
          [rowHover]="true"
          [totalRecords]="totalCategories"
          class="px-0 shadow">

          <ng-template pTemplate="header">
            <tr>
              <th class="bg-dark-subtle" >Nome</th>
              <th class="bg-dark-subtle" >Status</th>
            </tr>
          </ng-template>

          <ng-template pTemplate="body" let-category>
            <tr class="border-top border-secondary-subtle cursor-pointer" (click)="openCategoryDetails(category.id)">
              <td>{{ category.name }}</td>
              <td>
                <div class="badge rounded-pill p-2" [ngClass]="{'bg-success': category.active, 'bg-danger': !category.active}">
                  {{ category.active ? 'Ativo' : 'Inativo' }}
                </div>
              </td>
            </tr>
          </ng-template>
        </p-table>
      </div>
    </section>

`
})
export class CategoriesGridComponent implements OnInit {

  categoriesService: CategoriesService = inject(CategoriesService);
  router: Router = inject(Router);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  userDetailsService: UserDetailsService = inject(UserDetailsService);

  categories!: Array<CategoryModel>;
  totalCategories!: number;

  showFormPopup: boolean = false;
  showGridLoading: boolean = false;

  constructor(){}

  ngOnInit(): void {
    const categoryId: boolean = !!this.activatedRoute.snapshot.queryParams["id"];
    if(categoryId) this.showFormPopup = true;
  }

  getCategoriesPaginated(tableEvent: TableLazyLoadEvent){
    this.showGridLoading = true;

    const page: number = tableEvent.first! / tableEvent.rows!;
    const size: number = tableEvent.rows!

    this.categoriesService.getCategoriesPaginated(page, size).subscribe({
      next: (response) => {
        this.categories = response.content;
        this.totalCategories = response.totalElements
        this.showGridLoading = false;
      }
    })
  }

  openCategoryDetails(categoryId: string): void {
    this.router.navigate([], {queryParams: {id: categoryId}, queryParamsHandling: 'merge'});
    this.showFormPopup = true;
  }


}
