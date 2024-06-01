import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { IPage } from 'src/app/typings/interfaces/page.interface';
import { CategoryModel } from 'src/app/typings/models/category.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = `${environment.apiUrl}/categories`;

  constructor() { }

  saveCategory(category: CategoryModel): Observable<CategoryModel> {
    return this.http.post<CategoryModel>(this.apiUrl, category);
  }

  getAllCategories(): Observable<Array<CategoryModel>> {
    return this.http.get<Array<CategoryModel>>(this.apiUrl);
  }

  getCategoriesPaginated(page: number, size: number): Observable<IPage<CategoryModel>> {
    const url: string = `${this.apiUrl}/paginated?page=${page}&size=${size}`;

    return this.http.get<IPage<CategoryModel>>(url);
  }

  getCategoryById(id: string): Observable<CategoryModel> {
    const url: string = `${this.apiUrl}/${id}`;

    return this.http.get<CategoryModel>(url);
  }

  getCategoriesProductVarietyCount(): Observable<Array<any>> {
    const url: string = `${this.apiUrl}/products-variety-count`;

    return this.http.get<Array<any>>(url);
  }

  updateCategory(categoryUpdate: Partial<CategoryModel>): Observable<CategoryModel> {
    const url: string = `${this.apiUrl}/${categoryUpdate.id}`;

    return this.http.put<CategoryModel>(url, categoryUpdate);
  }

  changeCategoryActiveStatus(categoryId: string, newActiveStatus: boolean): Observable<void> {
    const url: string = `${this.apiUrl}/${categoryId}/change-active-status?active=${newActiveStatus}`;

    return this.http.delete<void>(url);
  }

}
