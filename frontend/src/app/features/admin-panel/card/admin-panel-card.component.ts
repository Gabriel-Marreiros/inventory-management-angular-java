import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'admin-panel-card',
  standalone: true,
  imports: [
    CommonModule,
    FontAwesomeModule
  ],

  template: `
    <div class="border rounded shadow-sm bg-white">
        <div class="d-flex justify-content-around py-4 px-1">
          <div class="">
              <h3 class="fs-3 fw-normal">{{title}}</h3>
              <span class="fs-2 fw-bold">{{count}}</span>
          </div>

          <div class="d-flex align-items-center">
              <fa-icon [icon]="icon" size="4x" ></fa-icon>
          </div>
        </div>
    </div>
  `
})
export class AdminPanelCardComponent {

  @Input({ required: true })
  title!: string;

  @Input({ required: true })
  count!: number;

  @Input({ required: true })
  icon!: IconDefinition;

}
