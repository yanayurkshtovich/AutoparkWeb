package services;

import infrastructure.databaseServices.entities.Orders;
import infrastructure.core.annotations.Autowired;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.annotations.Column;
import infrastructure.databaseServices.entityServices.OrdersService;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor
public class Mechanic implements Fixer {
    private final int BREAKDOWNS_UPPER_LIMIT = 5;
    private static final String[] details = {"filters",
                                            "axes",
                                            "shafts",
                                            "sleevs",
                                            "candles",
                                            "oil",
                                            "valvetrains",
                                            "joints"};
    @Autowired
    OrdersService ordersService;

    @Override
    public Map<String, Integer> detectBreaking(Integer vehicleID) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String,Integer> breakdownsMap = new HashMap<>();
        Method getter = null;
        Object valueInField = null;
        for (Orders o: ordersService.getAll()) {
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
    public boolean repair(Integer vehicleID) {
        for (Orders o : ordersService.getAll()) {
            if (Math.toIntExact(o.getVehicleID()) == vehicleID) {
                ordersService.deleteRowByID(o.getId(), "orders");
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isBroken(Integer vehicleID) {
        for (Orders o : ordersService.getAll()) {
            if (Math.toIntExact(o.getVehicleID()) == vehicleID) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void breakRandomVehicles(Object[] vehicles_id) {
        Map<String,Integer> breakdownsMap = new HashMap<>();
        for (Object id : vehicles_id) {
            if (shouldVehicleBeBroken()) {
                breakdownsMap = createBreakdownsMap();
                ordersService.save(Orders.builder()
                        .vehicleID(Long.valueOf((Integer) id))
                        .sleeveNumber(breakdownsMap.getOrDefault("sleevs", 0))
                        .filterNumber(breakdownsMap.getOrDefault("filters", 0))
                        .shaftNumber(breakdownsMap.getOrDefault("shafts", 0))
                        .axisNumber(breakdownsMap.getOrDefault("axes", 0))
                        .candleNumber(breakdownsMap.getOrDefault("candles", 0))
                        .oilNumber(breakdownsMap.getOrDefault("oil", 0))
                        .valveTrainNumber(breakdownsMap.getOrDefault("valvetrains", 0))
                        .jointNumber(breakdownsMap.getOrDefault("joints", 0))
                        .build());
            }
        }
    }

    private boolean shouldVehicleBeBroken() {
        Random rand = new Random();
        return rand.nextBoolean();
    }

    private int pickRandomDetail() {
        Random rand = new Random();
        return rand.nextInt(details.length);
    }

    private int getRandomBreakdownNumber(int lowerLimit) {
        Random rand = new Random();
        return rand.nextInt(BREAKDOWNS_UPPER_LIMIT - lowerLimit) + lowerLimit;
    }

    private Map<String, Integer> createBreakdownsMap() {
        Map<String, Integer> brokenDetails = new HashMap<>();
        int numberOfDetectedBreakdowns = getRandomBreakdownNumber(0);
        while (numberOfDetectedBreakdowns != 0) {
            String brokenDetail = details[pickRandomDetail()];
            if (!brokenDetails.containsKey(brokenDetail)) {
                brokenDetails.put(details[pickRandomDetail()], getRandomBreakdownNumber(1));
            }
            else {
                brokenDetails.put(brokenDetail, brokenDetails.get(brokenDetail) + getRandomBreakdownNumber(1));
            }
            numberOfDetectedBreakdowns--;
        }
        return brokenDetails;
    }
}
