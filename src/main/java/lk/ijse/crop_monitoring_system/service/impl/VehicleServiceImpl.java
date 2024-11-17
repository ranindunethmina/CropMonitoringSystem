package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.VehicleDTO;
import lk.ijse.crop_monitoring_system.entity.Vehicle;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.VehicleNotFoundException;
import lk.ijse.crop_monitoring_system.repository.VehicleRepository;
import lk.ijse.crop_monitoring_system.service.VehicleService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lk.ijse.crop_monitoring_system.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final Mapping mapping;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setVehicleCode(AppUtil.createVehicleCode());
        var vehicle = mapping.convertToVehicle(vehicleDTO);
        var savedVehicle = vehicleRepository.save(vehicle);
        if (savedVehicle == null) {
            throw new DataPersistFailedException("Cannot save vehicle");
        }
    }

    @Override
    public void updateVehicle(String vehicleId, VehicleDTO vehicleDTO) {
        Optional<Vehicle> tmpVehicleEntity = vehicleRepository.findById(vehicleId);
        if (!tmpVehicleEntity.isPresent()) {
            throw new VehicleNotFoundException("Vehicle not found");
        } else {
            tmpVehicleEntity.get().setLicensePlate(vehicleDTO.getLicensePlate());
            tmpVehicleEntity.get().setCategory(vehicleDTO.getCategory());
            tmpVehicleEntity.get().setFuelType(vehicleDTO.getFuelType());
            tmpVehicleEntity.get().setStatus(vehicleDTO.getStatus());
            tmpVehicleEntity.get().setRemarks(vehicleDTO.getRemarks());
        }
    }

    @Override
    public void deleteVehicle(String vehicleId) {
        Optional<Vehicle> findId = vehicleRepository.findById(vehicleId);
        if (!findId.isPresent()) {
            throw new VehicleNotFoundException("Vehicle not found");
        } else {
            vehicleRepository.deleteById(vehicleId);
        }
    }

    @Override
    public VehicleDTO getSelectedVehicle(String vehicleId) {
        if (vehicleRepository.existsById(vehicleId)) {
            return mapping.convertToVehicleDTO(vehicleRepository.getReferenceById(vehicleId));
        } else {
            throw new VehicleNotFoundException("Vehicle not found");
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return mapping.convertToVehicleDTOList(vehicleRepository.findAll());
    }
}