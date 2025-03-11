package com.geosapiens.eucomida.entity;

import com.geosapiens.eucomida.entity.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "public", name = "couriers")
@EqualsAndHashCode(of = "id")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O entregador deve estar associado a um usuário")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_courier_user"))
    private User user;

    @NotNull(message = "O tipo de veículo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 30)
    private VehicleType vehicleType;

    @Pattern(regexp = "^[A-Z]{3}\\d[A-Z]\\d{2}$", message = "A placa deve estar no formato correto (ex: AAA1B23)")
    @Column(name = "plate_number", length = 30)
    private String plateNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
