package infrastructure.databaseServices.entityServices;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entities.Rents;

import java.util.List;

public class RentsService {
    @Autowired
    EntityManager entityManager;

    public RentsService() {}

    @InitMethod
    public void init() {

    }

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).orElse(null);
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public Long save(Rents rent) {
        return entityManager.save(rent);
    }
}
