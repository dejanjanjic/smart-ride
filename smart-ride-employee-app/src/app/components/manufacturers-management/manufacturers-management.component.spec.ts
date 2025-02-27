import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManufacturersManagementComponent } from './manufacturers-management.component';

describe('ManufacturersManagementComponent', () => {
  let component: ManufacturersManagementComponent;
  let fixture: ComponentFixture<ManufacturersManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManufacturersManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManufacturersManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
