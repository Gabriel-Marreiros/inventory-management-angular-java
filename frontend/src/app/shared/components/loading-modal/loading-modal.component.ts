import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DialogModule } from 'primeng/dialog';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
   selector: 'loading-modal',
   standalone: true,
   imports: [
      CommonModule,
      DialogModule,
      ProgressSpinnerModule
   ],
   styleUrls: ['./loading-modal.component.scss'],

   template: `
      <p-dialog [header]="headerText" [visible]="showModal" [modal]="true" [draggable]="false" [resizable]="false" [styleClass]="styleClass">
         <div class="text-center">
            <p-progressSpinner></p-progressSpinner>
         </div>
      </p-dialog>
   `
})
export class LoadingModalComponent {
   @Input({required: true})
   showModal: boolean = false

   @Input({required: false})
   headerText: string = "Carregando..."

   @Input()
   styleClass!: string
}
