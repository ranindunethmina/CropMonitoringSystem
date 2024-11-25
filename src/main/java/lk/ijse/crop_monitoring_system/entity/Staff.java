package lk.ijse.crop_monitoring_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Staff implements Serializable {
    @Id
    private String id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Designation designation;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(length = 30, nullable = false)
    private String addressLine01;

    @Column(length = 30, nullable = false)
    private String addressLine02;

    @Column(length = 30, nullable = false)
    private String addressLine03;

    @Column(length = 30, nullable = false)
    private String addressLine04;

    @Column(length = 30, nullable = false)
    private String addressLine05;

    @Column(length = 10, nullable = false)
    private String contactNo;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vehicle> vehicle;

    @ManyToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Field> field;

    @ManyToMany(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Log> log;

    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL)
    @JsonIgnore
    private Equipment equipment;
}