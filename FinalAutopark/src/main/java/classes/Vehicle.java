package classes;

import classes.engines.Startable;
import exceptions.NotVehicleException;
import lombok.Getter;
import lombok.Setter;
import utils.TechnicalSpecialistUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Vehicle implements Comparable<Vehicle> {
    private int vehicleID;
    private VehicleType vehicleType;
    private String vehicleModel;
    private String vehicleRegistrationIdentifier;
    private int vehicleMass;
    private int vehicleMileage;
    private int vehicleManufactureYear;
    private enums.VehicleColor vehicleColor;
    private Startable vehicleEngine;
    private List<Rent> rentHistory;

    public Vehicle(String vehicleModel, int vehicleID, VehicleType vehicleType, String vehicleRegistrationIdentifier,
                   int vehicleMass, int vehicleMileage, int vehicleManufactureYear, enums.VehicleColor vehicleColor,
                   Startable vehicleEngine) throws NotVehicleException {
        setVehicleModel(vehicleModel);
        setVehicleID(vehicleID);
        setVehicleType(vehicleType);
        setVehicleRegistrationIdentifier(vehicleRegistrationIdentifier);
        setVehicleMass(vehicleMass);
        setVehicleMileage(vehicleMileage);
        setVehicleManufactureYear(vehicleManufactureYear);
        setVehicleColor(vehicleColor);
        setVehicleEngine(vehicleEngine);
    }

    public void setVehicleModel(String vehicleModel) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateModelName(vehicleModel)) {
            throw new NotVehicleException("Invalid model name");
        }
        else {
            this.vehicleModel = vehicleModel;
        }
    }

    public void setVehicleRegistrationIdentifier(String vehicleRegistrationIdentifier) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateRegistrationIdentifier(vehicleRegistrationIdentifier)) {
            throw new NotVehicleException("Invalid registration identifier");
        }
        else {
            this.vehicleRegistrationIdentifier = vehicleRegistrationIdentifier;
        }
    }

    public void setVehicleMass(int vehicleMass) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateWeight(vehicleMass)) {
            throw new NotVehicleException("Invalid mass");
        }
        else {
            this.vehicleMass = vehicleMass;
        }
    }

    public void setVehicleMileage(int vehicleMileage) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateMileage(vehicleMileage)) {
            throw new NotVehicleException("Invalid mileage");
        }
        else {
            this.vehicleMileage = vehicleMileage;
        }
    }

    public void setVehicleManufactureYear(int vehicleManufactureYear) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateManufactureYear(vehicleManufactureYear)) {
            throw new NotVehicleException("Invalid manufacture year");
        }
        else {
            this.vehicleManufactureYear = vehicleManufactureYear;
        }
    }

    public void setVehicleColor(enums.VehicleColor vehicleColor) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateColor(vehicleColor.getVehicleColor())) {
            throw new NotVehicleException("Invalid color");
        }
        else {
            this.vehicleColor = vehicleColor;
        }
    }

    public void createRentHistory(List<Rent> rents) {
        List<Rent> rentsHistory = new ArrayList<>();
        for (Rent r : rents) {
            if (r.getVehicleID() == this.vehicleID) {
                rentsHistory.add(r);
            }
        }
        this.setRentHistory(rentsHistory);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Vehicle vehicle)) {
            return false;
        }

        return vehicle.vehicleType.equals(this.vehicleType) &&
                vehicle.vehicleModel.equals(this.vehicleModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, vehicleModel);
    }


    @Override
    public int compareTo(Vehicle vehicle) {
        int manufactureYearComparisonResult = Integer.compare(this.vehicleManufactureYear, vehicle.vehicleManufactureYear);
        int mileageComparisonResult = Integer.compare(vehicle.vehicleMileage, this.vehicleMileage);
        return manufactureYearComparisonResult == 0 ? mileageComparisonResult : manufactureYearComparisonResult;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID=" + vehicleID +
                ", vehicleType=" + vehicleType +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleRegistrationIdentifier='" + vehicleRegistrationIdentifier + '\'' +
                ", vehicleMass=" + vehicleMass +
                ", vehicleMileage=" + vehicleMileage +
                ", vehicleManufactureYear=" + vehicleManufactureYear +
                ", vehicleColor=" + vehicleColor +
                ", vehicleEngine=" + vehicleEngine +
                '}';
    }
}
