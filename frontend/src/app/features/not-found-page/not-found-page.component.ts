import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'not-found-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ],
  styleUrls: ['./not-found-page.component.scss'],

  template: `
    <section class="container d-flex flex-column align-items-center min-vh-100">

      <div class="row mt-5">
        <div class="col-md-6 d-flex flex-column justify-content-center align-items-center text-center">
            <h5 class="fs-1 w-auto">Página não encontrada!</h5>

            <p class="fs-5">Não foi possivel localizar a página solicitada. Por favor, verifique o link e tente novamente ou retorne para a página inicial</p>

            <a [routerLink]="['/home']" class="btn btn-outline-primary w-auto">Página inicial</a>
        </div>

        <div class="col-md-6">
          <img src="assets/images/404 Error-rafiki.png" alt="" class="img-fluid">
        </div>

      </div>

    </section>
  `
})
export class NotFoundPageComponent {

  constructor(){}

}
