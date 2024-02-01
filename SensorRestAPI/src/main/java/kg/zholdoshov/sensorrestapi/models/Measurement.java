package kg.zholdoshov.sensorrestapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name="measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="value")
    @Min(value = -100, message = "Минимальное значение поля  должно быть -100")
    @Max(value = 100, message = "Максимальное значение поля  должно быть 100")
    private double value;

    public Measurement() {
    }

    public Measurement(boolean raining) {
        this.raining = raining;
    }

    public Measurement(int id, double value, boolean raining, Sensor sensor, LocalDateTime created_at) {
        this.id = id;
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
    @Column(name = "raining")
    private boolean raining;
    @ManyToOne()
    @JoinColumn(name="sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime created_at;

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
