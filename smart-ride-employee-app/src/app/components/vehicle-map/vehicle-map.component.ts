import { Component, inject, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { LeafletModule } from '@bluehalo/ngx-leaflet';
import { VehicleService } from '../../services/vehicle.service';

@Component({
  selector: 'app-vehicle-map',
  imports: [LeafletModule],
  templateUrl: './vehicle-map.component.html',
  styleUrl: './vehicle-map.component.css',
})
export class VehicleMapComponent implements OnInit {
  private vehicleService: VehicleService = inject(VehicleService);
  private map!: L.Map;
  private markersLayer = L.layerGroup();
  private availableIcon = L.icon({
    iconUrl: 'assets/available-marker.png',
    iconSize: [32, 32],
  });
  private unavailableIcon = L.icon({
    iconUrl: 'assets/unavailable-marker.png',
    iconSize: [32, 32],
  });
  options = {
    layers: [
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 25,
        attribution: '...',
      }),
    ],
    zoom: 17,
    center: L.latLng(44.76662491169107, 17.1870092734517),
  };

  ngOnInit(): void {
    this.vehicleService.getAll().subscribe((vehicles) => {
      this.markersLayer.clearLayers();

      vehicles.forEach((vehicle: any) => {
        const marker = L.marker([vehicle.positionX, vehicle.positionY], {
          icon:
            vehicle.vehicleState === 'AVAILABLE'
              ? this.availableIcon
              : this.unavailableIcon,
        });
        marker
          .bindPopup(
            vehicle.id + ' ' + vehicle.manufacturer.name + ' ' + vehicle.model
          )
          .addTo(this.markersLayer);
      });

      if (this.map) {
        this.markersLayer.addTo(this.map);
      }
    });
  }

  onMapReady(map: L.Map) {
    this.map = map;
    // Add the initial empty markers layer to the map
    this.markersLayer.addTo(this.map);
  }
}
