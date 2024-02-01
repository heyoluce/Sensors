package kg.zholdoshov.sensorrestapi.services;

import kg.zholdoshov.sensorrestapi.models.Sensor;
import kg.zholdoshov.sensorrestapi.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;
@Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }




    public List<Sensor>  findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElse(null);
    }

    public Sensor findByName(String name) {
        return sensorRepository.findSensorByName(name)
                .orElse(null);
    }

    @Transactional
    public void update(int id,Sensor updatedSensor) {
        updatedSensor.setId(id);
        sensorRepository.save(updatedSensor);
    }
    @Transactional
    public void save(Sensor sensor) {

    sensorRepository.save(sensor);
    }

    @Transactional
    public void delete(Sensor sensor) {
        sensorRepository.delete(sensor);
    }
}
