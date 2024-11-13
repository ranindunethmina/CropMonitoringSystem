package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.VehicleDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.VehicleNotFoundException;
import lk.ijse.crop_monitoring_system.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle (@RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to save vehicle: {}", vehicleDTO);
        if (vehicleDTO == null) {
            log.warn("Received null VehicleDTO");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                vehicleService.saveVehicle(vehicleDTO);
                log.info("Vehicle saved Successfully: {}", vehicleDTO.getVehicleCode());
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                log.error("failed due to data persistence issue.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                log.error("Something went wrong while saving vehicle.");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "allVehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicle(){
        log.info("Get all vehicles successfully");
        return vehicleService.getAllVehicles();
    }

    @PatchMapping(value = "/{vehicleId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable ("vehicleId") String vehicleId, @RequestBody VehicleDTO vehicleDTO) {
        log.info("Received request to update vehicle: {}", vehicleId);
        try {
            if (vehicleDTO == null && (vehicleId == null || vehicleId.isEmpty())) {
                log.warn("Received null vehicleId for update");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.updateVehicle(vehicleId, vehicleDTO);
            log.info("Vehicle updated successfully: {}", vehicleDTO.getVehicleCode());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while updating vehicle.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{vehicleId}" )
    public ResponseEntity<Void> deleteVehicle(@PathVariable ("vehicleId") String vehicleId) {
        log.info("Received request to delete vehicle: {}", vehicleId);
        try {
            vehicleService.deleteVehicle(vehicleId);
            log.info("Vehicle deleted successfully: {}", vehicleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (VehicleNotFoundException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.error("Something went wrong while deleting vehicle.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}