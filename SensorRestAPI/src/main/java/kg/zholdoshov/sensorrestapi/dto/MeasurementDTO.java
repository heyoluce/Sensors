package kg.zholdoshov.sensorrestapi.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import kg.zholdoshov.sensorrestapi.models.Sensor;

public class MeasurementDTO {

    @Column(name="value")
    @Min(value = -100, message = "Минимальное значение поля  должно быть -100")
    @Max(value = 100, message = "Максимальное значение поля  должно быть 100")
    private double value;

    public MeasurementDTO(boolean raining) {
        this.raining = raining;
    }

    public MeasurementDTO() {
    }

    public MeasurementDTO(double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }



    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Column(name = "raining")
    private boolean raining;
    @ManyToOne
    @JoinColumn(name="sensor_id")
    private Sensor sensor;

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
