package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class StaffDTO implements Serializable {
    private String id;
    @NotBlank(message = "First Name cannot be empty")
    @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
    private String firstName;
    @NotBlank(message = "Last Name cannot be empty")
    @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters")
    private String lastName;
    private String designation;
    private String gender;
    private LocalDate joinedDate;
    @NotNull(message = "Date of Birth is required")
    private LocalDate dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    @NotNull(message = "Contact No is required")
    private String contactNo;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;
    private String role;

    private List<String> vehicleIds;
    private List<String> fieldCodes;
//    private List<String> logIds;
    private String equipmentId;
}