package entities;

import infrastructure.dto.annotations.Column;
import infrastructure.dto.annotations.ID;
import infrastructure.dto.annotations.Table;
import lombok.*;

import java.util.Date;

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
