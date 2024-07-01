package classes.engines;

import utils.Constants;
import utils.TechnicalSpecialistUtil;

public class ElectricalEngine extends AbstractEngine{
    private double currentBatteryCharge;
    private int electricityConsumptionPerKmInkVatPerHour;

    public ElectricalEngine(int electricityConsumptionPerKmInkVatPerHour, double currentBatteryCharge) {
        super(Constants.ELECTRICAL_ENGINE_NAME, Constants.ElectricalEngineTaxPerMonth);
        this.electricityConsumptionPerKmInkVatPerHour = electricityConsumptionPerKmInkVatPerHour;
        this.currentBatteryCharge = currentBatteryCharge;
    }

    @Override
    public double getCurrentBatteryCharge() {
        return currentBatteryCharge;
    }

    @Override
    public int getElectricityConsumptionPerKmInkVatPerHour() {
        return electricityConsumptionPerKmInkVatPerHour;
    }

    public double getMaxKilometers() {
        return currentBatteryCharge / electricityConsumptionPerKmInkVatPerHour;
    }

    @Override
    public double getEngineVolumeInCubicCm() {
        return 0;
    }

    @Override
    public double getFuelTankVolume() {
        return 0;
    }

    @Override
    public double getFuelConsumptionPerHundredKmInLiters() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString() + "," +
                this.currentBatteryCharge + "," +
                this.electricityConsumptionPerKmInkVatPerHour + "\"";
    }
}
