package infrastructure.databaseServices.entities;

import infrastructure.databaseServices.annotations.Column;
import infrastructure.databaseServices.annotations.ID;
import infrastructure.databaseServices.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Orders {
    @ID
    private Long id;
    @Column(name = "vehicle_id", unique = true)
    private Long vehicleID;
    @Column(name = "sleevs")
    private Integer sleeveNumber;
    @Column(name = "filters")
    private Integer filterNumber;
    @Column(name = "shafts")
    private Integer shaftNumber;
    @Column(name = "axes")
    private Integer axisNumber;
    @Column(name = "candles")
    private Integer candleNumber;
    @Column(name = "oil")
    private Integer oilNumber;
    @Column(name = "valvetrains")
    private Integer valveTrainNumber;
    @Column(name = "joints")
    private Integer jointNumber;
}
