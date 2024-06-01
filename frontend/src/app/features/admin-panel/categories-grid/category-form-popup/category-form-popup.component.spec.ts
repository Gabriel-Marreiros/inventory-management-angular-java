import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryFormPopupComponent } from './category-form-popup.component';

describe('CategoryFormPopupComponent', () => {
  let component: CategoryFormPopupComponent;
  let fixture: ComponentFixture<CategoryFormPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CategoryFormPopupComponent]
    });
    fixture = TestBed.createComponent(CategoryFormPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
