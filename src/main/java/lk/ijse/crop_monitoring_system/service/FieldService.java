package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.FieldDTO;

import java.util.List;

public interface FieldService {
     void saveField(FieldDTO fieldDTO);
     void updateField(String fieldId, FieldDTO fieldDTO);
     void deleteField(String fieldId);
     FieldDTO getSelectedField(String fieldId);
     List<FieldDTO> getAllFields();
}