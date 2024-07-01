package entityMappers;

import classes.VehicleType;
import entities.VehicleTypes;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import infrastructure.dto.EntityManager;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class VehicleTypesEntityMapper {
    @Autowired
    EntityManager entityManager;

    public List<VehicleType> getVehicleTypesList() throws NotVehicleException {
        List<VehicleType> vehicleTypeList = new ArrayList<>();
        for (VehicleTypes t : entityManager.getAll(VehicleTypes.class)) {
            vehicleTypeList.add(new VehicleType(t.getName(), t.getCoefTaxes(), Math.toIntExact(t.getId())));
        }

        return vehicleTypeList;
    }

    public VehicleType getVehicleType(Long id) throws NotVehicleException {
        VehicleTypes vehicleTypeEntity = entityManager.get(id, VehicleTypes.class).orElse(null);
        assert (vehicleTypeEntity != null);
        return new VehicleType(vehicleTypeEntity.getName(),
                vehicleTypeEntity.getCoefTaxes(),
                Math.toIntExact(vehicleTypeEntity.getId()));
    }
}
