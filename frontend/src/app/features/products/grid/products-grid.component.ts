import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Table, TableLazyLoadEvent } from 'primeng/table';
import { ProductsService } from 'src/app/services/products/products.service';
import { ProductModel } from 'src/app/typings/models/product.model';

import { IconDefinition, faMagnifyingGlass as searchIcon, faXmark as closeIcon } from '@fortawesome/free-solid-svg-icons';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-products-grid',
  styleUrls: ['./products-grid.component.scss'],

  template: `
    <section class="container-fluid min-vh-100 py-5">
    <!-- offset-8 col-3 -->
      <div class="row mb-4 px-2 px-md-3 px-lg-4 px-xl-5 px-xxl-0">
        <div class="col-12 col-md-7 offset-md-5 col-lg-6 offset-lg-6 col-xl-5 offset-xl-7 col-xxl-4 offset-xxl-7">
          <div class="d-flex align-items-center justify-content-center gap-2 border bg-white rounded py-3 px-3 shadow">
            <fa-icon [icon]="searchIcon" />
            <input class="w-100 border-0" type="text" [(ngModel)]="searchFilter" (keyup.enter)="handleSearchProduct()" placeholder="Pesquisar produto"/>
            <button class="btn" (click)="handleClearSearchFilter()">
              <fa-icon [icon]="closeIcon" />
            </button>
          </div>
        </div>
      </div>

      <div class="row justify-content-center">
        <div class="col-11 col-xxl-10 p-0 shadow">
          <p-table
            #table
            [value]="products"
            [lazy]="true"
            (onLazyLoad)="handlePageChange($event)"
            sortField="brand"
            [sortOrder]="1"
            dataKey="id"
            [paginator]="true"
            [rowsPerPageOptions]="[5, 10, 15]"
            [rows]="10"
            [totalRecords]="totalProducts"
            [rowHover]="true"
            [loading]="showLoading">

            <ng-template pTemplate="header">
              <tr class="">
                <th class="bg-dark-subtle" pSortableColumn="sku">SKU <p-sortIcon field="sku"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="brand">Marca <p-sortIcon field="brand"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="name">Nome <p-sortIcon field="name"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="category.name">Categoria <p-sortIcon field="category.name"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="price">Preço <p-sortIcon field="price"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="quantity">Quantidade <p-sortIcon field="quantity"></p-sortIcon></th>
                <th class="bg-dark-subtle" pSortableColumn="active">Status <p-sortIcon field="active"></p-sortIcon></th>
              </tr>
            </ng-template>

            <ng-template pTemplate="body" let-product>
              <tr class="border-top border-light-subtle cursor-pointer" (click)="openProductDetails(product.id)">
                <td>{{ product.sku }}</td>
                <td>{{ product.brand }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.category.name }}</td>
                <td>{{ product.price | currency:'BRL' }}</td>
                <td>{{ product.quantity }}</td>
                <td>
                  <div class="badge rounded-pill p-2" [ngClass]="{'bg-success': product.active, 'bg-danger': !product.active}">
                    {{ product.active ? 'Ativo' : 'Inativo' }}
                  </div>
                </td>
              </tr>
            </ng-template>
          </p-table>
        </div>
      </div>
    </section>
  `,
})
export class ProductsGridComponent implements OnInit {

  productsService: ProductsService = inject(ProductsService);
  router: Router = inject(Router);
  activatedRoute = inject(ActivatedRoute);

  searchFilter!: string;
  searchIcon: IconDefinition = searchIcon;
  closeIcon: IconDefinition = closeIcon;

  products!: Array<ProductModel>;
  totalProducts!: number;

  showLoading: boolean = false;

  @ViewChild('table', {static: true})
  table!: Table;

  constructor() {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(({ search }) => {
      if(search){
        this.searchFilter = search;
        this.getProductsBySearchFilter({rows: this.table.rows, first: 0, sortField: this.table.sortField, sortOrder: this.table.sortOrder});
      }
    })
  }

  getProductsPaginated(pageEvent: TableLazyLoadEvent) {
    this.showLoading = true;

    const {page, size, sortField, sortOrder} = this.getPageInfos(pageEvent);

    this.productsService.getProductsPaginated(page, size, sortField, sortOrder).subscribe({
      next: (response) => {
        this.products = response.content;
        this.totalProducts = response.totalElements;

        this.showLoading = false;
      }
    })
  }

  openProductDetails(productId: string){
    this.router.navigate(["products", "details", productId]);
  }

  handlePageChange(pageEvent: TableLazyLoadEvent): void {
    if(this.searchFilter){
      this.getProductsBySearchFilter(pageEvent)
    }
    else {
      this.getProductsPaginated(pageEvent);
    }
  }

  handleSearchProduct(): void {
    this.router.navigate([], {queryParams: {search: this.searchFilter}})
  }

  getProductsBySearchFilter(pageEvent: TableLazyLoadEvent): void {
    this.showLoading = true;

    const {page, size, sortField, sortOrder} = this.getPageInfos(pageEvent);

    this.productsService.getProductsBySeacrhFilter(this.searchFilter, page, size, sortField, sortOrder).subscribe({
      next: (response) => {
        this.products = response.content;
        this.totalProducts = response.totalElements;

        this.showLoading = false;
      },

      error: (error: HttpErrorResponse) => {
        console.error(error);

        this.showLoading = false;
      }
    })
  }

  handleClearSearchFilter(): void {
    this.searchFilter = "";
    this.getProductsPaginated({rows: this.table.rows, first: 0, sortField: this.table.sortField, sortOrder: this.table.sortOrder})
    this.router.navigate([], {queryParams: {search: null}, queryParamsHandling: 'merge'})
  }

  private getPageInfos(pageEvent: TableLazyLoadEvent): {page: number, size: number, sortField: string, sortOrder: "asc" | "desc"} {
    const page: number = pageEvent.first! / pageEvent.rows!;
    const size: number = pageEvent.rows!

    const sortField: string = <string> pageEvent.sortField || "name";

    const sortOrder: "asc" | "desc" = pageEvent.sortOrder == 1 ? "asc" : "desc";

    return {page, size, sortField, sortOrder};
  }
}
