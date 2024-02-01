package kg.zholdoshov.sensorrestapi.services;

import kg.zholdoshov.sensorrestapi.models.Measurement;
import kg.zholdoshov.sensorrestapi.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;
    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement>  findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findById(int id) {
        return measurementRepository.findById(id).
                orElse(null);
    }

    public int getCountOfRainyDays() {
        return measurementRepository.countByRainingIsTrue();
    }

    @Transactional
    public void update(int id,Measurement updatedMeasurement) {
        updatedMeasurement.setId(id);
        measurementRepository.save(updatedMeasurement);
    }
    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreated_at(LocalDateTime.now());
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()));
        measurementRepository.save(measurement);
    }

    @Transactional
    public void delete(Measurement measurement) {
        measurementRepository.delete(measurement);
    }
}
