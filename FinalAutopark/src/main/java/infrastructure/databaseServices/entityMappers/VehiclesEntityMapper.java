package infrastructure.databaseServices.entityMappers;

import classes.Vehicle;
import classes.VehicleType;
import classes.engines.DieselEngine;
import classes.engines.ElectricalEngine;
import classes.engines.GasolineEngine;
import classes.engines.Startable;
import infrastructure.databaseServices.entities.Engines;
import infrastructure.databaseServices.entities.Vehicles;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entityServices.EnginesService;
import infrastructure.databaseServices.entityServices.VehiclesService;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class VehiclesEntityMapper {
    @Autowired
    VehiclesService vehiclesService;
    @Autowired
    EnginesService enginesService;
    @Autowired
    VehicleTypesEntityMapper vehicleTypesEntityMapper;
    @Autowired
    RentsEntityMapper rentsEntityMapper;

    public List<Vehicle> getVehiclesList() throws NotVehicleException {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (Vehicles v : vehiclesService.getAll()) {
            vehicleList.add(new Vehicle(v.getVehicleModel(),
                    Math.toIntExact(v.getId()),
                    getVehicleTypeByID(Math.toIntExact(v.getTypeID())),
                    v.getVehicleRegistrationIdentifier(),
                    v.getVehicleMass(),
                    v.getVehicleMileage(),
                    v.getVehicleManufactureYear(),
                    enums.VehicleColor.valueOf(v.getVehicleColor().toUpperCase()),
                    createEngine(v.getVehicleEngine(), v.getId())));
        }

        updateVehiclesWithRents(vehicleList);
        return vehicleList;
    }

    public Vehicle getVehicle(Long id) throws NotVehicleException {
        Vehicles vehiclesEntity = vehiclesService.get(id);
        assert (vehiclesEntity != null);
        return new Vehicle(vehiclesEntity.getVehicleModel(),
                Math.toIntExact(vehiclesEntity.getId()),
                getVehicleTypeByID(Math.toIntExact(vehiclesEntity.getTypeID())),
                vehiclesEntity.getVehicleRegistrationIdentifier(),
                vehiclesEntity.getVehicleMass(),
                vehiclesEntity.getVehicleMileage(),
                vehiclesEntity.getVehicleManufactureYear(),
                enums.VehicleColor.valueOf(vehiclesEntity.getVehicleColor().toUpperCase()),
                createEngine(vehiclesEntity.getVehicleEngine(), vehiclesEntity.getId()));
    }

    private void updateVehiclesWithRents(List<Vehicle> vehicles) throws NotVehicleException {
        for (Vehicle v : vehicles) {
            v.createRentHistory(rentsEntityMapper.getRentsList());
        }
    }

    private VehicleType getVehicleTypeByID(int vehicleTypeID) throws NotVehicleException {
        for (VehicleType t : vehicleTypesEntityMapper.getVehicleTypesList()) {
            if (vehicleTypeID == t.getTypeID()) {
                return t;
            }
        }

        return null;
    }

    private Startable createEngine(String engineName, Long id) {
        Engines certainVehicleEngine = null;
        for (Engines e : enginesService.getAll()) {
            if (e.getVehicleID().equals(id)) {
                certainVehicleEngine = e;
                break;
            }
        }

        Startable engine = null;
        switch (engineName) {
            case Constants.ELECTRICAL_ENGINE_NAME -> {
                assert certainVehicleEngine != null;
                engine = new ElectricalEngine(certainVehicleEngine.getElectricityConsumption(), certainVehicleEngine.getBatteryCharge());
            }
            case Constants.DIESEL_ENGINE_NAME -> {
                assert certainVehicleEngine != null;
                engine = new DieselEngine(certainVehicleEngine.getEngineVolume(),
                        certainVehicleEngine.getTankVolume(),
                        certainVehicleEngine.getFuelConsumption());
            }
            case Constants.GASOLINE_ENGINE_NAME -> {
                assert certainVehicleEngine != null;
                engine = new GasolineEngine(certainVehicleEngine.getEngineVolume(),
                        certainVehicleEngine.getTankVolume(),
                        certainVehicleEngine.getFuelConsumption());
            }
        }

        return engine;
    }
}
