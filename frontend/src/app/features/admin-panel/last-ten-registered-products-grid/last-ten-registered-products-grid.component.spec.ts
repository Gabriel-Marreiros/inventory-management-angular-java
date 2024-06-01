import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LastTenRegisteredProductsGridComponent } from './last-ten-registered-products-grid.component';

describe('LastTenRegisteredProductsGridComponent', () => {
  let component: LastTenRegisteredProductsGridComponent;
  let fixture: ComponentFixture<LastTenRegisteredProductsGridComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LastTenRegisteredProductsGridComponent]
    });
    fixture = TestBed.createComponent(LastTenRegisteredProductsGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
