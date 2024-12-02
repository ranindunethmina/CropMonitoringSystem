package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Log Date cannot be null")
    @PastOrPresent(message = "Log Date cannot be in the future")
    private LocalDate logDate;
    @Size(min = 5, max = 1000, message = "Details must be between 5 and 1000 characters")
    private String details;
    private String observedImage;

    private List<String> fieldCodes;
    private List<String> cropCodes;
    private List<String> staffIds;
}