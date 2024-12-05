package lk.ijse.crop_monitoring_system.service.impl;

import lk.ijse.crop_monitoring_system.dto.LogDTO;
import lk.ijse.crop_monitoring_system.entity.Crop;
import lk.ijse.crop_monitoring_system.entity.Field;
import lk.ijse.crop_monitoring_system.entity.Log;
import lk.ijse.crop_monitoring_system.entity.Staff;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.LogNotFoundException;
import lk.ijse.crop_monitoring_system.repository.CropRepository;
import lk.ijse.crop_monitoring_system.repository.FieldRepository;
import lk.ijse.crop_monitoring_system.repository.LogRepository;
import lk.ijse.crop_monitoring_system.repository.StaffRepository;
import lk.ijse.crop_monitoring_system.service.LogService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lk.ijse.crop_monitoring_system.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final CropRepository cropRepository;
    private final Mapping mapping;

    @Override
    public void saveLog(LogDTO logDTO) {
        List<Field> field = new ArrayList<>();
        List<Crop> crops = new ArrayList<>();
        List<Staff> staff = new ArrayList<>();

        for(String fieldCode:logDTO.getFieldCodes()){
            fieldRepository.findById(fieldCode).ifPresent(field::add);
        }
        for(String cropCode:logDTO.getCropCodes()){
            cropRepository.findById(cropCode).ifPresent(crops::add);
        }
        for(String staffId:logDTO.getStaffIds()){
            staffRepository.findById(staffId).ifPresent(staff::add);
        }

        logDTO.setLogCode(AppUtil.createLogID());
        var log = mapping.convertToLog(logDTO);
        log.setDetails(logDTO.getDetails());
        log.setField(field);
        log.setCrop(crops);
        log.setStaff(staff);

        var savedLog = logRepository.save(log);
        if (savedLog == null){
            throw new DataPersistFailedException("Can't save log");
        }
    }

    @Override
    public void updateLog(String logId, LogDTO logDTO) {
        Optional<Log> tmpLogEntity = logRepository.findById(logId);
        if (!tmpLogEntity.isPresent()){
            throw new LogNotFoundException("Log not found");
        }else {
            tmpLogEntity.get().setLogDate(logDTO.getLogDate());
            tmpLogEntity.get().setDetails(logDTO.getDetails());
            tmpLogEntity.get().setObservedImage(logDTO.getObservedImage());
        }
    }

    @Override
    public void deleteLog(String logId) {
        Optional<Log> findId = logRepository.findById(logId);
        if (!findId.isPresent()){
            throw new LogNotFoundException("Log not found");
        } else {
            logRepository.deleteById(logId);
        }
    }

    @Override
    public LogDTO getSelectedLog(String logId) {
        if (logRepository.existsById(logId)){
            return mapping.convertToLogDTO(logRepository.getReferenceById(logId));
        } else {
            throw new LogNotFoundException("Log not found");
        }
    }

    @Override
    public List<LogDTO> getAllLog() {
        return mapping.convertToLogDTOList(logRepository.findAll());
    }
}