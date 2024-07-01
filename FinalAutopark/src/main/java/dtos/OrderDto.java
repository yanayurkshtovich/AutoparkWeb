package dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OrderDto {
    private Integer vehicleID;
    private Map<String,Integer> breakdownsMap;
}
