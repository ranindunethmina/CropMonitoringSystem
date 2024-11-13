package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void updateVehicle(String vehicleId, VehicleDTO vehicleDTO);
    void deleteVehicle(String vehicleId);
    VehicleDTO getSelectedVehicle(String vehicleId);
    List<VehicleDTO> getAllVehicles();
}