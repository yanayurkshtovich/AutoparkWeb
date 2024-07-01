package dtoMappers;

import dtos.OrderDto;
import entities.Orders;
import infrastructure.core.annotations.Autowired;
import infrastructure.dto.EntityManager;
import lombok.NoArgsConstructor;
import services.Mechanic;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class OrderDtoMapper {
    @Autowired
    private Mechanic mechanic;
    @Autowired
    private EntityManager entityManager;

    public List<OrderDto> getOrderDtos() {
        return entityManager.getAll(Orders.class).stream().map(orders -> {
            try {
                return OrderDto.builder()
                        .vehicleID(Math.toIntExact(orders.getVehicleID()))
                        .breakdownsMap(mechanic.detectBreaking(Math.toIntExact(orders.getVehicleID())))
                        .build();
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
