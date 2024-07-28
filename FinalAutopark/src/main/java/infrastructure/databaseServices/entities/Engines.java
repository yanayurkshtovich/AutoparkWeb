package infrastructure.databaseServices.entities;

import infrastructure.databaseServices.annotations.Column;
import infrastructure.databaseServices.annotations.ID;
import infrastructure.databaseServices.annotations.Table;
import lombok.*;

@Table(name = "engines")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Engines {
    @ID
    private Long id;
    @Column(name = "vehicle_id", unique = true)
    private Long vehicleID;
    @Column(name = "electricity_consumption")
    private Integer electricityConsumption;
    @Column(name = "battery_charge")
    private Double batteryCharge;
    @Column(name = "engine_volume")
    private Double engineVolume;
    @Column(name = "tank_volume")
    private Double tankVolume;
    @Column(name = "fuel_consumption")
    private Double fuelConsumption;
}
