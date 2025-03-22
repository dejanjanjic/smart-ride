import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFailureByVehicleComponent } from './add-failure-by-vehicle.component';

describe('AddFailureByVehicleComponent', () => {
  let component: AddFailureByVehicleComponent;
  let fixture: ComponentFixture<AddFailureByVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddFailureByVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFailureByVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
