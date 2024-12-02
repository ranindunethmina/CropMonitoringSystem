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
public class EquipmentDTO implements Serializable {
    private String equipmentId;
    @NotBlank(message = "Equipment Name cannot be empty")
    @Size(min = 3, max = 100, message = "Equipment Name must be between 3 and 100 characters")
    private String name;
    @NotBlank(message = "Type cannot be empty")
    private String type;
    @NotBlank(message = "Status cannot be empty")
    private String status;

    private String staffId;
    private String fieldCode;
}