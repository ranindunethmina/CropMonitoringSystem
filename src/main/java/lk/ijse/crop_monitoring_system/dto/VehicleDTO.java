package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO implements Serializable {
    private String vehicleCode;
    @NotBlank(message = "License Plate cannot be empty")
    private String licensePlate;
    @NotBlank(message = "Category cannot be empty")
    @Size(min = 3, max = 50, message = "Category must be between 3 and 50 characters")
    private String category;
    @NotBlank(message = "Fuel Type cannot be empty")
    @Pattern(regexp = "^(Petrol|Diesel|Electric|Hybrid)$",
       message = "Fuel Type must be one of the following: Petrol, Diesel, Electric, or Hybrid")
    private String fuelType;
    private String status;
    @Size(max = 255, message = "Remarks must not exceed 255 characters")
    private String remarks;

    private String staffId;
}