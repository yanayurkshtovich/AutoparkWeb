package services;

import classes.Vehicle;
import entities.Orders;
import infrastructure.core.annotations.Autowired;
import infrastructure.dto.EntityManager;
import infrastructure.dto.annotations.Column;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Mechanic implements Fixer {
    @Autowired
    EntityManager entityManager;

    @Override
    public Map<String, Integer> detectBreaking(Integer vehicleID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String,Integer> breakdownsMap = new HashMap<>();
        Method getter = null;
        Object valueInField = null;
        for (Orders o: entityManager.getAll(Orders.class)) {
            if (Math.toIntExact(o.getVehicleID()) == vehicleID) {
                for (Field f : o.getClass().getDeclaredFields()) {
                    getter = o.getClass().getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1));
                    valueInField = getter.invoke(o);
                    if (f.isAnnotationPresent(Column.class) && valueInField != null && !f.getName().equals("vehicleID")) {
                        breakdownsMap.put(f.getName(), (Integer) valueInField);
                    }
                }
            }
        }

        return breakdownsMap;
    }

    @Override
    public void repair(Integer vehicleID) {
        for (Orders o : entityManager.getAll(Orders.class)) {
            if (Math.toIntExact(o.getVehicleID()) == vehicleID) {
                entityManager.deleteRowByID(o.getId(), "orders");
            }
        }
    }

    @Override
    public boolean isBroken(Integer vehicleID) {
        for (Orders o : entityManager.getAll(Orders.class)) {
            if (Math.toIntExact(o.getVehicleID()) == vehicleID) {
                return true;
            }
        }

        return false;
    }
}
