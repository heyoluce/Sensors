package kg.zholdoshov.sensorrestapi.util;

import kg.zholdoshov.sensorrestapi.dto.SensorDTO;
import kg.zholdoshov.sensorrestapi.models.Sensor;
import kg.zholdoshov.sensorrestapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

@Component
public class SensorUniqueNameValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorUniqueNameValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        if (sensorService.findByName(sensor.getName())!=null) {
            errors.rejectValue("name","","Это имя уже используется");
        }
    }
}
