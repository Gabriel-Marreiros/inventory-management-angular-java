import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoriesGridComponent } from './categories-grid.component';

describe('CategoriesGridComponent', () => {
  let component: CategoriesGridComponent;
  let fixture: ComponentFixture<CategoriesGridComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoriesGridComponent]
    });
    fixture = TestBed.createComponent(CategoriesGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
