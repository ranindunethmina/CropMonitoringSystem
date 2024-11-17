package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.StaffDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.StaffNotFoundException;
import lk.ijse.crop_monitoring_system.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class StaffController {
    private final StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO){
        log.info("Received request to save staff: {}", staffDTO);
        if (staffDTO == null){
            log.warn("Received null StaffDTO");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                staffService.saveStaff(staffDTO);
                log.info("Staff saved successfully: {}", staffDTO.getId());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                log.error("failed due to data persistence issue.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                log.error("Something went wrong while saving staff.");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffDTO getStaff (@PathVariable("staffId") String staffId) {
        log.info("Staff with ID: {} retrieved successfully", staffId);
        return staffService.getSelectedStaff(staffId);
    }

    @GetMapping(value = "allStaff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO>  getAllStaff(){
        log.info("Get all staff successfully");
        return staffService.getAllStaff();
    }

    @PatchMapping(value = "/{staffId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable ("staffId") String staffId, @RequestBody StaffDTO staffDTO) {
        log.info("Received request to update Staff: {}", staffId);
        try {
            if (staffDTO == null && (staffId == null || staffId.isEmpty())) {
                log.warn("Received null staffId for update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateStaff(staffId, staffDTO);
            log.info("Staff updated successfully: {}", staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            log.warn("Staff not found for update: {}", staffId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while updating staff.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{staffId}" )
    public ResponseEntity<Void> deleteStaff(@PathVariable ("staffId") String staffId) {
        log.info("Received request to delete staff: {}", staffId);
        try {
            staffService.deleteStaff(staffId);
            log.info("Staff deleted successfully: {}", staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (StaffNotFoundException e){
            log.warn("Staff not found for delete: {}", staffId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while deleting staff.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}