import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceConsumptionDetailsComponent } from './device-consumption-details.component';

describe('DeviceConsumptionDetailsComponent', () => {
  let component: DeviceConsumptionDetailsComponent;
  let fixture: ComponentFixture<DeviceConsumptionDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceConsumptionDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeviceConsumptionDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
