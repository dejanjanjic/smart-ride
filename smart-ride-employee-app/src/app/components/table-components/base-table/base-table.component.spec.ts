import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseTableComponent } from './base-table.component';

describe('BaseTableComponent', () => {
  let component: BaseTableComponent<any>;
  let fixture: ComponentFixture<BaseTableComponent<any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BaseTableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BaseTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
