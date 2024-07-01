package classes.engines;

public interface Startable {
    public double getEngineTaxPerMonth();
    public double getMaxKilometers();
    public String getEngineName();
    public double getEngineVolumeInCubicCm();
    public double getFuelTankVolume();
    public double getFuelConsumptionPerHundredKmInLiters();
    public double getCurrentBatteryCharge();
    public int getElectricityConsumptionPerKmInkVatPerHour();
}
