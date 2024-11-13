package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.CropDTO;

import java.util.List;

public interface CropService {
    void saveCrop(CropDTO cropDTO);
    void updateCrop(String cropId, CropDTO cropDTO);
    void deleteCrop(String cropId);
    CropDTO getSelectedCrop(String cropId);
    List<CropDTO> getAllCrop();
}