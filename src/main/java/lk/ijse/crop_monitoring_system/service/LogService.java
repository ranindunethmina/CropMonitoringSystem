package lk.ijse.crop_monitoring_system.service;

import lk.ijse.crop_monitoring_system.dto.LogDTO;

import java.util.List;

public interface LogService {
    void saveLog(LogDTO logDTO);
    void updateLog(String logId, LogDTO logDTO);
    void deleteLog(String logId);
    LogDTO getSelectedLog(String logId);
    List<LogDTO> getAllLog();
}