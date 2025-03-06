import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { CarsTableComponent } from '../cars-table/cars-table.component';
import { EBikesTableComponent } from '../e-bikes-table/e-bikes-table.component';
import { EScootersTableComponent } from '../e-scooters-table/e-scooters-table.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Vehicle } from '../../model/vehicle.model';
import { CarService } from '../../services/car.service';
import { EBikeService } from '../../services/e-bike.service';
import { EScooterService } from '../../services/e-scooter.service';
import { of } from 'rxjs';
import { Car } from '../../model/car.model';

import Papa from 'papaparse';
import { EBike } from '../../model/ebike.model';
import { EScooter } from '../../model/escooter.model';
import { VehicleState } from '../../enum/vehicle-state.enum';

@Component({
  selector: 'app-vehicles-management',
  imports: [
    MatTabsModule,
    MatIconModule,
    CarsTableComponent,
    EBikesTableComponent,
    EScootersTableComponent,
    MatButtonModule,
  ],
  templateUrl: './vehicles-management.component.html',
  styleUrl: './vehicles-management.component.css',
})
export class VehiclesManagementComponent {
  @ViewChild('fileInput') fileInput!: ElementRef;

  private carService: CarService = inject(CarService);
  private eBikeService: EBikeService = inject(EBikeService);
  private eScooterService: EScooterService = inject(EScooterService);

  openFileDialog(): void {
    this.fileInput.nativeElement.click();
  }
  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      console.log('Selected file:', file.name);
      const reader = new FileReader();
      reader.onload = (e) => {
        const content: string = e.target?.result as string;
        const lines: Array<string> = content.split('\n');
        for (let line of lines) {
          let mark = line.split(',')[0];
          if (mark === 'C') {
            console.log(line);
            this.parseCar(line);
          } else if (mark === 'B') {
            this.parseEBike(line);
          } else if (mark === 'S') {
            this.parseEScooter(line);
          } else {
            throw new Error(`Mark '${mark}' is invalid`);
          }
        }
        console.log('File content:', content);
      };
      reader.readAsText(file);
    }
  }
  parseEScooter(line: string) {
    const parsed = Papa.parse<string[]>(line, {
      delimiter: ',',
      skipEmptyLines: true,
      header: false,
    });
    const parts = parsed.data[0];

    if (!parts || parts.length < 6) {
      throw new Error('Invalid line format');
    }

    const id = parts[1],
      manufacturer = parts[2],
      model = parts[3],
      purchasePrice = parseFloat(parts[4]),
      maxSpeed = parseInt(parts[5]);

    const eScooter: EScooter = {
      id: id,
      manufacturer: manufacturer,
      model: model,
      purchasePrice: purchasePrice,
      maxSpeed: maxSpeed,
      vehicleState: VehicleState.AVAILABLE,
    };

    this.eScooterService.add(eScooter).subscribe({
      next: (eScooter: EScooter) => {
        console.log('Successful:', eScooter);
      },
      error: (err) => {
        console.error('Conflict:', err.message);
      },
    });
  }
  parseEBike(line: string) {
    const parsed = Papa.parse<string[]>(line, {
      delimiter: ',',
      skipEmptyLines: true,
      header: false,
    });
    const parts = parsed.data[0];

    if (!parts || parts.length < 6) {
      throw new Error('Invalid line format');
    }

    const id = parts[1],
      manufacturer = parts[2],
      model = parts[3],
      purchasePrice = parseFloat(parts[4]),
      maxRange = parseInt(parts[5]);

    const eBike: EBike = {
      id: id,
      manufacturer: manufacturer,
      model: model,
      purchasePrice: purchasePrice,
      maxRange: maxRange,
      vehicleState: VehicleState.AVAILABLE,
    };

    this.eBikeService.add(eBike).subscribe({
      next: (eBike: EBike) => {
        console.log('Successful:', eBike);
      },
      error: (err) => {
        console.error('Conflict:', err.message);
      },
    });
  }
  parseCar(line: string) {
    const parsed = Papa.parse<string[]>(line, {
      delimiter: ',',
      skipEmptyLines: true,
      header: false,
    });
    const parts = parsed.data[0];

    if (!parts || parts.length < 7) {
      throw new Error('Invalid line format');
    }

    const id = parts[1],
      manufacturer = parts[2],
      model = parts[3],
      purchasePrice = parseFloat(parts[4]),
      purchaseDateTime = parts[5],
      description = parts[6];
    const car: Car = {
      id: id,
      manufacturer: manufacturer,
      model: model,
      purchasePrice: purchasePrice,
      purchaseDateTime: new Date(purchaseDateTime),
      description: description,
      vehicleState: VehicleState.AVAILABLE,
    };

    this.carService.add(car).subscribe({
      next: (car: Car) => {
        console.log('Successful:', car);
      },
      error: (err) => {
        console.error('Conflict:', err.message);
      },
    });
  }
}
