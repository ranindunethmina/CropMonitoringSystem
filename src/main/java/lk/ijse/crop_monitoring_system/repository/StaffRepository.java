package lk.ijse.crop_monitoring_system.repository;

import lk.ijse.crop_monitoring_system.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query
    Staff getStaffMemberById(String id);
}