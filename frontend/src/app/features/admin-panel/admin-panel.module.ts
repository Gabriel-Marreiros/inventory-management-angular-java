import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TableModule } from 'primeng/table';
import { AdminPanelComponent } from './admin-panel.component';
import { AdminPanelCardComponent } from './card/admin-panel-card.component';
import { CategoriesGridComponent } from './categories-grid/categories-grid.component';
import { LastTenRegisteredProductsGridComponent } from './last-ten-registered-products-grid/last-ten-registered-products-grid.component';
import { UsersGridComponent } from './users-grid/users-grid.component';



@NgModule({
  declarations: [
    AdminPanelComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    TableModule,
    AdminPanelCardComponent,
    UsersGridComponent,
    CategoriesGridComponent,
    LastTenRegisteredProductsGridComponent,
    RouterModule
  ],
  exports: [
    AdminPanelComponent
  ]
})
export class AdminPanelModule { }
