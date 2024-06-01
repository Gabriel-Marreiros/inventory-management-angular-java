import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { IPage } from 'src/app/typings/interfaces/page.interface';
import { UserModel } from 'src/app/typings/models/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  http: HttpClient = inject(HttpClient);
  apiUrl: string = `${environment.apiUrl}/users`;

  constructor() { }

  getAllUsers(): Observable<Array<UserModel>> {
    return this.http.get<Array<UserModel>>(this.apiUrl);
  }

  getUsersPaginated(page: number, size: number): Observable<IPage<UserModel>> {
    const url: string = `${this.apiUrl}/paginated?page=${page}&size=${size}`;

    return this.http.get<IPage<UserModel>>(url);
  }

  getUserById(id: string): Observable<UserModel> {
    const url: string = `${this.apiUrl}/${id}`

    return this.http.get<UserModel>(url);
  }

  saveUser(user: UserModel): Observable<UserModel> {
    return this.http.post<UserModel>(this.apiUrl, user);
  }

  updateUser(updateUser: Partial<UserModel>): Observable<UserModel> {
    const url: string = `${this.apiUrl}/${updateUser.id}`;

    return this.http.put<UserModel>(url, updateUser);
  }

  changeUserActiveStatus(userId: string, newActiveStatus: boolean): Observable<void> {
    const url: string = `${this.apiUrl}/${userId}/change-active-status?active=${newActiveStatus}`;

    return this.http.delete<void>(url);
  }
}
