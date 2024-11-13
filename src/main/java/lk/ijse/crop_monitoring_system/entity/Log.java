package lk.ijse.crop_monitoring_system.entity;

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
public class Log implements Serializable {
    @Id
    private String logCode;

    @Column(nullable = false)
    private LocalDate logDate;

    @Column(nullable = false)
    private String details;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String observedImage;

    @ManyToMany
    @JoinTable(name="log_field",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name="fieldCode"))
    private List<Field> field;

    @ManyToMany
    @JoinTable(
            name="log_crop",
            joinColumns = @JoinColumn(name="logCode"),
            inverseJoinColumns = @JoinColumn(name="cropCode")
    )
    private List<Crop> crop;

    @ManyToMany
    @JoinTable(
            name="log_staff",
            joinColumns = @JoinColumn(name="logCode"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Staff> staff;
}