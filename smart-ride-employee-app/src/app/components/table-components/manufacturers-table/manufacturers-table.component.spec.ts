import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManufacturersTableComponent } from './manufacturers-table.component';

describe('ManufacturersTableComponent', () => {
  let component: ManufacturersTableComponent;
  let fixture: ComponentFixture<ManufacturersTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManufacturersTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManufacturersTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
