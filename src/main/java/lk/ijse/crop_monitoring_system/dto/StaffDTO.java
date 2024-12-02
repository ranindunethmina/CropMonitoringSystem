package lk.ijse.crop_monitoring_system.dto;

import jakarta.validation.constraints.*;
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
    @PastOrPresent(message = "Joined Date must not be in the future")
    private LocalDate joinedDate;
    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be in the past")
    private LocalDate dob;
    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    @NotNull(message = "Contact No is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid Contact Number format")
    private String contactNo;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;
    private String role;

    private List<String> vehicleIds;
    private List<String> fieldCodes;
    private List<String> logIds;
    private String equipmentId;
}