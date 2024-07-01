package utils;

import classes.Vehicle;

import java.util.ArrayList;

public class BubbleSortTool {
    public static void DescendingBubbleSort(ArrayList<Vehicle> vehicles) { // не проще ли написать свой компаратор?
        Vehicle temp;
        int j = vehicles.size() - 1;
        while (j != 0) {
            for (int i = 0; i < j; i++) {
                if (vehicles.get(i).compareTo(vehicles.get(i + 1)) < 0) {
                    temp = vehicles.get(i);
                    vehicles.set(i, vehicles.get(i + 1));
                    vehicles.set(i + 1, temp);
                }
            }
            j--;
        }
    }
}
