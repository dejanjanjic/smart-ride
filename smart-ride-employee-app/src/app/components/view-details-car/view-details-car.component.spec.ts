import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDetailsCarComponent } from './view-details-car.component';

describe('ViewDetailsCarComponent', () => {
  let component: ViewDetailsCarComponent;
  let fixture: ComponentFixture<ViewDetailsCarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDetailsCarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDetailsCarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
