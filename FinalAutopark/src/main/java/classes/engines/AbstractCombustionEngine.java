package classes.engines;

public class AbstractCombustionEngine extends AbstractEngine{
    private double engineVolumeInCubicCm;
    private double fuelTankVolume;
    private double fuelConsumptionPerHundredKmInLiters;

    public AbstractCombustionEngine(String engineName, double engineTaxPerMonth,
                                    double engineVolumeInCubicCm, double fuelTankVolume,
                                    double fuelConsumptionPerHundredKmInLiters) {
        super(engineName, engineTaxPerMonth);
        this.engineVolumeInCubicCm = engineVolumeInCubicCm;
        this.fuelTankVolume = fuelTankVolume;
        this.fuelConsumptionPerHundredKmInLiters = fuelConsumptionPerHundredKmInLiters;
    }

    public double getEngineVolumeInCubicCm() {
        return engineVolumeInCubicCm;
    }

    public double getFuelTankVolume() {
        return fuelTankVolume;
    }

    public double getFuelConsumptionPerHundredKmInLiters() {
        return fuelConsumptionPerHundredKmInLiters;
    }

    @Override
    public double getCurrentBatteryCharge() {
        return 0;
    }

    @Override
    public int getElectricityConsumptionPerKmInkVatPerHour() {
        return 0;
    }

    @Override
    public double getMaxKilometers() {
        return fuelTankVolume * 100 / fuelConsumptionPerHundredKmInLiters;
    }

    @Override
    public String toString() {
        return super.toString() + "," +
                engineVolumeInCubicCm + "," +
                fuelTankVolume + "," +
                fuelConsumptionPerHundredKmInLiters + "\"";
    }
}
