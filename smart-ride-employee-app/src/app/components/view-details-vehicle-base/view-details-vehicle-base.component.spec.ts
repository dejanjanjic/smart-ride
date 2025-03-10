import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDetailsVehicleBaseComponent } from './view-details-vehicle-base.component';

describe('ViewDetailsVehicleBaseComponent', () => {
  let component: ViewDetailsVehicleBaseComponent;
  let fixture: ComponentFixture<ViewDetailsVehicleBaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDetailsVehicleBaseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDetailsVehicleBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
