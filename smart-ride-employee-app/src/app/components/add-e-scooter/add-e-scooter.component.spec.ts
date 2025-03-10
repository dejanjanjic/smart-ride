import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEScooterComponent } from './add-e-scooter.component';

describe('AddEScooterComponent', () => {
  let component: AddEScooterComponent;
  let fixture: ComponentFixture<AddEScooterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEScooterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEScooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
