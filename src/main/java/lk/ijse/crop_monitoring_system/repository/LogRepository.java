package lk.ijse.crop_monitoring_system.repository;

import lk.ijse.courseWork.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> { }