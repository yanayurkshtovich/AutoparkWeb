package services;

import classes.Vehicle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface Fixer {
    public Map<String, Integer> detectBreaking(Vehicle vehicle) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    public void repair (Vehicle vehicle);
    public boolean isBroken(Vehicle vehicle);
    default boolean detectAndRepair(Vehicle vehicle) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
            repair(vehicle);
            return true;
        }

        return false;
    }
}
