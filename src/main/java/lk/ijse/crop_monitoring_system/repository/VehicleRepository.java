package lk.ijse.crop_monitoring_system.repository;

import lk.ijse.crop_monitoring_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> { }