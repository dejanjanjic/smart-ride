import { Vehicle } from './vehicle.model';

export interface Car extends Vehicle {
  purchaseDateTime: Date;
  description: string;
}
