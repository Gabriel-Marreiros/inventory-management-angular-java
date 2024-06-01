import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IconDefinition, faDolly as cartIcon, faUsers as usersIcon, faLayerGroup as categoriesIcon } from '@fortawesome/free-solid-svg-icons';
import { ProductsService } from 'src/app/services/products/products.service';

enum ViewsEnum {
  PRODUCTS = "products",
  USERS = "users",
  CATEGORIES = "categories"
}

@Component({
  selector: 'app-admin-panel',
  styleUrls: ['./admin-panel.component.scss'],

  template: `
    <section class="container-fluid min-vh-100 p-4 p-xxl-5">

      <div class="row justify-content-center gap-4 gap-xl-5">

        <div class="col-12 col-xl-3">
          <div class="shadow-sm">
            <nav>
              <ul class="list-group">
                <li class="list-group-item" [routerLinkActive]="['bg-teste', 'text-white']">
                  <a class="d-block w-100 fs-5" [routerLink]="['./']" [queryParams]="{view: 'products'}">Produtos</a>
                </li>
                <li class="list-group-item" [routerLinkActive]="['bg-teste', 'text-white']">
                  <a class="d-block w-100 fs-5" [routerLink]="['./']" [queryParams]="{view: 'users'}">Usuários</a>
                </li>
                <li class="list-group-item" [routerLinkActive]="['bg-teste', 'text-white']">
                  <a class="d-block w-100 fs-5" [routerLink]="['./']" [queryParams]="{view: 'categories'}">Categorias</a>
                </li>
                <li class="list-group-item">
                  <a class="d-block w-100 fs-5" [routerLink]="['/reports']">Relatórios</a>
                </li>
              </ul>
            </nav>
          </div>
        </div>

        <div class="col-12 col-xl-8 col-xxl-7">

          <div class="row mb-5 justify-content-between gap-2 gap-md-0">

            <admin-panel-card class="col-12 col-md-4" title="Produtos Ativos" [count]="activeProducts" [icon]="cartIcon"></admin-panel-card>

            <admin-panel-card class="col-12 col-md-4" title="Usuários Ativos" [count]="activeUsers" [icon]="usersIcon"></admin-panel-card>

            <admin-panel-card class="col-12 col-md-4" title="Categorias Ativas" [count]="activeCategories" [icon]="categoriesIcon"></admin-panel-card>

          </div>

          <div class="row">
            <ng-container [ngSwitch]="view">
              <users-grid *ngSwitchCase="'users'" />

              <categories-grid *ngSwitchCase="'categories'" />

              <last-ten-registered-products-grid *ngSwitchCase="'products'" />
            </ng-container>
          </div>

        </div>
      </div>

    </section>
  `
})
export class AdminPanelComponent implements OnInit {

  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  productsService: ProductsService = inject(ProductsService);

  cartIcon: IconDefinition = cartIcon;
  usersIcon: IconDefinition = usersIcon;
  categoriesIcon: IconDefinition = categoriesIcon;

  view!: ViewsEnum;

  activeProducts!: number;
  activeCategories!: number;
  activeUsers!: number;

  constructor() { }

  ngOnInit(): void {
    this.getView();
    this.getSummaryProductsCategoriesUsersActive();
  }

  getView(): void {
    this.activatedRoute.queryParams.subscribe({
      next: (params) => {
        if(!params['view']){
          this.router.navigate([], {queryParams: {view: ViewsEnum.PRODUCTS}})
        }

        this.view = params['view'];
      }
    })
  }

  getSummaryProductsCategoriesUsersActive(): void {
    this.productsService.getSummaryProductsCategoriesUsersActive().subscribe({
      next: (response) => {
        this.activeProducts = response.activeProducts;
        this.activeCategories = response.activeCategories;
        this.activeUsers = response.activeUsers;
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
      }
    })
  }

}
