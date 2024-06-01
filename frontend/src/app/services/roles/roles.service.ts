import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { RoleModel } from 'src/app/typings/models/role.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  readonly apiUrl: string = `${environment.apiUrl}/roles`

  private http: HttpClient = inject(HttpClient);

  constructor() { }

  getAllRoles(): Observable<Array<RoleModel>>{
    return this.http.get<Array<RoleModel>>(this.apiUrl);
  }
}
