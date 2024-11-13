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
public class User implements Serializable {
    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}