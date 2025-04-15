import {
  ChangeDetectionStrategy,
  Component,
  inject,
  OnInit,
  ChangeDetectorRef,
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
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-revenue-by-day',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [
    MatFormFieldModule,
    MatDatepickerModule,
    MatInputModule,
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
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  view: [number, number] = [700, 300];
  colorScheme: string = 'cool';

  readonly range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  public results: any[] = [];

  ngOnInit(): void {
    // Initialize with last 7 days by default
    const today = new Date();
    const lastWeek = new Date();
    lastWeek.setDate(today.getDate() - 7);

    this.range.setValue({
      start: lastWeek,
      end: today,
    });

    this.range.valueChanges.subscribe((value) => {
      if (value.start && value.end) {
        this.getRevenueByDay();
      }
    });

    // Initial data load
    this.getRevenueByDay();
  }

  public getRevenueByDay() {
    if (!this.range.value.start || !this.range.value.end) {
      return;
    }

    this.rentalService
      .getRevenueByDay(this.range.value.start, this.range.value.end)
      .subscribe({
        next: (data: any) => {
          this.results = data;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error:', err.message);
        },
      });
  }
}
