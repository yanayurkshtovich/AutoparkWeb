package infrastructure.databaseServices.entities;

import infrastructure.databaseServices.annotations.Column;
import infrastructure.databaseServices.annotations.ID;
import infrastructure.databaseServices.annotations.Table;
import lombok.*;

@Table(name = "types")
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypes {
    @ID
    private Integer id;
    @Column(name = "type_name", unique = true)
    private String name;
    @Column(name = "coefTaxes")
    private Double coefTaxes;
}
