package infrastructure.dto.impl;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.impl.Context;
import infrastructure.dto.ConnectionFactory;
import infrastructure.dto.EntityManager;
import infrastructure.dto.PostgreDataBase;

import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBase dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {}

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }

    @Override
    public void deleteRowByID(Long id, String tableName) {
        dataBaseService.deleteRowByID(id, tableName);
    }
}
