package enums;

public enum VehicleColor {
    RED ("Red"),
    BLUE ("Blue"),
    GREEN ("Green"),
    YELLOW ("Yellow"),
    BLACK ("Black"),
    WHITE ("White"),
    GRAY ("Gray"),
    UNDEFINED ("Undefined");

    private final String colorName;

    private VehicleColor(String colorName) {
        this.colorName = colorName;
    }

    public String getVehicleColor() {
        return colorName;
    }
}
