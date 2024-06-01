import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ProductsService } from 'src/app/services/products/products.service';
import { ProductModel } from 'src/app/typings/models/product.model';
import { ButtonModule } from 'primeng/button';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'last-ten-registered-products-grid',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    RouterModule
  ],
  styleUrls: ['./last-ten-registered-products-grid.component.scss'],

  template: `
    <section>
      <div class="d-flex justify-content-between mb-3">
        <span class="align-content-end">Últimos {{lastTenRegisteredProducts.length}} produtos cadastrados:</span>

        <div class="d-flex gap-3">
          <p-button label="Todos os produtos" [routerLink]="['/products', 'grid']" />
          <p-button label="Cadastrar produto" [routerLink]="['/products', 'form']" />
        </div>
      </div>

      <div class="row">
        <p-table
          [value]="lastTenRegisteredProducts"
          [rowHover]="true"
          [loading]="showGridLoading"
          class="px-0 shadow">

          <ng-template pTemplate="header">
              <tr>
                  <th class="bg-dark-subtle" >Nome</th>
                  <th class="bg-dark-subtle" >Marca</th>
                  <th class="bg-dark-subtle" >Categoria</th>
                  <!-- <th class="bg-dark-subtle" >Quantidade</th> -->
                  <th class="bg-dark-subtle" >Status</th>
              </tr>
          </ng-template>

          <ng-template pTemplate="body" let-product>
              <tr class="border-top border-bottom border-light-subtle cursor-pointer" (click)="openProductDetails(product.id)">
                <td>{{ product.name }}</td>
                <td>{{ product.brand }}</td>
                <td>{{ product.category.name }}</td>
                <td>
                  <div class="badge rounded-pill p-2" [ngClass]="{'bg-success': product.active, 'bg-danger': !product.active}">
                    {{ product.active ? 'Ativo' : 'Inativo' }}
                  </div>
                </td>
              </tr>
          </ng-template>
        </p-table>
      </div>
    </section>
  `
})
export class LastTenRegisteredProductsGridComponent {

  productsService: ProductsService = inject(ProductsService);
  router: Router = inject(Router)

  lastTenRegisteredProducts!: Array<ProductModel>;
  showGridLoading: boolean = false;

  constructor() { }

  ngOnInit(): void {
    this.getLastTenRegisteredProducts();
  }

  getLastTenRegisteredProducts(): void {
    this.showGridLoading = true;

    this.productsService.getLastTenRegisteredProducts().subscribe({
      next: (response) => {
        this.lastTenRegisteredProducts = response;

        this.showGridLoading = false;
      }
    })
  }

  openProductDetails(productId: string){
    this.router.navigate(["/products", "details", productId])
  }
}
