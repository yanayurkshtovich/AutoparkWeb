import classes.Rent;
import classes.Vehicle;
import classes.VehicleType;
import dtoMappers.OrderDtoMapper;
import dtoMappers.VehicleDtoMapper;
import dtos.OrderDto;
import dtos.VehicleDto;
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
import javassist.bytecode.analysis.SubroutineScanner;
import services.Mechanic;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws NotVehicleException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        //interfaceToImplementation.put(Fixer.class, MechanicService.class);
        interfaceToImplementation.put(EntityManager.class, EntityManagerImpl.class);
        ApplicationContext applicationContext = new ApplicationContext("infrastructure", interfaceToImplementation);
        for (VehicleDto v : applicationContext.getObject(VehicleDtoMapper.class).getVehicleDtos()) {
            System.out.println(v.isBroken());
        }

    }
}


