import {
  Component,
  ElementRef,
  inject,
  ViewChild,
  ChangeDetectorRef,
} from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { CarsTableComponent } from '../../table-components/cars-table/cars-table.component';
import { EBikesTableComponent } from '../../table-components/e-bikes-table/e-bikes-table.component';
import { EScootersTableComponent } from '../../table-components/e-scooters-table/e-scooters-table.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CarService } from '../../../services/car.service';
import { EBikeService } from '../../../services/e-bike.service';
import { EScooterService } from '../../../services/e-scooter.service';
import { Car } from '../../../model/car.model';
import { EBike } from '../../../model/ebike.model';
import { EScooter } from '../../../model/escooter.model';
import { VehicleState } from '../../../enum/vehicle-state.enum';

import Papa from 'papaparse';
import { Observable, forkJoin, of } from 'rxjs';
import { finalize, catchError, tap } from 'rxjs/operators';

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
  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  @ViewChild(CarsTableComponent) carsTable?: CarsTableComponent;
  @ViewChild(EBikesTableComponent) eBikesTable?: EBikesTableComponent;
  @ViewChild(EScootersTableComponent) eScootersTable?: EScootersTableComponent;

  private carService: CarService = inject(CarService);
  private eBikeService: EBikeService = inject(EBikeService);
  private eScooterService: EScooterService = inject(EScooterService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  isLoading = false;

  openFileDialog(): void {
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      if (
        file.type !== 'text/csv' &&
        !file.name.toLowerCase().endsWith('.csv')
      ) {
        input.value = '';
        return;
      }

      this.isLoading = true;
      const reader = new FileReader();

      reader.onload = (e) => {
        const content: string = e.target?.result as string;

        const parseResult = Papa.parse<string[]>(content, {
          delimiter: ',',
          skipEmptyLines: true,
          header: false,
        });

        if (parseResult.errors.length > 0) {
          this.isLoading = false;
          input.value = '';
          return;
        }

        const addObservables: Observable<any>[] = [];

        for (const row of parseResult.data) {
          if (!row || row.length === 0 || !row[0]) continue;

          const mark = row[0].trim().toUpperCase();

          try {
            if (mark === 'C') {
              const car = this.parseCarRow(row);
              if (car) {
                addObservables.push(
                  this.carService.add(car).pipe(
                    tap((addedCar) => console.log('Car added:', addedCar)),
                    catchError((err) => {
                      console.error(
                        `Error adding car (ID: ${car.id}):`,
                        err.message || err
                      );
                      return of(null);
                    })
                  )
                );
              }
            } else if (mark === 'B') {
              const eBike = this.parseEBikeRow(row);
              if (eBike) {
                addObservables.push(
                  this.eBikeService.add(eBike).pipe(
                    tap((addedBike) => console.log('EBike added:', addedBike)),
                    catchError((err) => {
                      console.error(
                        `Error adding eBike (ID: ${eBike.id}):`,
                        err.message || err
                      );
                      return of(null);
                    })
                  )
                );
              }
            } else if (mark === 'S') {
              const eScooter = this.parseEScooterRow(row);
              if (eScooter) {
                addObservables.push(
                  this.eScooterService.add(eScooter).pipe(
                    tap((addedScooter) =>
                      console.log('EScooter added:', addedScooter)
                    ),
                    catchError((err) => {
                      console.error(
                        `Error adding eScooter (ID: ${eScooter.id}):`,
                        err.message || err
                      );
                      return of(null);
                    })
                  )
                );
              }
            } else {
              console.warn(`Invalid mark '${mark}' found in row:`, row);
            }
          } catch (error: any) {
            console.error(
              `Error processing row: ${row.join(',')}`,
              error.message || error
            );
          }
        }

        if (addObservables.length > 0) {
          forkJoin(addObservables)
            .pipe(
              finalize(() => {
                this.refreshTables();
                this.isLoading = false;
                input.value = '';
                this.cdr.detectChanges();
              })
            )
            .subscribe({
              next: (results) => {
                const successfulAdds = results.filter((r) => r !== null).length;
                if (successfulAdds < results.length) {
                  console.warn(
                    `${
                      results.length - successfulAdds
                    } vehicles failed to add. Check previous logs.`
                  );
                }
              },
              error: (err) => {
                console.error('Unexpected error in forkJoin:', err);
                this.isLoading = false;
                input.value = '';
              },
            });
        } else {
          console.log('No valid vehicle data found in the file to process.');
          this.isLoading = false;
          input.value = '';
        }
      };

      reader.onerror = (e) => {
        console.error('Error reading file:', reader.error);
        this.isLoading = false;
        input.value = '';
      };

      reader.readAsText(file);
    }
  }

  private parseCarRow(parts: string[]): Car | null {
    if (!parts || parts.length < 8) {
      console.warn('Invalid car row format:', parts);
      return null;
    }

    const [
      ,
      id,
      manufacturer,
      model,
      purchasePriceStr,
      purchaseDateTimeStr,
      description,
      imagePath,
    ] = parts;

    const purchasePrice = parseFloat(purchasePriceStr);
    const purchaseDateTime = new Date(purchaseDateTimeStr);

    if (isNaN(purchasePrice) || isNaN(purchaseDateTime.getTime())) {
      console.warn(
        `Invalid number or date format in car row: ${parts.join(',')}`
      );
      return null;
    }

    return {
      id: id.trim(),
      manufacturer: manufacturer.trim(),
      model: model.trim(),
      purchasePrice: purchasePrice,
      purchaseDateTime: purchaseDateTime,
      description: description.trim(),
      vehicleState: VehicleState.AVAILABLE,
      picture: imagePath ? imagePath.trim() : undefined,
    };
  }

  private parseEBikeRow(parts: string[]): EBike | null {
    if (!parts || parts.length < 7) {
      console.warn('Invalid eBike row format:', parts);
      return null;
    }

    const [
      ,
      id,
      manufacturer,
      model,
      purchasePriceStr,
      maxRangeStr,
      imagePath,
    ] = parts;
    const purchasePrice = parseFloat(purchasePriceStr);
    const maxRange = parseInt(maxRangeStr, 10);

    if (isNaN(purchasePrice) || isNaN(maxRange)) {
      console.warn(`Invalid number format in eBike row: ${parts.join(',')}`);
      return null;
    }

    return {
      id: id.trim(),
      manufacturer: manufacturer.trim(),
      model: model.trim(),
      purchasePrice: purchasePrice,
      maxRange: maxRange,
      vehicleState: VehicleState.AVAILABLE,
      picture: imagePath ? imagePath.trim() : undefined,
    };
  }

  private parseEScooterRow(parts: string[]): EScooter | null {
    if (!parts || parts.length < 7) {
      console.warn('Invalid eScooter row format:', parts);
      return null;
    }

    const [
      ,
      id,
      manufacturer,
      model,
      purchasePriceStr,
      maxSpeedStr,
      imagePath,
    ] = parts;
    const purchasePrice = parseFloat(purchasePriceStr);
    const maxSpeed = parseInt(maxSpeedStr, 10);

    if (isNaN(purchasePrice) || isNaN(maxSpeed)) {
      console.warn(`Invalid number format in eScooter row: ${parts.join(',')}`);
      return null;
    }

    return {
      id: id.trim(),
      manufacturer: manufacturer.trim(),
      model: model.trim(),
      purchasePrice: purchasePrice,
      maxSpeed: maxSpeed,
      vehicleState: VehicleState.AVAILABLE,
      picture: imagePath ? imagePath.trim() : undefined,
    };
  }

  private refreshTables(): void {
    this.carsTable?.loadData();
    this.eBikesTable?.loadData();
    this.eScootersTable?.loadData();
  }
}
