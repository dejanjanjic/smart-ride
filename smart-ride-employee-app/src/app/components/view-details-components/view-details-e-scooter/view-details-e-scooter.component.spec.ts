import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDetailsEScooterComponent } from './view-details-e-scooter.component';

describe('ViewDetailsEScooterComponent', () => {
  let component: ViewDetailsEScooterComponent;
  let fixture: ComponentFixture<ViewDetailsEScooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDetailsEScooterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDetailsEScooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
