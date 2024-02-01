package kg.zholdoshov.sensorrestapi.controllers;
import jakarta.validation.Valid;
import kg.zholdoshov.sensorrestapi.dto.MeasurementDTO;
import kg.zholdoshov.sensorrestapi.models.Measurement;
import kg.zholdoshov.sensorrestapi.models.Sensor;
import kg.zholdoshov.sensorrestapi.services.MeasurementService;
import kg.zholdoshov.sensorrestapi.services.SensorService;
import kg.zholdoshov.sensorrestapi.util.ErrorResponse;
import kg.zholdoshov.sensorrestapi.util.MeasurementNotCreatedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementsController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public int getCountOfRainingDays() {
        return measurementService.getCountOfRainyDays();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg=new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }
        if (sensorService.findByName(measurementDTO.getSensor().getName())==null) {
            throw new MeasurementNotCreatedException("Сенсора с таким именем нет в базе данных");
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handle(MeasurementNotCreatedException e) {
        ErrorResponse response= new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Sensor sensor = measurementDTO.getSensor();
        Measurement measurement =new Measurement();
        measurement.setSensor(sensor);
        modelMapper.map(measurementDTO, measurement);
        return measurement;

    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

}
