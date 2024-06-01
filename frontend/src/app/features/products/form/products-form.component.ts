import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { FileSelectEvent } from 'primeng/fileupload';
import { CategoriesService } from 'src/app/services/categories/categories.service';
import { ProductsService } from 'src/app/services/products/products.service';
import { UserDetailsService } from 'src/app/services/user-details/user-details.service';
import { CategoryModel } from 'src/app/typings/models/category.model';
import { ProductModel } from 'src/app/typings/models/product.model';

@Component({
  selector: 'app-products-form',
  templateUrl: './products-form.component.html',
  styleUrls: ['./products-form.component.scss']
})
export class ProductsFormComponent implements OnInit {

  productsService: ProductsService = inject(ProductsService);
  categoriesService: CategoriesService = inject(CategoriesService);
  activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  router: Router = inject(Router);
  confirmationService: ConfirmationService = inject(ConfirmationService);
  location: Location = inject(Location);
  userDetailsService: UserDetailsService = inject(UserDetailsService);

  formHasSubmitted: boolean = false;
  showLoadingModal: boolean = false;
  isDetailsForm: boolean = false;
  isEditingForm: boolean = false;

  categoriesOptions!: Array<CategoryModel>;

  productForm = new FormGroup<Record<keyof ProductModel, FormControl>>({
    id: new FormControl<string | null>(null),
    name: new FormControl<string>('', [Validators.required]),
    brand: new FormControl<string>('', [Validators.required]),
    sku: new FormControl<string>('', [Validators.required]),
    description: new FormControl<string>('', [Validators.required]),
    price: new FormControl<number>(0, [Validators.required]),
    quantity: new FormControl<number>(0, [Validators.required]),
    image: new FormControl<File | null>(null),
    link: new FormControl<string>(''),
    category: new FormControl<string | null>(null, [Validators.required]),
    active: new FormControl<boolean>(true)
  });

  constructor() { }

  ngOnInit(): void {
    this.getCategoriesOptions();

    const productId = this.activatedRoute.snapshot.params["id"];

    if (productId) {
      this.getProductDetails(productId)
      this.isDetailsForm = true;
      this.productForm.disable();
    }
  }

  imageFileSelectHandler($event: FileSelectEvent): void {
    console.log($event.files[0])
  }

  getCategoriesOptions(): void {
    this.categoriesService.getAllCategories()
      .subscribe({
        next: (response) => {
          this.categoriesOptions = response;
        }
      })
  }

  saveProduct(): void {
    this.showLoadingModal = true;

    this.productsService.saveProduct(this.productForm.value as ProductModel).subscribe({
      next: () => {
        this.showLoadingModal = false;
        this.showGenericModal("Produto cadastrado com sucesso!");
      },

      error: (error: HttpErrorResponse) => {
        console.error(error);
      }
    })
  }

  openSaveConfirmationModal(): void {
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Cadastrar Produto",
      message: "Tem certeza que deseja cadastrar esse produto?",
      accept: () => this.saveProduct(),
    });
  }

  updateProduct(): void{
    this.showLoadingModal = true;

    this.productsService.updateProduct(this.productForm.value).subscribe({
      next: () => {
        this.showLoadingModal = false;
        this.showGenericModal("Produto atualizado com sucesso!");
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
      }
    })
  }

  openUpdateConfirmationModal(): void {
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Produto",
      message: "Tem certeza que deseja atualizar esse produto?",
      accept: () => this.updateProduct(),
    });
  }

  getProductDetails(productId: string): void {
    this.showLoadingModal = true;

    this.productsService.getProductById(productId).subscribe({
      next: (response: ProductModel) => {
        this.productForm.setValue(response);
        this.productForm.get("category")?.setValue(response.category.id);

        this.showLoadingModal = false;
      }
    })
  }

  changeCategoryActiveStatus(newActiveStatus: boolean): void {
    if(!this.isDetailsForm) return;

    this.showLoadingModal = true;

    const productId: string = this.productForm.get("id")?.value;

    this.productsService.changeProductActiveStatus(productId, newActiveStatus).subscribe({
      next: (response) => {
        this.showLoadingModal = false;
        this.showGenericModal(`Produto ${newActiveStatus == true ? 'ativado' : 'inativado'} com sucesso!`);
      },

      error: (error: HttpErrorResponse) => {
        console.error(error)
        this.showLoadingModal = false;
        this.showGenericModal(`Ocorreu um erro ao ${newActiveStatus == true ? 'ativar' : 'inativar'} o produto! Por favor, entre em contato com um técnico ou tente novamente mais tarde.`, 'Erro');
      }
    })
  }

  openChangeActiveStatusConfirmModal(newActiveStatus: boolean){
    this.confirmationService.confirm({
      key: "confirmModal",
      header: "Atualizar Categoria",
      message: `Tem certeza que deseja ${newActiveStatus == true ? 'ativar' : 'inativar'} esse produto?`,
      accept: () => this.changeCategoryActiveStatus(newActiveStatus),
    })
  }

  showGenericModal(message: string, header: string = "Inventory Management"){
    this.confirmationService.confirm({
      key: "genericModal",
      header: header,
      message: message,
      accept: () => this.router.navigate(["products", "grid"]),
    })
  }

  enableEditing(): void {
    this.isEditingForm = true;
    this.isDetailsForm = false;
    this.productForm.enable();
  }

  userIsAdmin(): boolean {
    return this.userDetailsService.userIsAdmin();
  }

  showRequiredError(formControl: string): boolean {
    return !!this.productForm.get(formControl)?.errors?.["required"] && !!this.productForm.get(formControl)?.dirty;
  }

  get productIsActive(): boolean {
    return this.productForm.get('active')?.value;
  }
}
