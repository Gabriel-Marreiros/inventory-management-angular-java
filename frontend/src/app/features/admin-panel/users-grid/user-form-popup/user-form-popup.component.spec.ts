import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormPopupComponent } from './user-form-popup.component';

describe('UserFormPopupComponent', () => {
  let component: UserFormPopupComponent;
  let fixture: ComponentFixture<UserFormPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UserFormPopupComponent]
    });
    fixture = TestBed.createComponent(UserFormPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
