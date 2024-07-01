package classes.engines;

import utils.Constants;

public class DieselEngine extends AbstractCombustionEngine{
    public DieselEngine(double engineVolumeInCubicCm, double fuelTankVolume, double fuelConsumptionPerHundredKmInLiters) {
        super(Constants.DIESEL_ENGINE_NAME,
                Constants.DieselEngineTaxPerMonth,
                engineVolumeInCubicCm,
                fuelTankVolume,
                fuelConsumptionPerHundredKmInLiters);
    }
}

