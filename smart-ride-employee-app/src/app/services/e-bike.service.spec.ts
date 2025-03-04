import { TestBed } from '@angular/core/testing';

import { EBikeService } from './e-bike.service';

describe('EBikeService', () => {
  let service: EBikeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EBikeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
