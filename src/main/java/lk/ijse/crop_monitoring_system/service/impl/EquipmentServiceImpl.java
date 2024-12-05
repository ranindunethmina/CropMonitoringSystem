package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_system.entity.Equipment;
import lk.ijse.crop_monitoring_system.entity.Status;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.EquipmentNotFoundException;
import lk.ijse.crop_monitoring_system.repository.EquipmentRepository;
import lk.ijse.crop_monitoring_system.service.EquipmentService;
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
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setEquipmentId(AppUtil.createEquipmentId());
        var equipment = mapping.convertToEquipment(equipmentDTO);
        var savedEquipment = equipmentRepository.save(equipment);
        if (savedEquipment == null){
            throw new DataPersistFailedException("Can't save equipment");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDTO equipmentDTO) {
        Optional<Equipment> tmpEquipmentEntity = equipmentRepository.findById(equipmentId);
        if (!tmpEquipmentEntity.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not found");
        }else {
            tmpEquipmentEntity.get().setName(equipmentDTO.getName());
            tmpEquipmentEntity.get().setType(equipmentDTO.getType());
            tmpEquipmentEntity.get().setStatus(Status.valueOf(equipmentDTO.getStatus()));
        }
    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<Equipment> findId = equipmentRepository.findById(equipmentId);
        if (!findId.isPresent()) {
            throw new EquipmentNotFoundException("Equipment not found");
        } else {
            equipmentRepository.deleteById(equipmentId);
        }
    }

    @Override
    public EquipmentDTO getSelectedEquipment(String equipmentId) {
        if (equipmentRepository.existsById(equipmentId)) {
            return mapping.convertToEquipmentDTO(equipmentRepository.getReferenceById(equipmentId));
        }else {
            throw new EquipmentNotFoundException("Equipment not found");
        }
    }

    @Override
    public List<EquipmentDTO> getAllEquipment() {
        return mapping.convertToEquipmentDTOList(equipmentRepository.findAll());
    }
}