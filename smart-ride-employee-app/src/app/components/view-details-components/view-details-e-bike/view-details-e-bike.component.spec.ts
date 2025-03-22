import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDetailsEBikeComponent } from './view-details-e-bike.component';

describe('ViewDetailsEBikeComponent', () => {
  let component: ViewDetailsEBikeComponent;
  let fixture: ComponentFixture<ViewDetailsEBikeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDetailsEBikeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDetailsEBikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
