package dto;

import dto.dtos.OrderDto;
import infrastructure.databaseServices.entities.Orders;
import infrastructure.core.annotations.Autowired;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entityServices.OrdersService;
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
    private OrdersService ordersService;

    public List<OrderDto> getOrderDtos() {
        return ordersService.getAll().stream().map(orders -> {
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
