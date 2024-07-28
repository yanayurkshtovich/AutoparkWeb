package infrastructure.databaseServices.entityMappers;

import classes.Rent;
import infrastructure.databaseServices.entities.Rents;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entityServices.RentsService;

import java.util.ArrayList;
import java.util.List;

public class RentsEntityMapper {
    @Autowired
    RentsService rentsService;

    public List<Rent> getRentsList() throws NotVehicleException {
        List<Rent> rentsList = new ArrayList<>();
        for (Rents r : rentsService.getAll()) {
            rentsList.add(new Rent(r.getRentCost(), r.getRentDate(), Math.toIntExact(r.getVehicleID())));
        }

        return rentsList;
    }
}
