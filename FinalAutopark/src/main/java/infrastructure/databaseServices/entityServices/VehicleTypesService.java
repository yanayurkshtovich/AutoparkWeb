package infrastructure.databaseServices.entityServices;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entities.VehicleTypes;

import java.util.List;
import java.util.Optional;

public class VehicleTypesService {
    @Autowired
    EntityManager entityManager;

    public VehicleTypesService() {}

    @InitMethod
    public void init() {

    }

    public VehicleTypes get(Long id) {
        return entityManager.get(id, VehicleTypes.class).orElse(null);
    }

    public List<VehicleTypes> getAll() {
        return entityManager.getAll(VehicleTypes.class);
    }

    public Long save(VehicleTypes type) {
        return entityManager.save(type);
    }
}
