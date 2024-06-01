import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPanelComponent } from '../features/admin-panel/admin-panel.component';
import { HomeComponent } from '../features/home/home.component';
import { LoginComponent } from '../features/login/login.component';
import { AuthenticationGuard } from './guards/authentication/authentication.guard';
import { NotFoundPageComponent } from '../features/not-found-page/not-found-page.component';

const routes: Routes = [

  {
    path: "",
    pathMatch: "full",
    redirectTo: 'home',
  },

  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthenticationGuard]
  },

  {
    path: "admin",
    component: AdminPanelComponent,
    canActivate: [AuthenticationGuard]
  },

  {
    path: "products",
    loadChildren: () => import('../features/products/products.module').then((m) => m.ProductsModule),
    canActivate: [AuthenticationGuard]
    // children: [
    //   {
    //     path: "",
    //     pathMatch: "full",
    //     redirectTo: "grid"
    //   },

    //   {
    //     path: "grid",
    //     loadChildren: () => import().then((m) => m)
    //   },

    //   {
    //     path: "form",
    //     loadChildren: () => import().then((m) => m)
    //   }
    // ]
  },

  {
    path: "reports",
    loadChildren: () => import("../features/reports/reports.module").then(m => m.ReportsModule),
    canActivate: [AuthenticationGuard]
  },

  {
    path: 'login',
    component: LoginComponent,
  },

  {
    path: "**",
    component: NotFoundPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
