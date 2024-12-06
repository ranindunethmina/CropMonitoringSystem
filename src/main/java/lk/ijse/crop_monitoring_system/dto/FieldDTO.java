package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO implements Serializable {
    private String fieldCode;
    @NotBlank(message = "Field Name cannot be empty")
    @Size(min = 3, max = 100, message = "Field Name must be between 3 and 100 characters")
    private String fieldName;
    @NotNull(message = "Location cannot be null")
    private Point location;
    @Positive(message = "Extent Size must be greater than zero")
    private double extentSize;
    private String fieldImage1;
    private String fieldImage2;

    private List<String> staffIds;
}