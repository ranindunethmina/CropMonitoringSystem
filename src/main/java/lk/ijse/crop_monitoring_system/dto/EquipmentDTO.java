package lk.ijse.crop_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO implements Serializable {
    private String equipmentId;
    private String name;
    private String type;
    private String status;

    private String staffId;
    private String fieldCode;
}