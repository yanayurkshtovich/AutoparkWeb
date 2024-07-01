package classes.engines;
import utils.Constants;

public class GasolineEngine extends AbstractCombustionEngine{
    public GasolineEngine(double engineVolumeInCubicCm, double fuelTankVolume, double fuelConsumptionPerHundredKmInLiters) {
        super(Constants.GASOLINE_ENGINE_NAME,
                Constants.GasolineEngineTaxPerMonth,
                engineVolumeInCubicCm,
                fuelTankVolume,
                fuelConsumptionPerHundredKmInLiters);
    }
}
