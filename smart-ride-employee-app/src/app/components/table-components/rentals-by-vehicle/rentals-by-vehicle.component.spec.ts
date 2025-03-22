import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsByVehicleComponent } from './rentals-by-vehicle.component';

describe('RentalsByVehicleComponent', () => {
  let component: RentalsByVehicleComponent;
  let fixture: ComponentFixture<RentalsByVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalsByVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalsByVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
