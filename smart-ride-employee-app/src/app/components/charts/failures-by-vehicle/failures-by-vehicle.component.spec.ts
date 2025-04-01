import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FailuresByVehicleComponent } from './failures-by-vehicle.component';

describe('FailuresByVehicleComponent', () => {
  let component: FailuresByVehicleComponent;
  let fixture: ComponentFixture<FailuresByVehicleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FailuresByVehicleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FailuresByVehicleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
