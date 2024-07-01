package dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentDto {
    private int vehicleId;
    private String rentDate;
    private Double rentCost;
}
