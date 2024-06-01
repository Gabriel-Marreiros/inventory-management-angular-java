import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AdminPanelModule } from '../features/admin-panel/admin-panel.module';
import { HomeModule } from '../features/home/home.module';
import { LoginModule } from '../features/login/login.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TokenJwtInterceptor } from './interceptors/token-jwt/token-jwt.interceptor';
import { HeaderComponent } from '../shared/components/header/header.component';
import { FooterComponent } from '../shared/components/footer/footer.component';
import { NotFoundPageComponent } from '../features/not-found-page/not-found-page.component';
import { UserDetailsService } from '../services/user-details/user-details.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    HomeModule,
    AdminPanelModule,
    LoginModule,
    HeaderComponent,
    FooterComponent,
    NotFoundPageComponent
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenJwtInterceptor,
      multi: true
    },
    UserDetailsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
