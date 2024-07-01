package entityMappers;

import classes.Rent;
import entities.Rents;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import infrastructure.dto.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class RentsEntityMapper {
    @Autowired
    EntityManager entityManager;

    public List<Rent> getRentsList() throws NotVehicleException {
        List<Rent> rentsList = new ArrayList<>();
        for (Rents r : entityManager.getAll(Rents.class)) {
            rentsList.add(new Rent(r.getRentCost(), r.getRentDate(), Math.toIntExact(r.getVehicleID())));
        }

        return rentsList;
    }
}
