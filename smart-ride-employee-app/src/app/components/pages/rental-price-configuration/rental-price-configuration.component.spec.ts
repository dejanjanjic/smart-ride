import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalPriceConfigurationComponent } from './rental-price-configuration.component';

describe('RentalPriceConfigurationComponent', () => {
  let component: RentalPriceConfigurationComponent;
  let fixture: ComponentFixture<RentalPriceConfigurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalPriceConfigurationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalPriceConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
