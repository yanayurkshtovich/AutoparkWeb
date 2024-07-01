package dtoMappers;

import dtos.VehicleTypeDto;
import entityMappers.VehicleTypesEntityMapper;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class VehicleTypeDtoMapper {
    @Autowired
    private VehicleTypesEntityMapper vehicleTypesEntityMapper;

    public List<VehicleTypeDto> getVehicleTypeDtos() throws NotVehicleException {
        return vehicleTypesEntityMapper.getVehicleTypesList().stream().map(vehicleType -> {
            return VehicleTypeDto.builder()
                    .id(vehicleType.getTypeID())
                    .name(vehicleType.getType())
                    .taxCoefficient(vehicleType.getTaxCoefficient()).build();
        }).collect(Collectors.toList());
    }
}
