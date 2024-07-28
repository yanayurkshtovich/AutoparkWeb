package infrastructure.databaseServices.entityMappers;

import classes.VehicleType;
import infrastructure.databaseServices.entities.VehicleTypes;
import exceptions.NotVehicleException;
import infrastructure.core.annotations.Autowired;
import infrastructure.databaseServices.EntityManager;
import infrastructure.databaseServices.entityServices.VehicleTypesService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class VehicleTypesEntityMapper {
    @Autowired
    VehicleTypesService vehicleTypesService;

    public List<VehicleType> getVehicleTypesList() throws NotVehicleException {
        List<VehicleType> vehicleTypeList = new ArrayList<>();
        for (VehicleTypes t : vehicleTypesService.getAll()) {
            vehicleTypeList.add(new VehicleType(t.getName(), t.getCoefTaxes(), Math.toIntExact(t.getId())));
        }

        return vehicleTypeList;
    }

    public VehicleType getVehicleType(Long id) throws NotVehicleException {
        VehicleTypes vehicleTypeEntity = vehicleTypesService.get(id);
        assert (vehicleTypeEntity != null);
        return new VehicleType(vehicleTypeEntity.getName(),
                vehicleTypeEntity.getCoefTaxes(),
                Math.toIntExact(vehicleTypeEntity.getId()));
    }
}
