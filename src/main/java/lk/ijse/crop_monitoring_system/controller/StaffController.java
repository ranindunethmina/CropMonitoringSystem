package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.StaffDTO;
import lk.ijse.crop_monitoring_system.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StaffController {
    private final StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO){
        if (staffDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            try {
                staffService.saveStaff(staffDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping(value = "allStaff", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO>  getAllStaff(){
        return staffService.getAllStaff();
    }

    @PatchMapping(value = "/{staffId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable ("staffId") String staffId, @RequestBody StaffDTO staffDTO) {
        try {
            if (staffDTO == null && (staffId == null || staffId.isEmpty())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            staffService.updateStaff(staffId, staffDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value ="/{staffId}" )
    public ResponseEntity<Void> deleteStaff(@PathVariable ("staffId") String staffId) {
        try {
            staffService.deleteStaff(staffId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}