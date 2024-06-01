import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveBrandProductsChartComponent } from './active-brand-products-chart.component';

describe('ActiveBrandProductsChartComponent', () => {
  let component: ActiveBrandProductsChartComponent;
  let fixture: ComponentFixture<ActiveBrandProductsChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ActiveBrandProductsChartComponent]
    });
    fixture = TestBed.createComponent(ActiveBrandProductsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
