import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelCardComponent } from './admin-panel-card.component';

describe('AdminPanelCardComponent', () => {
  let component: AdminPanelCardComponent;
  let fixture: ComponentFixture<AdminPanelCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdminPanelCardComponent]
    });
    fixture = TestBed.createComponent(AdminPanelCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
