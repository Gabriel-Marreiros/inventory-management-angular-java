import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsGridComponent } from './grid/products-grid.component';
import { ProductsFormComponent } from './form/products-form.component';

const routes: Routes = [
  {
    path: "",
    pathMatch: 'full',
    redirectTo: "grid"
  },

  {
    path: "grid",
    pathMatch: 'full',
    component: ProductsGridComponent
  },

  {
    path: "form",
    pathMatch: "full",
    component: ProductsFormComponent
  },

  {
    path: "details/:id",
    pathMatch: "full",
    component: ProductsFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
