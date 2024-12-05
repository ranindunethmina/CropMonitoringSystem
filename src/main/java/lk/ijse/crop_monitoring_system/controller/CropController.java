package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.CropDTO;
import lk.ijse.crop_monitoring_system.exception.CropNotFoundException;
import lk.ijse.crop_monitoring_system.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_system.service.CropService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/crops")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class CropController {
    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart ("commonName") String commonName,
            @RequestPart ("scientificName") String scientificName,
            @RequestPart ("cropImage") MultipartFile cropImage,
            @RequestPart ("category") String category,
            @RequestPart ("season") String season,
            @RequestPart ("fieldCode") String fieldCode) {
        try {
            String base64cropImage = AppUtil.toBase64(cropImage);

            CropDTO cropDTO = new CropDTO();
            cropDTO.setCommonName(commonName);
            cropDTO.setScientificName(scientificName);
            cropDTO.setCropImage(base64cropImage);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);
            cropDTO.setFieldCode(fieldCode);

            cropService.saveCrop(cropDTO);
            log.info("Crop saved Successfully: {}", cropDTO.getCropCode());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (CropNotFoundException e) {
            log.error("Crop not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while saving crop.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        log.info("Received request to delete crop: {}", cropId);
        try {
            cropService.deleteCrop(cropId);
            log.info("Crop deleted successfully: {}", cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CropNotFoundException e) {
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Something went wrong while deleting vehicle.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{cropId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CropDTO getCrop (@PathVariable("cropId") String cropId) {
        log.info("Crop with ID: {} retrieved successfully", cropId);
        return cropService.getSelectedCrop(cropId);
    }

    @GetMapping(value = "allCrop", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrop(){
        log.info("Get all crops successfully");
        return cropService.getAllCrop();
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop (
            @PathVariable("id") String cropId,
            @RequestPart("commonName") String updateCommonName,
            @RequestPart ("scientificName") String updateScientificName,
            @RequestPart("cropImage") MultipartFile updateCropImage,
            @RequestPart ("category") String updateCategory,
            @RequestPart ("season") String updateSeason,
            @RequestPart ("fieldCode") String fieldCode) {
        try {
            String updateBase64cropImage = AppUtil.toBase64(updateCropImage);

            CropDTO updateCrop = new CropDTO();
            updateCrop.setCropCode(cropId);
            updateCrop.setCommonName(updateCommonName);
            updateCrop.setScientificName(updateScientificName);
            updateCrop.setCropImage(updateBase64cropImage);
            updateCrop.setCategory(updateCategory);
            updateCrop.setSeason(updateSeason);
            updateCrop.setFieldCode(fieldCode);

            cropService.updateCrop(cropId, updateCrop);
            log.info("Crop updated successfully: {}", cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CropNotFoundException e) {
            log.warn("No crop found with ID: {}", cropId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataPersistFailedException e){
            log.error("failed due to data persistence issue.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Something went wrong while updating crop.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}