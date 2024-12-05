package lk.ijse.crop_monitoring_system.util;

import lk.ijse.crop_monitoring_system.dto.*;
import lk.ijse.crop_monitoring_system.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    public Field convertToField(FieldDTO fieldDTO) {
        return modelMapper.map(fieldDTO, Field.class);
    }

    public FieldDTO convertToFieldDTO(Field field) {
        return modelMapper.map(field, FieldDTO.class);
    }

    public List<FieldDTO> convertToFieldDTOList(List<Field> fieldList) {
        return modelMapper.map(fieldList, new TypeToken<List<FieldDTO>>() {}.getType());
    }


    public Crop convertToCrop(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, Crop.class);
    }

    public CropDTO convertToCropDTO(Crop crop) {
        return modelMapper.map(crop, CropDTO.class);
    }

    public List<CropDTO> convertToCropDTOList(List<Crop> cropList) {
        return modelMapper.map(cropList, new TypeToken<List<CropDTO>>() {}.getType());
    }


    public Staff convertToStaff(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, Staff.class);
    }

    public StaffDTO convertToStaffDTO(Staff staff) {
        return modelMapper.map(staff, StaffDTO.class);
    }

    public List<StaffDTO> convertToStaffDTOList(List<Staff> staffList) {
        return modelMapper.map(staffList, new TypeToken<List<StaffDTO>>() {}.getType());
    }


    public Log convertToLog(LogDTO logDTO) {
        return modelMapper.map(logDTO, Log.class);
    }

    public LogDTO convertToLogDTO(Log log) {
        return modelMapper.map(log, LogDTO.class);
    }

    public List<LogDTO> convertToLogDTOList(List<Log> logList) {
        return modelMapper.map(logList, new TypeToken<List<LogDTO>>() {}.getType());
    }


    public Vehicle convertToVehicle(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, Vehicle.class);
    }

    public VehicleDTO convertToVehicleDTO(Vehicle vehicle) {
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    public List<VehicleDTO> convertToVehicleDTOList(List<Vehicle> vehicleList) {
        return modelMapper.map(vehicleList, new TypeToken<List<VehicleDTO>>() {}.getType());
    }


    public Equipment convertToEquipment(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, Equipment.class);
    }

    public EquipmentDTO convertToEquipmentDTO(Equipment equipment) {
        return modelMapper.map(equipment, EquipmentDTO.class);
    }

    public List<EquipmentDTO> convertToEquipmentDTOList(List<Equipment> equipmentList) {
        return modelMapper.map(equipmentList, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }


    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> convertToUserDTOList(List<User> userList) {
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>() {}.getType());
    }
}