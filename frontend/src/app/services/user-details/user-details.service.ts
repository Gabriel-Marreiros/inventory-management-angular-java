import { Injectable, inject } from '@angular/core';
import { RolesEnum } from 'src/app/typings/enums/roles.enum';
import { ITokenJwtPayload } from 'src/app/typings/interfaces/token-jwt-payload.interface';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {
  tokenJwtService: TokenService = inject(TokenService);

  userDetails!: ITokenJwtPayload;

  constructor() {
    if(this.tokenJwtService.hasTokenJwt()){
      const token: string = this.tokenJwtService.getTokenJwt();
      this.setUserDetails(token);
    };
  }

  setUserDetails(token: string){
    this.userDetails = this.tokenJwtService.getPayloadFromTokenJwt(token);
  }

  userIsAdmin(): boolean {
    return this.userDetails.role == RolesEnum.ADMIN;
  }

  getUserName(): string {
    return this.userDetails.name;
  }

  getUserId(): string {
    return this.userDetails.userId;
  }

}
