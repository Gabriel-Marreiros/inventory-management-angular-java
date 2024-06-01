import { Component } from '@angular/core';

@Component({
  selector: 'reports',
  styleUrls: ['./reports.component.scss'],

  template: `
    <section class="container-fluid p-3 p-xl-5">

      <div class="row mb-4 mb-lg-5">
        <div class="col-12">
          <registered-product-numbers-chart />
        </div>
      </div>

      <div class="row">
        <div class="col-12 col-lg-7 col-xxl-8">
          <active-brand-products-chart />
        </div>

        <div class="col-12 col-lg-5 col-xxl-4 mt-4 mt-lg-0">
          <category-product-variety-chart />
        </div>
      </div>

    </section>
  `
})
export class ReportsComponent{

  constructor() { }

}
