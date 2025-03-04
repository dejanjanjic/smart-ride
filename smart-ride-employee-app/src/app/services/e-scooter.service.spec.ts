import { TestBed } from '@angular/core/testing';

import { EScooterService } from './e-scooter.service';

describe('EScooterService', () => {
  let service: EScooterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EScooterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
