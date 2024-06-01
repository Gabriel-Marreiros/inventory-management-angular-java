import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryProductVarietyChartComponent } from './category-product-variety-chart.component';

describe('CategoryProductVarietyChartComponent', () => {
  let component: CategoryProductVarietyChartComponent;
  let fixture: ComponentFixture<CategoryProductVarietyChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoryProductVarietyChartComponent]
    });
    fixture = TestBed.createComponent(CategoryProductVarietyChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
