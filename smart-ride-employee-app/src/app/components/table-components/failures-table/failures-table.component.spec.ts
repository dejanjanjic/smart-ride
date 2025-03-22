import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FailuresTableComponent } from './failures-table.component';

describe('FailuresTableComponent', () => {
  let component: FailuresTableComponent;
  let fixture: ComponentFixture<FailuresTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FailuresTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FailuresTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
