package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO implements Serializable {
    private String cropCode;
    @NotBlank(message = "Common Name cannot be empty")
    @Size(min = 3, max = 100, message = "Common Name must be between 3 and 100 characters")
    private String commonName;
    @Size(max = 150, message = "Scientific Name must not exceed 150 characters")
    private String scientificName;
    private String cropImage;
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;
    private String season;

    @NotBlank(message = "Field Code cannot be empty")
    private String fieldCode;
}