package entities;

import infrastructure.dto.annotations.Column;
import infrastructure.dto.annotations.ID;
import infrastructure.dto.annotations.Table;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "type_id")
    private Long typeID;
    @Column(name = "model")
    private String vehicleModel;
    @Column(name = "registration_identifier")
    private String vehicleRegistrationIdentifier;
    @Column(name = "mass")
    private Integer vehicleMass;
    @Column(name = "mileage")
    private Integer vehicleMileage;
    @Column(name = "manufacture_year")
    private Integer vehicleManufactureYear;
    @Column(name = "color")
    private String vehicleColor;
    @Column(name = "engine")
    private String vehicleEngine;
}
