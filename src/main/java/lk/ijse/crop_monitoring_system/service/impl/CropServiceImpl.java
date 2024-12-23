package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.CropDTO;
import lk.ijse.crop_monitoring_system.entity.Crop;
import lk.ijse.crop_monitoring_system.entity.Field;
import lk.ijse.crop_monitoring_system.exception.CropNotFoundException;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.FieldNotFoundException;
import lk.ijse.crop_monitoring_system.repository.CropRepository;
import lk.ijse.crop_monitoring_system.repository.FieldRepository;
import lk.ijse.crop_monitoring_system.service.CropService;
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
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO,String fieldCode) {
        cropDTO.setCropCode(AppUtil.createCropCode());
        var crop = mapping.convertToCrop(cropDTO);
        Field field = fieldRepository.findById(fieldCode).orElseThrow(
                () -> new FieldNotFoundException("Field not found")
        );
        crop.setField(field);
        var savedCrop = cropRepository.save(crop);
        if (savedCrop == null) {
            throw new DataPersistFailedException("Cannot save crop");
        }
    }

    @Override
    public void updateCrop(String cropId,String fieldCode, CropDTO cropDTO) {
        Optional<Crop> tmpCropEntity = cropRepository.findById(cropId);
        if (!tmpCropEntity.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        } else {
            Field field = fieldRepository.findById(fieldCode).orElseThrow(
                    () -> new FieldNotFoundException("Field not found")
            );
            tmpCropEntity.get().setField(field);
            tmpCropEntity.get().setCommonName(cropDTO.getCommonName());
            tmpCropEntity.get().setScientificName(cropDTO.getScientificName());
            tmpCropEntity.get().setCropImage(cropDTO.getCropImage());
            tmpCropEntity.get().setCategory(cropDTO.getCategory());
            tmpCropEntity.get().setSeason(cropDTO.getSeason());
        }
    }

    @Override
    public void deleteCrop(String cropId) {
        Optional<Crop> findId = cropRepository.findById(cropId);
        if (!findId.isPresent()) {
            throw new CropNotFoundException("Crop not found");
        } else {
            cropRepository.deleteById(cropId);
        }
    }

    @Override
    public CropDTO getSelectedCrop(String cropId) {
        if (cropRepository.existsById(cropId)) {
            return mapping.convertToCropDTO(cropRepository.getReferenceById(cropId));
        }else {
            throw new CropNotFoundException("Crop not found");
        }
    }

    @Override
    public List<CropDTO> getAllCrop() {
        return mapping.convertToCropDTOList(cropRepository.findAll());
    }
}