package lk.ijse.crop_monitoring_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO implements Serializable {
    private String logCode;
    private LocalDate logDate;
    private String details;
    private String observedImage;

    private List<String> fieldCodes;
    private List<String> cropCodes;
    private List<String> staffIds;
}