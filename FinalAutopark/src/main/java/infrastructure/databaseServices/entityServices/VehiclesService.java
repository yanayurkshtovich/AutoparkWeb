package infrastructure.databaseServices.entityServices;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entities.Vehicles;

import java.util.List;

public class VehiclesService {
    @Autowired
    EntityManager entityManager;

    public VehiclesService() {}

    @InitMethod
    public void init() {

    }

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).orElse(null);
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicle) {
        return entityManager.save(vehicle);
    }
}
