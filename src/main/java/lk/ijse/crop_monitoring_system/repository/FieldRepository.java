package lk.ijse.crop_monitoring_system.repository;

import lk.ijse.crop_monitoring_system.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> { }