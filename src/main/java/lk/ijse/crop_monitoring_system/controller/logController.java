package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.LogDTO;
import lk.ijse.crop_monitoring_system.dto.StaffDTO;
import lk.ijse.crop_monitoring_system.service.LogService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class logController {
    private final LogService logService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("details") String details,
            @RequestPart("observedImage") MultipartFile observedImage) {
        try {
            String base64logImage = AppUtil.toBase64(observedImage);

            LogDTO logDTO = new LogDTO();
            logDTO.setLogDate(LocalDate.parse(logDate));
            logDTO.setDetails(details);
            logDTO.setObservedImage(base64logImage);

            logService.saveLog(logDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public LogDTO getLog (@PathVariable("logId") String logId) {
        log.info("Log with ID: {} retrieved successfully", logId);
        return logService.getSelectedLog(logId);
    }

    @GetMapping(value = "allLogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDTO> getAllLogs() {
        return logService.getAllLog();
    }

    @PatchMapping(value = "/{logId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateLog(
            @PathVariable("logId") String logCode,
            @RequestPart("logDate") String updateLogDate,
            @RequestPart("details") String updateDetails,
            @RequestPart("observedImage") MultipartFile updateObservedImage) {
        try {
            String updateBase64logImage = AppUtil.toBase64(updateObservedImage);

            LogDTO logDTO = new LogDTO();
            logDTO.setLogCode(logCode);
            logDTO.setLogDate(LocalDate.parse(updateLogDate));
            logDTO.setDetails(updateDetails);
            logDTO.setObservedImage(updateBase64logImage);

            logService.updateLog(logCode, logDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{logId}" )
    public ResponseEntity<Void> deleteLog(@PathVariable ("logId") String logId) {
        try {
            logService.deleteLog(logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}