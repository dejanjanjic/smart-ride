import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalsTableComponent } from './rentals-table.component';

describe('RentalsTableComponent', () => {
  let component: RentalsTableComponent;
  let fixture: ComponentFixture<RentalsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalsTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
