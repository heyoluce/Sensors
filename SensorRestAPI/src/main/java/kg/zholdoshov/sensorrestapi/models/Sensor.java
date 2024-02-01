package kg.zholdoshov.sensorrestapi.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="sensor", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    public Sensor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @NotBlank(message = "Поле \"name\" не должно быть пустым")
    @Size(min=3, max=30, message = "Минимальный размер поля \"name\": 0 | Максимальный размер поля \"name\": 30")
    @Column(name="name", unique = true)
    private String name;

    public Sensor() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
