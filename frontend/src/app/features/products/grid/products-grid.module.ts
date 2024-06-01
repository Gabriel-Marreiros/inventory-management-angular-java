import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TableModule } from 'primeng/table';
import { ProductsService } from 'src/app/services/products/products.service';
import { ProductsGridComponent } from './products-grid.component';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';



@NgModule({
  declarations: [
    ProductsGridComponent
  ],
  imports: [
    CommonModule,
    TableModule,
    RouterModule,
    FormsModule,
    InputTextModule,
    FontAwesomeModule
  ],
  exports: [
    ProductsGridComponent
  ],
  providers: [
    ProductsService
  ]
})
export class ProductsGridModule { }
