package lk.ijse.crop_monitoring_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Vehicle implements Serializable {
    @Id
    private String vehicleCode;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String fuelType;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "id")
    private Staff staff;
}