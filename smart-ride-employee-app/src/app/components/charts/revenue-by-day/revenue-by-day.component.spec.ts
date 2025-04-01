import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueByDayComponent } from './revenue-by-day.component';

describe('RevenueByDayComponent', () => {
  let component: RevenueByDayComponent;
  let fixture: ComponentFixture<RevenueByDayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevenueByDayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueByDayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
