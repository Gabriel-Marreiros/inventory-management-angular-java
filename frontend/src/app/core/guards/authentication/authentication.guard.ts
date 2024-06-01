import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { TokenService } from 'src/app/services/token/token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {
  router: Router = inject(Router);
  authenticationService: AuthenticationService = inject(AuthenticationService);
  tokenJwtService: TokenService = inject(TokenService);

  constructor(){}

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    const authenticated: boolean = await firstValueFrom(this.authenticationService.authenticate());

    if(!authenticated){
      this.tokenJwtService.removeTokenJwt();
      return this.router.createUrlTree(['login']);
    }

    return true;
  }

}
