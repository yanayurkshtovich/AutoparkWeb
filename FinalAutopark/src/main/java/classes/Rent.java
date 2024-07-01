package classes;

import exceptions.NotVehicleException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.TechnicalSpecialistUtil;

@Getter
@Setter
@NoArgsConstructor
public class Rent {
    private String rentDate;
    private double rentCost;
    private int vehicleID;

    public Rent(double rentCost, String rentDate, int vehicleID) throws NotVehicleException {
        setRentCost(rentCost);
        setRentDate(rentDate);
        setVehicleID(vehicleID);
    }

    public void setRentDate(String rentDate) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateRentDate(rentDate)) {
            throw new NotVehicleException("Invalid rent date format");
        }
        else {
            this.rentDate = rentDate;
        }
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentDate='" + rentDate + '\'' +
                ", rentCost=" + rentCost +
                ", vehicleID=" + vehicleID +
                '}';
    }
}
