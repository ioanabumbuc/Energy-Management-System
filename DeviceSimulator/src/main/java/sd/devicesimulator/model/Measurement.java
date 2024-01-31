package sd.devicesimulator.model;


public class Measurement {
    private String timestamp;
    private Long deviceId;
    private Double measurementValue;

    public Measurement(Long deviceId, Double measurementValue) {
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public Measurement(String timestamp, Long deviceId, Double measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }

    @Override
    public String toString() {
        return "{" +
                " \"timestamp\": \"" + timestamp +
                "\" , \"deviceId\":" + deviceId +
                ", \"measurementValue\":" + measurementValue +
                '}';
    }
}
