package infrastructure.databaseServices.entityServices;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entities.Engines;

import java.util.List;

public class EnginesService {
    @Autowired
    EntityManager entityManager;

    public EnginesService() {}

    @InitMethod
    public void init() {

    }

    public Engines get(Long id) {
        return entityManager.get(id, Engines.class).orElse(null);
    }

    public List<Engines> getAll() {
        return entityManager.getAll(Engines.class);
    }

    public Long save(Engines engine) {
        return entityManager.save(engine);
    }
}
