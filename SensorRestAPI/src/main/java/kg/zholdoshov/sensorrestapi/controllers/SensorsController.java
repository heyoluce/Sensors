package kg.zholdoshov.sensorrestapi.controllers;

import jakarta.validation.Valid;

import kg.zholdoshov.sensorrestapi.dto.SensorDTO;
import kg.zholdoshov.sensorrestapi.models.Sensor;
import kg.zholdoshov.sensorrestapi.services.SensorService;
import kg.zholdoshov.sensorrestapi.util.ErrorResponse;
import kg.zholdoshov.sensorrestapi.util.SensorNotCreatedException;
import kg.zholdoshov.sensorrestapi.util.SensorUniqueNameValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorService sensorService;
    private final SensorUniqueNameValidator sensorUniqueNameValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService, SensorUniqueNameValidator sensorUniqueNameValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorUniqueNameValidator = sensorUniqueNameValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        sensorUniqueNameValidator.validate(sensorDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg=new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotCreatedException e) {
        ErrorResponse response= new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
