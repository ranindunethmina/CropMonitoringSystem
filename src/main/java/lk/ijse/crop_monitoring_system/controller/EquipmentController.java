package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_system.dto.StaffDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.EquipmentNotFoundException;
import lk.ijse.crop_monitoring_system.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO){
        log.info("Received request to save Equipment: {}", equipmentDTO);
        if (equipmentDTO == null){
            log.warn("Received null equipmentDTO");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                equipmentService.saveEquipment(equipmentDTO);
                log.info("Equipment saved successfully: {}", equipmentDTO.getEquipmentId());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (EquipmentNotFoundException e){
                log.info("Equipment not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch (DataPersistFailedException e){
                log.error("failed due to data persistence issue.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                log.error("Something went wrong while saving equipment.");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "/{equipmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentDTO getEquipment (@PathVariable("equipmentId") String equipmentId) {
        log.info("Equipment with ID: {} retrieved successfully", equipmentId);
        return equipmentService.getSelectedEquipment(equipmentId);
    }

    @GetMapping(value = "allEquipment", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipment(){
        log.info("Get all equipment successfully");
        return equipmentService.getAllEquipment();
    }

    @PatchMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable ("equipmentId") String equipmentId, @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Received request to update equipment: {}", equipmentId);
        try {
            if (equipmentDTO == null && (equipmentId == null || equipmentId.isEmpty())) {
                log.warn("No equipment found with ID: {}", equipmentId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentId, equipmentDTO);
            log.info("Status of equipment with ID: {} updated successfully", equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataPersistFailedException e){
            log.warn("No equipment found with ID: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while updating equipment with ID: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{equipmentId}" )
    public ResponseEntity<Void> deleteEquipment(@PathVariable ("equipmentId") String equipmentId) {
        log.info("Received request to delete equipment: {}", equipmentId);
        try {
            equipmentService.deleteEquipment(equipmentId);
            log.info("Status of equipment with ID: {} deleted successfully", equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EquipmentNotFoundException e){
            log.warn("Equipment not found with ID: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while deleting equipment with ID: {}", equipmentId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}