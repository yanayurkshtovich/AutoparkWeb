package dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VehicleTypeDto {
    private int id;
    private String name;
    private double taxCoefficient;
}
