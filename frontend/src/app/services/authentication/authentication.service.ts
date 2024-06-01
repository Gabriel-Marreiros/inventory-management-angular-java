import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, from, of } from 'rxjs';
import { ILoginResponse } from 'src/app/typings/interfaces/login-response.interface';
import { ILogin } from 'src/app/typings/interfaces/login.interface';
import { environment } from 'src/environments/environment';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private http: HttpClient = inject(HttpClient);
  private tokenService: TokenService = inject(TokenService);
  private router: Router = inject(Router);

  private BASE_URL: string = `${environment.apiUrl}/auth`


  constructor() { }

  login(data: ILogin): Observable<ILoginResponse> {
    const url: string = `${this.BASE_URL}/login`

    return this.http.post<ILoginResponse>(url, data);
  }

  authenticate(): Observable<boolean> {
    const token: string = this.tokenService.getTokenJwt();

    if(!token){
      return of(false);
    }

    const url: string = `${this.BASE_URL}/authenticate`;

    return this.http.post<boolean>(url, token);
  }

  logout(): void {
    this.tokenService.removeTokenJwt();
    this.router.navigate(["login"]);
  }

}
