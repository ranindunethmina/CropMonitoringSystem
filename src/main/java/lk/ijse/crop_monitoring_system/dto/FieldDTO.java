package lk.ijse.crop_monitoring_system.dto;

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
    private String fieldName;
    private Point location;
    private double extentSize;
    private String fieldImage1;
    private String fieldImage2;

//    private List<String> cropCodes;
//    private List<String> equipmentIds;
    private List<String> staffIds;
//    private List<String> logIds;
}