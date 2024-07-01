package utils;

import classes.VehicleType;
import classes.engines.*;
import enums.VehicleColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.*;

public class TechnicalSpecialistUtil {
    public static final int MANUFACTURE_YEAR_LOWER_BOND = 1886;
    final static String registrationIdentifierRegex = "\\d{4} [A-Z]{2}-\\d";
    final static String rentDateRegex = "\\d{4}-\\d{2}-\\d{2}";


    public static boolean validateManufactureYear(int year) {
        int currentYear = Year.now().getValue();
        return isYearFormat(year) && year >= MANUFACTURE_YEAR_LOWER_BOND && year <= currentYear;
    }

    private static boolean isYearFormat(int year) {
        return (year > 999 && year < 10000);
    }

    public static boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    public static boolean validateWeight(int weight) {
        return weight > 0;
    }

    public static boolean validateColor(String color) {
        return color != null;
    }

    public static boolean validateVehicleType(String type) {
        return !type.isBlank();
    }

    public static boolean validateTaxCoefficient(double taxCoefficient) {
        return (taxCoefficient >= 0);
    }

    public static boolean validateRentDate(String date) {
        if (date.isBlank()) {
            return false;
        }

        Pattern datePattern = Pattern.compile(rentDateRegex);
        Matcher matcher = datePattern.matcher(date);
        return matcher.matches();
    }

    public static boolean validateRegistrationIdentifier(String registrationIdentifier) {
        if (registrationIdentifier.isBlank()) {
            return false;
        }

        Pattern registrationIdentifierPattern = Pattern.compile(registrationIdentifierRegex);
        Matcher matcher = registrationIdentifierPattern.matcher(registrationIdentifier);
        return matcher.matches();
    }

    public static boolean validateModelName(String modelName) {
        return !modelName.isBlank();
    }



    private static boolean validateElectricalEngine(ElectricalEngine engine) {
        return (engine.getEngineName() != null &&
                engine.getEngineTaxPerMonth() >=0 &&
                engine.getCurrentBatteryCharge() >= 0 &&
                engine.getElectricityConsumptionPerKmInkVatPerHour() >=0);
    }

    private static boolean validateCombustionEngine(AbstractCombustionEngine engine) {
        return (engine.getEngineName() != null &&
                engine.getEngineTaxPerMonth() >= 0 &&
                engine.getFuelConsumptionPerHundredKmInLiters() >= 0 &&
                engine.getEngineVolumeInCubicCm() >= 0 &&
                engine.getFuelTankVolume() >= 0);
    }

    public static boolean validateEngine(Startable engine) {
        if (engine instanceof ElectricalEngine) {
            return validateElectricalEngine((ElectricalEngine) engine);
        }
        else if (engine instanceof AbstractCombustionEngine) {
            return validateCombustionEngine((AbstractCombustionEngine) engine);
        }

        return false;
    }
}