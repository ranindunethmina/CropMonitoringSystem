package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO);
    void deleteEquipment(String equipmentId);
    EquipmentDTO getSelectedEquipment(String equipmentId);
    List<EquipmentDTO> getAllEquipment();
}