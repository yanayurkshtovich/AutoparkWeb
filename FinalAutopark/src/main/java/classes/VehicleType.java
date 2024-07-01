package classes;

import exceptions.NotVehicleException;
import lombok.Getter;
import lombok.Setter;
import utils.TechnicalSpecialistUtil;

@Getter
@Setter
public class VehicleType {
    private String type;
    private double taxCoefficient;
    private int typeID;

    public VehicleType(String type, double taxCoefficient, int typeID) throws NotVehicleException {
        setType(type);
        setTaxCoefficient(taxCoefficient);
        setTypeID(typeID);
    }

    public void setType(String type) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateVehicleType(type)) {
            throw new NotVehicleException("Invalid vehicle type");
        }
        else {
            this.type = type;
        }
    }

    public void setTaxCoefficient(double taxCoefficient) throws NotVehicleException {
        if (!TechnicalSpecialistUtil.validateTaxCoefficient(taxCoefficient))
            throw new NotVehicleException("Invalid vehicle type tax coefficient");
        else {
            this.taxCoefficient = taxCoefficient;
        }
    }

    @Override
    public String toString() {
        return "VehicleType{" +
                "type='" + type + '\'' +
                ", taxCoefficient=" + taxCoefficient +
                ", typeID=" + typeID +
                '}';
    }
}
