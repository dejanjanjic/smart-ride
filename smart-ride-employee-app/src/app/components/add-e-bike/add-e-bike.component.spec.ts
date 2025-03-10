import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEBikeComponent } from './add-e-bike.component';

describe('AddEBikeComponent', () => {
  let component: AddEBikeComponent;
  let fixture: ComponentFixture<AddEBikeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEBikeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEBikeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
