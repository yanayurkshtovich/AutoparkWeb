import classes.Rent;
import classes.Vehicle;
import classes.VehicleType;
import entities.Vehicles;
import entityMappers.VehicleTypesEntityMapper;
import entityMappers.VehiclesEntityMapper;
import exceptions.NotVehicleException;
import infrastructure.core.impl.ApplicationContext;
import infrastructure.core.impl.Context;
import infrastructure.dto.EntityManager;
import infrastructure.dto.annotations.Column;
import infrastructure.dto.annotations.ID;
import infrastructure.dto.annotations.Table;
import infrastructure.dto.impl.EntityManagerImpl;
import services.Mechanic;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws NotVehicleException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

//        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
//        //interfaceToImplementation.put(Fixer.class, MechanicService.class);
//        interfaceToImplementation.put(EntityManager.class, EntityManagerImpl.class);
//        ApplicationContext applicationContext = new ApplicationContext("infrastructure", interfaceToImplementation);
//        Mechanic mechanic = applicationContext.getObject(Mechanic.class);
//        for (Vehicle v : applicationContext.getObject(VehiclesEntityMapper.class).getVehiclesList()) {
//            System.out.println(mechanic.detectBreaking(v));
//        }

    }
}


