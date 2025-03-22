import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsManagementComponent } from './rentals-management.component';

describe('RentalsManagementComponent', () => {
  let component: RentalsManagementComponent;
  let fixture: ComponentFixture<RentalsManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalsManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
