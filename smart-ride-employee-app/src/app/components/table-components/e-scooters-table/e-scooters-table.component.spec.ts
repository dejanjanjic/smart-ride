import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EScootersTableComponent } from './e-scooters-table.component';

describe('EScootersTableComponent', () => {
  let component: EScootersTableComponent;
  let fixture: ComponentFixture<EScootersTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EScootersTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EScootersTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
