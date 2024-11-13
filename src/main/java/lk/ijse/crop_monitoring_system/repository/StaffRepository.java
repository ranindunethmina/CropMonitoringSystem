package lk.ijse.crop_monitoring_system.repository;

import lk.ijse.courseWork.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> { }