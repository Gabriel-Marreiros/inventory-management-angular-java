import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisteredProductNumbersChartComponent } from './registered-product-numbers-chart.component';

describe('RegisteredProductNumbersChartComponent', () => {
  let component: RegisteredProductNumbersChartComponent;
  let fixture: ComponentFixture<RegisteredProductNumbersChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RegisteredProductNumbersChartComponent]
    });
    fixture = TestBed.createComponent(RegisteredProductNumbersChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
