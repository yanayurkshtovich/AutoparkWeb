package utils;

import classes.Rent;
import classes.Vehicle;
import dto.dtos.VehicleDto;

import java.util.List;

public class ProfitCalculationTool {
    public static double getCalcTaxPerMonth(Vehicle vehicle) {
        double vehicleMassTaxContribution = (double) vehicle.getVehicleMass() * Constants.VEHICLE_MASS_COEFFICIENT;
        double taxContribution = vehicle.getVehicleEngine().getEngineTaxPerMonth() +
                vehicle.getVehicleType().getTaxCoefficient() * Constants.VEHICLE_TYPE_TAX_COEFFICIENT;
        return  vehicleMassTaxContribution +
                taxContribution +
                Constants.ADDITIONAL_TAX_FACTOR;
    }

    public static double getTotalIncome(Vehicle vehicle) {
        double totalIncome = 0.0;
        for (Rent r : vehicle.getRentHistory()) {
            totalIncome += r.getRentCost();
        }

        return totalIncome;
    }

    public static double getTotalProfit(Vehicle vehicle) {
        return getTotalIncome(vehicle) - getCalcTaxPerMonth(vehicle);
    }

    public static double sumTotalProfit(List<Vehicle> vehiclesCollection) {
        double totalProfit = 0.0;
        for (Vehicle v : vehiclesCollection) {
            totalProfit += getTotalProfit(v);
        }

        return totalProfit;
    }

    public static double calculateAvgTax(List<VehicleDto> vehiclesList) {
        double avgTax = 0.0;
        for (VehicleDto dto : vehiclesList) {
            avgTax += dto.getVehicleTaxPerMonth();
        }

        return avgTax;
    }

    public static double calculateAvgIncome(List<VehicleDto> vehiclesList) {
        double avgIncome = 0.0;
        for (VehicleDto dto : vehiclesList) {
            avgIncome += dto.getVehicleIncome();
        }

        return avgIncome;
    }

    public static double calculateAutoparkProfit(List<VehicleDto> vehiclesList) {
        double autoparkProfit = 0.0;
        for (VehicleDto dto : vehiclesList) {
            autoparkProfit += dto.getVehicleProfit();
        }

        return autoparkProfit;
    }
}
