package entities;

import infrastructure.dto.annotations.Column;
import infrastructure.dto.annotations.ID;
import infrastructure.dto.annotations.Table;
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
