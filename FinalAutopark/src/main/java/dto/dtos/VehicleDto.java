package dto.dtos;

import classes.Rent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
@Setter
public class VehicleDto {
    private Integer id;
    private String type;
    private String model;
    private String registrationIdentifier;
    private Integer mass;
    private Integer manufactureYear;
    private String color;
    private String engineType;
    private Integer mileage;
    private Double tankVolumeOrBatteryCharge;
    private Double fuelOrElectricityConsumption;
    private Double engineTaxPerMonth;
    private Double maxKilometers;
    private List<Rent> rentHistory;
    private Double vehicleTaxPerMonth;
    private Double vehicleProfit;
    private Double vehicleIncome;
    private boolean isBroken;
}
