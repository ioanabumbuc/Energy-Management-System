import {Component} from '@angular/core';
import {Role, Measurement, Device} from "../../_models/Model";
import {MeasurementService} from "../../_services/measurement.service";
import {ActivatedRoute} from "@angular/router";
import {Chart, ChartConfiguration} from "chart.js/auto";
import * as moment from "moment";
import {DeviceService} from "../../_services/device.service";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";


@Component({
  selector: 'app-device-consumption-details',
  templateUrl: './device-consumption-details.component.html',
  styleUrls: ['./device-consumption-details.component.scss'],
})
export class DeviceConsumptionDetailsComponent {
  role = Role.NONE;
  showPage = true;
  measurements: Measurement[] = [];
  device: Device = {
    description: '',
    address: '',
    maxConsumption: -1,
    userId: -1
  }
  selectedDate: any;
  deviceId = -1;
  // @ts-ignore
  lineChart: Chart;
  noChartData = true;

  constructor(private measurementService: MeasurementService,
              private route: ActivatedRoute,
              private deviceService: DeviceService) {
  }

  ngOnInit() {
    this.showPage = this.isAllowed();
    if (!this.showPage) {
      return;
    }
    this.deviceId = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.deviceId) {
      return;
    }
    this.getDeviceDetails();
  }

  isAllowed() {
    const role = sessionStorage.getItem("role");
    if (role === Role.CLIENT) {
      this.role = Role.CLIENT;
    }
    if (role === Role.ADMIN) {
      this.role = Role.ADMIN;
    }
    return !!role;
  }

  getDeviceDetails() {
    this.deviceService.getDeviceDetails(this.deviceId).subscribe({
      next: (resp) => {
        this.device = resp;
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  getMeasurements(date: string) {
    this.measurementService.getMeasurementsByDate(this.deviceId, date).subscribe({
      next: (resp: Measurement[]) => {
        this.measurements = resp;
        if(!resp.length){
          this.noChartData = true;
          return;
        }
        this.noChartData = false
        this.createChart();
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  createChart() {
    if (this.lineChart) {
      this.lineChart.destroy();
    }

    const canvas = document.getElementById("line-chart") as HTMLCanvasElement;

    const data = {
      labels: this.measurements.map((m) => m.timestamp),
      datasets: [
        {
          label: 'Consumed energy',
          data: this.measurements.map((m) => parseFloat(m.consumedEnergy.toFixed(2)))
        }
      ]
    }

    const config = {
      type: 'line',
      data: data,
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
        },
        scales: {
          y: {
            min: 0,
            max: 150
          }
        }
      }
    };

    // @ts-ignore
    this.lineChart = new Chart(canvas, config);
  }

  convertTimestampToDate(timestamp: number) {
    const timestampInMilliseconds = timestamp * 1000;

    return moment(timestampInMilliseconds).format('YYYY-MM-DD HH:mm:ss');
  }


  dateChange(event: MatDatepickerInputEvent<unknown, unknown | null>) {
    this.selectedDate = event.value;
    // @ts-ignore
    const formattedDate = moment(event.value).format('DD/MM/YYYY');

    this.getMeasurements(formattedDate)
  }
}
