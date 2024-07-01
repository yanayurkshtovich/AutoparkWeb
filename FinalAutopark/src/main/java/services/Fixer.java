package services;

import classes.Vehicle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface Fixer {
    public Map<String, Integer> detectBreaking(Integer vehicleID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    public void repair (Integer vehicleID);
    public boolean isBroken(Integer vehicleID);
    default boolean detectAndRepair(Integer vehicleID) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        detectBreaking(vehicleID);
        if (isBroken(vehicleID)) {
            repair(vehicleID);
            return true;
        }

        return false;
    }
}
