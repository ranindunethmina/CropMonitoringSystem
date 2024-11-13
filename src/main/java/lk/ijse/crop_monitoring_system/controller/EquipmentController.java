package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.EquipmentDTO;
import lk.ijse.crop_monitoring_system.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO){
        if (equipmentDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                equipmentService.saveEquipment(equipmentDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "allEquipment", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipment(){
        return equipmentService.getAllEquipment();
    }

    @PatchMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable ("equipmentId") String equipmentId, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            if (equipmentDTO == null && (equipmentId == null || equipmentId.isEmpty())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(equipmentId, equipmentDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{equipmentId}" )
    public ResponseEntity<Void> deleteEquipment(@PathVariable ("equipmentId") String equipmentId) {
        try {
            equipmentService.deleteEquipment(equipmentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}