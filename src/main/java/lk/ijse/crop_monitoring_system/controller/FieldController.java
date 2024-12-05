package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.FieldDTO;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.exception.FieldNotFoundException;
import lk.ijse.crop_monitoring_system.service.FieldService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/field")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class FieldController {
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestParam("fieldName")String fieldName,
            @RequestParam("fieldLocation") String location,
            @RequestParam("fieldSize") double extentSize,
            @RequestParam("fieldImage1") MultipartFile fieldImage1,
            @RequestParam("fieldImage2") MultipartFile fieldImage2
    ){
        try {
            String[] coords = location.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            Point fieldLocationP = new Point(x, y);

            String base64fieldImage1 = AppUtil.toBase64(fieldImage1);
            String base64fieldImage2 = AppUtil.toBase64(fieldImage2);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldName(fieldName);
            fieldDTO.setLocation(fieldLocationP);
            fieldDTO.setExtentSize(extentSize);
            fieldDTO.setFieldImage1(base64fieldImage1);
            fieldDTO.setFieldImage2(base64fieldImage2);

            fieldService.saveField(fieldDTO);
            log.info("Field saved Successfully: {}", fieldDTO.getFieldCode());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (FieldNotFoundException e) {
            log.error("Field not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while saving field.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(
            @PathVariable("id") String fieldCode,
            @RequestParam("fieldName")String updateFieldName,
            @RequestParam("fieldLocation") String updateLocation,
            @RequestParam("fieldSize") double updateExtentSize,
            @RequestParam("fieldImage1") MultipartFile updateFieldImage1,
            @RequestParam("fieldImage2") MultipartFile updateFieldImage2,
            @RequestParam("staffIds") List<String> staffIds
    ){
        try {
            String[] coords = updateLocation.split(",");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            Point fieldLocationP = new Point(x, y);

            String updateBase64fieldImage1 = AppUtil.toBase64(updateFieldImage1);
            String updateBase64fieldImage2 = AppUtil.toBase64(updateFieldImage2);

            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldCode(fieldCode);
            fieldDTO.setFieldName(updateFieldName);
            fieldDTO.setLocation(fieldLocationP);
            fieldDTO.setExtentSize(updateExtentSize);
            fieldDTO.setFieldImage1(updateBase64fieldImage1);
            fieldDTO.setFieldImage2(updateBase64fieldImage2);

            fieldService.updateField(staffIds, fieldDTO);
            log.info("Field updated successfully: {}",fieldCode);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (FieldNotFoundException e) {
            log.error("Field not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while saving field.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldId") String fieldId) {
        log.info("Received request to delete field: {}", fieldId);
        try {
            fieldService.deleteField(fieldId);
            log.info("Field deleted successfully: {}", fieldId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Something went wrong while deleting field.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldDTO getField (@PathVariable("fieldId") String fieldId) {
        log.info("Field with ID: {} retrieved successfully", fieldId);
        return fieldService.getSelectedField(fieldId);
    }

    @GetMapping(value = "allField", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllField(){
        log.info("Get all fields successfully");
        return fieldService.getAllFields();
    }
}