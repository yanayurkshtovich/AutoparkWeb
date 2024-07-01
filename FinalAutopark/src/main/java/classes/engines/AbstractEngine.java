package classes.engines;

public abstract class AbstractEngine implements Startable {
    private String engineName;
    private double engineTaxPerMonth;

    public AbstractEngine(String engineName, double engineTaxPerMonth) {
        this.engineName = engineName;
        this.engineTaxPerMonth = engineTaxPerMonth;
    }

    @Override
    public String getEngineName() {
        return engineName;
    }

    @Override
    public double getEngineTaxPerMonth() {
        return engineTaxPerMonth;
    }

    @Override
    public String toString() {
        return "\"" +
                engineName + ',' +
                engineTaxPerMonth;
    }
}
