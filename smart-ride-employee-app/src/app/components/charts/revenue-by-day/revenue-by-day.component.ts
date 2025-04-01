import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { RentalService } from '../../../services/rental.service';

@Component({
  selector: 'app-revenue-by-day',
  providers: [provideNativeDateAdapter()],
  imports: [
    MatFormFieldModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    NgxChartsModule,
  ],
  templateUrl: './revenue-by-day.component.html',
  styleUrl: './revenue-by-day.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RevenueByDayComponent implements OnInit {
  private rentalService: RentalService = inject(RentalService);
  view: [number, number] = [600, 200];

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  public results = [];

  ngOnInit(): void {
    this.range.valueChanges.subscribe((value) => {
      if (value.start && value.end) {
        this.getRevenueByDay();
      }
    });
  }

  public getRevenueByDay() {
    this.rentalService
      .getRevenueByDay(this.range.value.start!, this.range.value.end!)
      .subscribe({
        next: (data: any) => {
          this.results = data;
        },
        error: (err) => {
          console.error('Error:', err.message);
        },
      });
  }
}
