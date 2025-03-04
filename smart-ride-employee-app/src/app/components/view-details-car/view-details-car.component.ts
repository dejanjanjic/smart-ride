import { Component, OnInit } from '@angular/core';
import { Car } from '../../model/car.model';

@Component({
  selector: 'app-view-details-car',
  imports: [],
  templateUrl: './view-details-car.component.html',
  styleUrl: './view-details-car.component.css',
})
export class ViewDetailsCarComponent implements OnInit {
  public car: Car | null = null;
  ngOnInit(): void {
    loadData();
  }
}
function loadData() {
  throw new Error('Function not implemented.');
}
