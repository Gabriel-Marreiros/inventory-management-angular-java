import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from 'src/app/services/token/token.service';

@Injectable()
export class TokenJwtInterceptor implements HttpInterceptor {

  private tokenService: TokenService = inject(TokenService);

  constructor() { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token: string = this.tokenService.getTokenJwt();

    const modifiedRequets = request.clone({ setHeaders: { Authorization: token } });

    return next.handle(modifiedRequets)
  }
}
