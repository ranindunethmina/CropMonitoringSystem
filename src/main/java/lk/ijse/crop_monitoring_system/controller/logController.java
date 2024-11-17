package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.LogDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.LogNotFoundException;
import lk.ijse.crop_monitoring_system.service.LogService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @RequestParam("logDate") String logDate,
            @RequestParam("details") String details,
            @RequestParam("observedImage") MultipartFile observedImage,
            @RequestParam("fieldCodes") List<String> fieldCodes,
            @RequestParam("cropCodes") List<String> cropCodes,
            @RequestParam("staffIds") List<String> staffIds) {
        try {
            String base64logImage = AppUtil.toBase64(observedImage);

            LogDTO logDTO = new LogDTO();
            logDTO.setLogDate(LocalDate.parse(logDate));
            logDTO.setDetails(details);
            logDTO.setObservedImage(base64logImage);
            logDTO.setFieldCodes(fieldCodes);
            logDTO.setCropCodes(cropCodes);
            logDTO.setStaffIds(staffIds);

            logService.saveLog(logDTO);
            log.info("Save logs success");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (LogNotFoundException e) {
            log.error("Log not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while saving log.");
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
        log.info("Get all logs successfully");
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
            log.info("Status of log with ID: {} updated successfully", logCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (LogNotFoundException e){
            log.warn("No logs found with ID: {}", logCode);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while updating logs with ID: {}", logCode);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{logId}" )
    public ResponseEntity<Void> deleteLog(@PathVariable ("logId") String logId) {
        try {
            logService.deleteLog(logId);
            log.info("Log with ID: {} deleted successfully", logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (LogNotFoundException e){
            log.warn("No logs found with ID: {}", logId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while deleting log with ID: {}", logId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}