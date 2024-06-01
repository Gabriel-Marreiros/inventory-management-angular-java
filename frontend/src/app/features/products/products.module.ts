import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductsGridModule } from './grid/products-grid.module';
import { ProductsFormModule } from './form/products-form.module';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    ProductsGridModule,
    ProductsFormModule
  ]
})
export class ProductsModule { }
