import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EBikesTableComponent } from './e-bikes-table.component';

describe('EBikesTableComponent', () => {
  let component: EBikesTableComponent;
  let fixture: ComponentFixture<EBikesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EBikesTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EBikesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
