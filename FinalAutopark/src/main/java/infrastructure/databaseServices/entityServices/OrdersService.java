package infrastructure.databaseServices.entityServices;

import infrastructure.core.annotations.Autowired;
import infrastructure.core.annotations.InitMethod;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entities.Orders;

import java.util.List;

public class OrdersService {
    @Autowired
    EntityManager entityManager;

    public OrdersService() {}

    @InitMethod
    public void init() {

    }

    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).orElse(null);
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public Long save(Orders order) {
        return entityManager.save(order);
    }

    public void deleteRowByID(Long id, String tableName) {
        entityManager.deleteRowByID(id, tableName);
    }
}
