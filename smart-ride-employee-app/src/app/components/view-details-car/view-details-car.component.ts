import { Component, inject, OnInit } from '@angular/core';
import { Car } from '../../model/car.model';
import { ActivatedRoute } from '@angular/router';
import { CarService } from '../../services/car.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-details-car',
  imports: [CommonModule],
  templateUrl: './view-details-car.component.html',
  styleUrl: './view-details-car.component.css',
})
export class ViewDetailsCarComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);
  private carService: CarService = inject(CarService);

  public car: Car | null = null;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    const id = this.activatedRoute.snapshot.params['id'];
    this.carService.getById(id).subscribe({
      next: (car) => {
        this.car = car;
        this.carService.getImageById(id).subscribe({
          next: (image) => {
            const imageUrl = URL.createObjectURL(image);
            this.car!.picture = imageUrl;
          },
          error: (err) => {
            console.error('Image not found:', err);
          },
        });
      },
      error: (err) => {
        console.error('Car not found: ', err);
      },
    });
  }
}
