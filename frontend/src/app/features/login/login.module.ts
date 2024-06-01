import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { LoadingModalComponent } from 'src/app/shared/components/loading-modal/loading-modal.component';



@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    FormsModule,
    LoadingModalComponent
  ],
  exports: [
    LoginComponent
  ],
  providers: [
   AuthenticationService
  ]
})
export class LoginModule { }
