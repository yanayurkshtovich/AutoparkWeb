package utils;

import classes.Vehicle;

import java.util.ArrayList;

public class PrintSimilarVehiclesTool {
    public static void printSimilarVehicles(ArrayList<Vehicle> vehicles) {
        System.out.println("\nSIMILAR VEHICLES:");
        int boundary = vehicles.size();
        for (int i = 0; i < boundary; i++) {
            for (int j = 0; j < boundary; j++) {
                if (i != j & vehicles.get(i).equals(vehicles.get(j))) {
                    System.out.println(vehicles.get(i));
                    break;
                }
            }
        }
    }
}
