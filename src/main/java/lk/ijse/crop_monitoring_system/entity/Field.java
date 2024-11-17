package lk.ijse.crop_monitoring_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Field implements Serializable {
    @Id
    private String fieldCode;

    @Column(length = 50, nullable = false)
    private String fieldName;

    @Column(nullable = false)
    private Point location;

    @Column(nullable = false)
    private double extentSize;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String fieldImage1;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String fieldImage2;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Crop> crop;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Equipment> equipment;

    @ManyToMany
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "fieldCode"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )    private List<Staff> staff;

    @ManyToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Log> log;
}