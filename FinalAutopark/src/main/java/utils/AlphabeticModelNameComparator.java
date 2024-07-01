package utils;

import classes.Vehicle;

import java.util.Comparator;


public class AlphabeticModelNameComparator implements Comparator<Vehicle> {

    @Override
    public int compare(Vehicle v1, Vehicle v2) {
        return v1.getVehicleModel().compareTo(v2.getVehicleModel());
    }
}
