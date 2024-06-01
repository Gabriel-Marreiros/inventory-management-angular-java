import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { ITokenJwtPayload } from 'src/app/typings/interfaces/token-jwt-payload.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  private authorizationTokenName: string = environment.authorizationTokenName;

  constructor() { }

  getTokenJwt(): string {
    const token: string = localStorage.getItem(this.authorizationTokenName) ?? "";

    return token;
  }

  setTokenJwt(token: string): void {
    localStorage.setItem(this.authorizationTokenName, token);
  }

  removeTokenJwt(): void {
    localStorage.removeItem(this.authorizationTokenName);
  }

  decodeTokenJwt(token: string){
    const teste = jwtDecode(token);
    return teste;
  }

  getPayloadFromTokenJwt(token: string): ITokenJwtPayload {
    const payload = jwtDecode<ITokenJwtPayload>(token);

    return payload;
  }

  hasTokenJwt(): boolean {
    return !!localStorage.getItem(this.authorizationTokenName);
  }
}
