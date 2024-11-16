package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.FieldDTO;
import lk.ijse.crop_monitoring_system.entity.Field;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.FieldNotFoundException;
import lk.ijse.crop_monitoring_system.repository.FieldRepository;
import lk.ijse.crop_monitoring_system.service.FieldService;
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
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final Mapping mapping;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setFieldCode(AppUtil.createFieldId());
        var field = mapping.convertToField(fieldDTO);
        var savedField = fieldRepository.save(field);
        if (savedField == null) {
            throw new DataPersistFailedException("Cannot save field");
        }
    }

    @Override
    public void updateField(String fieldId, FieldDTO fieldDTO) {
        Optional<Field> tmpFieldEntity = fieldRepository.findById(fieldId);
        if (!tmpFieldEntity.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        } else {
            tmpFieldEntity.get().setFieldName(fieldDTO.getFieldName());
            tmpFieldEntity.get().setLocation(fieldDTO.getLocation());
            tmpFieldEntity.get().setExtentSize(fieldDTO.getExtentSize());
            tmpFieldEntity.get().setFieldImage1(fieldDTO.getFieldImage1());
            tmpFieldEntity.get().setFieldImage2(fieldDTO.getFieldImage2());
        }
    }

    @Override
    public void deleteField(String fieldId) {
        Optional<Field> findId = fieldRepository.findById(fieldId);
        if (!findId.isPresent()) {
            throw new FieldNotFoundException("Field not found");
        }else {
            fieldRepository.deleteById(fieldId);
        }
    }

    @Override
    public FieldDTO getSelectedField(String fieldId) {
        if (fieldRepository.existsById(fieldId)) {
            return mapping.convertToFieldDTO(fieldRepository.getReferenceById(fieldId));
        }else {
            throw new FieldNotFoundException("Field not found");
        }
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.convertToFieldDTOList(fieldRepository.findAll());
    }
}