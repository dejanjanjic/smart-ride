import { Failure } from './failure.model';
import { Rental } from './rental.model';

export interface Vehicle {
  id: string;
  manufacturer: string;
  model: string;
  purchasePrice: number;
  picturePath?: string;
  failures?: Failure[];
  rentals?: Rental[];
}
