import { Component, inject } from '@angular/core';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';

@Component({
  selector: 'app-home',
  styleUrls: ['./home.component.scss'],
  providers: [
    UserDetailsService
  ],

  template: `
    <section class="container-fluid min-vh-100">

      <div class="row justify-content-center align-items-center my-5 mb-lg-0">

        <div class="col-11 col-sm-10 col-xxl-8 py-5 px-5 px-lg-0 border rounded shadow-sm bg-white">

          <div class="row mb-5">
            <h2 class="text-center">Olá, seja bem-vindo(a) <span class="fw-bold">{{userDetailsService.getUserName()}}</span></h2>
          </div>

          <div class="row justify-content-center gap-5">

            <div class="col-12 col-md-10 col-lg-5 col-xl-4 border border-secondary-subtle rounded shadow panel-card">
              <a [routerLink]="['/products', 'grid']" class="d-flex align-items-center justify-content-center p-5">
                <img src="assets/images/Checking boxes-amico.png" alt="" class="w-50">
                <h3 class="fs-2 text-black">Inventário</h3>
              </a>
            </div>

            <div class="col-12 col-md-10 col-lg-5 col-xl-4 border border-secondary-subtle rounded shadow panel-card">
              <a [routerLink]="['/products', 'form']" class="d-flex align-items-center justify-content-center gap-1 p-5">
                <img src="assets/images/Forms-rafiki.png" alt="" class="w-50">
                <h3 class="fs-2 text-black">Cadastrar Produto</h3>
              </a>
            </div>

            <div class="col-12 col-md-10 col-lg-5 col-xl-4 border border-secondary-subtle rounded shadow panel-card">
              <a [routerLink]="['/admin']" [queryParams]="{view: 'users'}" class="d-flex align-items-center justify-content-center gap-1 p-5">
                <img src="assets/images/Team work-rafiki.png" alt="" class="w-50">
                <h3 class="fs-2 text-black">Usuários</h3>
              </a>
            </div>

            <div class="col-12 col-md-10 col-lg-5 col-xl-4 border border-secondary-subtle rounded shadow panel-card">
              <a [routerLink]="['/reports']" class="d-flex align-items-center justify-content-center gap-1 p-5">
                <img src="assets/images/Data report-pana.png" alt="" class="w-50">
                <h3 class="fs-2 text-black">Relatórios</h3>
              </a>
            </div>

          </div>

        </div>
      </div>

    </section>
  `
})
export class HomeComponent {
  userDetailsService: UserDetailsService = inject(UserDetailsService);

  constructor(){}

}
