package kg.zholdoshov.sensorrestapi.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotBlank(message = "Поле  не должно быть пустым")
    @Size(min=3, max=30, message = "Минимальный размер поля : 0 | Максимальный размер поля : 30")
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SensorDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }
}
