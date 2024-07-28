package infrastructure.databaseServices.entities;

import infrastructure.databaseServices.annotations.Column;
import infrastructure.databaseServices.annotations.ID;
import infrastructure.databaseServices.annotations.Table;
import lombok.*;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "vehicle_id")
    private Long vehicleID;
    @Column(name = "rentDate")
    private String rentDate;
    @Column(name = "returnDate")
    private String returnDate;
    @Column(name = "rent_cost")
    private Double rentCost;
}
