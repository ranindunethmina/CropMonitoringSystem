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
public class Equipment implements Serializable {
    @Id
    private String equipmentId;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String type;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name ="fieldCode")
    private Field field;
}