import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueByVehicleTypeComponent } from './revenue-by-vehicle-type.component';

describe('RevenueByVehicleTypeComponent', () => {
  let component: RevenueByVehicleTypeComponent;
  let fixture: ComponentFixture<RevenueByVehicleTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevenueByVehicleTypeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueByVehicleTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
