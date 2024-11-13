package lk.ijse.crop_monitoring_system.controller;

import lk.ijse.crop_monitoring_system.dto.CropDTO;
import lk.ijse.crop_monitoring_system.service.CropService;
import lk.ijse.crop_monitoring_system.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.DataException;
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
public class CropController {
    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart ("scientificName") String scientificName,
            @RequestPart("cropImage") MultipartFile cropImage,
            @RequestPart ("category") String category,
            @RequestPart ("season") String season) {
        try {
            String base64cropImage = AppUtil.toBase64(cropImage);

            CropDTO cropDTO = new CropDTO();
            cropDTO.setCommonName(commonName);
            cropDTO.setScientificName(scientificName);
            cropDTO.setCropImage(base64cropImage);
            cropDTO.setCategory(category);
            cropDTO.setSeason(season);

            cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cropId}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("cropId") String cropId) {
        try {
            cropService.deleteCrop(cropId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "allCrop", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrop(){
        return cropService.getAllCrop();
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop (
            @PathVariable("id") String cropId,
            @RequestPart("commonName") String updateCommonName,
            @RequestPart ("scientificName") String updateScientificName,
            @RequestPart("cropImage") MultipartFile updateCropImage,
            @RequestPart ("category") String updateCategory,
            @RequestPart ("season") String updateSeason) {
        try {
            String updateBase64cropImage = AppUtil.toBase64(updateCropImage);

            CropDTO updateCrop = new CropDTO();
            updateCrop.setCropCode(cropId);
            updateCrop.setCommonName(updateCommonName);
            updateCrop.setScientificName(updateScientificName);
            updateCrop.setCropImage(updateBase64cropImage);
            updateCrop.setCategory(updateCategory);
            updateCrop.setSeason(updateSeason);

            cropService.updateCrop(cropId, updateCrop);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (DataException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}