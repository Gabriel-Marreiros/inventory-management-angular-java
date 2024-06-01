import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ChartModule } from 'primeng/chart';
import { CategoriesService } from 'src/app/services/categories/categories.service';
import { ReportsRoutingModule } from './reports-routing.module';
import { ReportsComponent } from './reports.component';
import { RegisteredProductNumbersChartComponent } from './registered-product-numbers-chart/registered-product-numbers-chart.component';
import { ActiveBrandProductsChartComponent } from './active-brand-products-chart/active-brand-products-chart.component';
import { CategoryProductVarietyChartComponent } from './category-product-variety-chart/category-product-variety-chart.component';
import { SplitButtonModule } from 'primeng/splitbutton';
import { ProductsService } from 'src/app/services/products/products.service';


@NgModule({
  declarations: [
    ReportsComponent,
    RegisteredProductNumbersChartComponent,
    ActiveBrandProductsChartComponent,
    CategoryProductVarietyChartComponent
  ],
  imports: [
    CommonModule,
    ReportsRoutingModule,
    ChartModule,
    SplitButtonModule
  ],
  exports: [
    ReportsComponent
  ],
  providers: [
    ProductsService,
    CategoriesService
  ]
})
export class ReportsModule { }
