package lk.ijse.crop_monitoring_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Crop implements Serializable {
    @Id
    private String cropCode;

    @Column(length = 150, nullable = false)
    private String commonName;

    @Column(length = 150, nullable = false)
    private String scientificName;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String cropImage;

    @Column(length = 30, nullable = false)
    private String category;

    @Column(length = 30, nullable = false)
    private String season;

    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private Field field;

    @ManyToMany(mappedBy = "crop")
    private List<Log> log;
}