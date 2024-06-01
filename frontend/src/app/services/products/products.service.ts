import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, filter } from 'rxjs';
import { IBrandsProductsStatusReport } from 'src/app/typings/interfaces/brands-products-status-report-interface';
import { IPage } from 'src/app/typings/interfaces/page.interface';
import { IProductsNumberRegisteredMonthReport } from 'src/app/typings/interfaces/products-number-registered-month-report.interface';
import { ISummaryProductsCategoriesUsersActive } from 'src/app/typings/interfaces/summary-products-categories-users-active.interface';
import { ProductModel } from 'src/app/typings/models/product.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private http: HttpClient = inject(HttpClient);
  private apiUrl: string = `${environment.apiUrl}/products`;

  constructor() { }

  saveProduct(product: ProductModel): Observable<ProductModel> {
    return this.http.post<ProductModel>(this.apiUrl, product);
  }

  getAllProducts(): Observable<Array<ProductModel>> {
    return this.http.get<Array<ProductModel>>(this.apiUrl);
  }

  getProductsPaginated(page: number, size: number, sortField: string, sortOrder: "asc" | "desc"): Observable<IPage<ProductModel>>{
    const url: string = `${this.apiUrl}/paginated?page=${page}&size=${size}&sortBy=${sortField}&sortOrder=${sortOrder}`;

    return this.http.get<IPage<ProductModel>>(url);
  }


  getProductsBySeacrhFilter(searchFilter: string, page: number, size: number, sortField: string, sortOrder: "asc" | "desc") {
    const url: string = `${this.apiUrl}/search-filter/paginated?searchFilter=${searchFilter}&page=${page}&size=${size}&sortBy=${sortField}&sortOrder=${sortOrder}`;

    return this.http.get<IPage<ProductModel>>(url);
  }

  getLastTenRegisteredProducts(): Observable<Array<ProductModel>> {
    const url: string = `${this.apiUrl}/last-ten-registered`;

    return this.http.get<Array<ProductModel>>(url);
  }

  getProductById(id: string): Observable<ProductModel> {
    const url: string = `${this.apiUrl}/${id}`;

    return this.http.get<ProductModel>(url);
  }

  updateProduct(product: Partial<ProductModel>): Observable<ProductModel> {
    const url: string = `${this.apiUrl}/${product.id}`;

    return this.http.put<ProductModel>(url, product);
  }

  getBrandsProductsStatusReport(): Observable<Array<IBrandsProductsStatusReport>> {
    const url: string = `${this.apiUrl}/brands-products-status-report`;

    return this.http.get<Array<IBrandsProductsStatusReport>>(url);
  }

  getProductsNumberRegisteredMonthReport(yearFilter?: string): Observable<Array<IProductsNumberRegisteredMonthReport>>{
    let url: string = `${this.apiUrl}/products-number-registered-month-report`;

    if(yearFilter){
      url += `?year=${yearFilter}`;
    }

    return this.http.get<Array<IProductsNumberRegisteredMonthReport>>(url);
  }

  changeProductActiveStatus(productId: string, newActiveStatus: boolean): Observable<void> {
    const url: string = `${this.apiUrl}/${productId}/change-active-status?active=${newActiveStatus}`;

    return this.http.delete<void>(url);
  }

  getSummaryProductsCategoriesUsersActive(): Observable<ISummaryProductsCategoriesUsersActive> {
    const url: string = `${this.apiUrl}/summary-products-categories-users-active`;

    return this.http.get<ISummaryProductsCategoriesUsersActive>(url);
  }
}
