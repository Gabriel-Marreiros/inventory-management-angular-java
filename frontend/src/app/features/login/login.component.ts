import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { TokenService } from 'src/app/services/token/token.service';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';
import { ILoginResponse } from 'src/app/typings/interfaces/login-response.interface';
import { ILogin } from 'src/app/typings/interfaces/login.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  styleUrls: ['./login.component.scss'],

  template: `
    <section class="container-fluid vh-100">

      <div class="row h-100 align-items-center justify-content-center">

        <div class="col-12 col-md-11 col-xxl-8 border rounded bg-white p-3 p-md-4 p-lg-5">

            <div class="row mb-2 text-center">
              <h2>Inventory Management</h2>
              <h5>Angular & Java</h5>
            </div>

            <div class="row justify-content-center">
              <div class="col-12 col-sm-10 col-md-6 col-xl-5 my-auto">

                  <div class="p-5 border shadow rounded">
                    <h3 class="mb-5 fw-bold">Login</h3>

                    <form class="d-flex flex-column gap-4" #loginForm="ngForm"  (ngSubmit)="handleLogin($event)">

                        <div class="d-flex flex-column gap-2">
                          <label for="emailInput">E-mail</label>
                          <input name="email" [(ngModel)]="login.email" id="emailInput" type="email" required email pInputText placeholder="Digite o e-mail"/>
                          <div *ngIf="showRequiredError('email')" class="p-error">O campo de e-mail é obrigatório.</div>
                          <div *ngIf="showEmailError('email')" class="p-error">O e-mail digitado não é válido.</div>
                        </div>

                        <div class="d-flex flex-column gap-2">
                          <label for="passwordInput">Senha</label>
                          <p-password name="password" [(ngModel)]="login.password" inputId="passwordInput" [feedback]="false" [toggleMask]="true" placeholder="Digite a senha" required></p-password>
                          <div *ngIf="showRequiredError('password')" class="p-error">O campo de senha é obrigatório.</div>
                        </div>

                        <div *ngIf="showLoginErrorMessage" class="alert alert-danger text-center m-0" role="alert">
                          Usuário ou senha invalidos!
                        </div>

                        <p-button styleClass="w-100 bg-teste rounded-pill mt-3" type="submit" label="Login" />

                    </form>
                  </div>

              </div>

              <div class="col-12 col-sm-10 col-md-6 col-xl-7">
                  <img src="assets/images/Login-amico.png" class="img-fluid" alt="">
              </div>
            </div>

        </div>

      </div>

      <loading-modal styleClass="col-md-5" [showModal]="showLoadingModal" />

    </section>
  `
})
export class LoginComponent implements OnInit {
  authenticationService: AuthenticationService = inject(AuthenticationService);
  router: Router = inject(Router);

  userDetailsService: UserDetailsService = inject(UserDetailsService);
  tokenJwtService: TokenService = inject(TokenService);

  authorizationTokenName: string = environment.authorizationTokenName;
  showLoadingModal: boolean = false;
  showLoginErrorMessage: boolean = false;

  login: ILogin = {
    email: '',
    password: ''
  }

  @ViewChild("loginForm", {static: true})
  loginForm!: NgForm;

  constructor() { }

  ngOnInit(): void {
    this.checkUserAlreadyLoggedAndRedirect();
  }

  handleLogin($event: SubmitEvent): void {
    $event.preventDefault();

    if(this.loginForm.invalid){
      return;
    };

    this.showLoadingModal = true;

    this.authenticationService.login(this.login).subscribe({
      next: (response: ILoginResponse) => {
        this.tokenJwtService.setTokenJwt(response.token);
        this.userDetailsService.setUserDetails(response.token);

        this.showLoadingModal = false;

        this.router.navigate(["/home"]);
      },

      error: (error: HttpErrorResponse) => {
        console.error(error);
        this.showLoginErrorMessage = true;
        this.showLoadingModal = false;
      }
    })
  }

  async checkUserAlreadyLoggedAndRedirect(): Promise<void> {
    const hasAuthenticationToken: boolean = !!localStorage.getItem(this.authorizationTokenName);

    const userAuthenticate: boolean = await firstValueFrom(this.authenticationService.authenticate());

    if(hasAuthenticationToken && userAuthenticate){
      this.router.navigate(["/home"]);
    }
    else {
      this.authenticationService.logout();
    }
  }

  showRequiredError(formControl: string): boolean {
    return this.loginForm.controls[formControl].errors?.['required'] && (this.loginForm.submitted && this.loginForm.invalid);
  }

  showEmailError(formControl: string): boolean {
    return this.loginForm.controls[formControl].errors?.['email'] && (this.loginForm.submitted && this.loginForm.invalid);
  }
}
