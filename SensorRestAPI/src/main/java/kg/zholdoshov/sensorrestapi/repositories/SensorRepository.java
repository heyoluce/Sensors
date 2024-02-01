package kg.zholdoshov.sensorrestapi.repositories;

import kg.zholdoshov.sensorrestapi.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findSensorByName(String name);
}
