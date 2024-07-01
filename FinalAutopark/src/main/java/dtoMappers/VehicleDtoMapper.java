package dtoMappers;

import classes.Vehicle;
import dtos.VehicleDto;
import entityMappers.VehiclesEntityMapper;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import lombok.NoArgsConstructor;
import services.Mechanic;
import utils.Constants;
import utils.ProfitCalculationTool;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class VehicleDtoMapper {
    @Autowired
    private VehiclesEntityMapper vehiclesEntityMapper;
    @Autowired
    private Mechanic mechanic;

    public List<VehicleDto> getVehicleDtos() throws NotVehicleException {
        return vehiclesEntityMapper.getVehiclesList().stream().map(vehicle -> {
            return VehicleDto.builder()
                    .id(vehicle.getVehicleID())
                    .type(vehicle.getVehicleType().getType())
                    .model(vehicle.getVehicleModel())
                    .registrationIdentifier(vehicle.getVehicleRegistrationIdentifier())
                    .mass(vehicle.getVehicleMass())
                    .manufactureYear(vehicle.getVehicleManufactureYear())
                    .color(vehicle.getVehicleColor().getVehicleColor())
                    .engineType(vehicle.getVehicleEngine().getEngineName())
                    .mileage(vehicle.getVehicleMileage())
                    .tankVolumeOrBatteryCharge(getTankVolumeOrBatteryCharge(vehicle))
                    .fuelOrElectricityConsumption(getFuelOrElectricityConsumption(vehicle))
                    .engineTaxPerMonth(vehicle.getVehicleEngine().getEngineTaxPerMonth())
                    .maxKilometers(vehicle.getVehicleEngine().getMaxKilometers())
                    .rentHistory(vehicle.getRentHistory())
                    .vehicleTaxPerMonth(ProfitCalculationTool.getCalcTaxPerMonth(vehicle))
                    .vehicleProfit(ProfitCalculationTool.getTotalProfit(vehicle))
                    .vehicleIncome(ProfitCalculationTool.getTotalIncome(vehicle))
                    .isBroken(mechanic.isBroken(vehicle.getVehicleID()))
                    .build();
        }).collect(Collectors.toList());
    }

    private double getTankVolumeOrBatteryCharge(Vehicle vehicle) {
        if (vehicle.getVehicleEngine().getEngineName().equals(Constants.ELECTRICAL_ENGINE_NAME)) {
            return vehicle.getVehicleEngine().getCurrentBatteryCharge();
        }
        else {
            return vehicle.getVehicleEngine().getFuelTankVolume();
        }
    }

    private double getFuelOrElectricityConsumption(Vehicle vehicle) {
        if (vehicle.getVehicleEngine().getEngineName().equals(Constants.ELECTRICAL_ENGINE_NAME)) {
            return vehicle.getVehicleEngine().getElectricityConsumptionPerKmInkVatPerHour();
        }
        else {
            return vehicle.getVehicleEngine().getFuelConsumptionPerHundredKmInLiters();
        }
    }

}
