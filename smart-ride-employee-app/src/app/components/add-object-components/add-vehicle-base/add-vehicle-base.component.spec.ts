import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddVehicleBaseComponent } from './add-vehicle-base.component';

describe('AddVehicleBaseComponent', () => {
  let component: AddVehicleBaseComponent;
  let fixture: ComponentFixture<AddVehicleBaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddVehicleBaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddVehicleBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
