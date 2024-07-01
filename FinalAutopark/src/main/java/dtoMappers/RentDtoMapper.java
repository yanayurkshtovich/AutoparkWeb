package dtoMappers;

import dtos.RentDto;
import entityMappers.RentsEntityMapper;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class RentDtoMapper {
    @Autowired
    private RentsEntityMapper rentsEntityMapper;

    public List<RentDto> getRentDtos() throws NotVehicleException {
        return rentsEntityMapper.getRentsList().stream().map(rent -> {
            return RentDto.builder()
                    .vehicleId(rent.getVehicleID())
                    .rentDate(rent.getRentDate())
                    .rentCost(rent.getRentCost())
                    .build();
        }).collect(Collectors.toList());
    }
}
