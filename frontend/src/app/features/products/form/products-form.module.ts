import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ProductsService } from 'src/app/services/products/products.service';
import { ProductsFormComponent } from './products-form.component';

import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { LoadingModalComponent } from 'src/app/shared/components/loading-modal/loading-modal.component';


@NgModule({
  declarations: [
    ProductsFormComponent
  ],
  imports: [
    CommonModule,
    InputTextModule,
    InputTextareaModule,
    InputNumberModule,
    DropdownModule,
    FileUploadModule,
    ButtonModule,
    ConfirmDialogModule,
    ReactiveFormsModule,
    RouterModule,
    LoadingModalComponent
  ],
  exports: [
    ProductsFormComponent
  ],
  providers: [
    ProductsService,
    ConfirmationService
  ]
})
export class ProductsFormModule { }
