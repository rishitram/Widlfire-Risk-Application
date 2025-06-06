public class Climate {
    private double averageTemperature;
    private double annualRainfall; 
    private int dryMonthsPerYear;

    public Climate(double averageTemperature, double annualRainfall, int dryMonthsPerYear) {
        this.averageTemperature = averageTemperature;
        this.annualRainfall = annualRainfall;
        this.dryMonthsPerYear = dryMonthsPerYear;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public double getAnnualRainfall() {
        return annualRainfall;
    }

    public void setAnnualRainfall(double annualRainfall) {
        this.annualRainfall = annualRainfall;
    }

    public int getDryMonthsPerYear() {
        return dryMonthsPerYear;
    }

    public void setDryMonthsPerYear(int dryMonthsPerYear) {
        this.dryMonthsPerYear = dryMonthsPerYear;
    }
    public String toString() {
        return "Climate{" +
           "dryMonthsPerYear=" + dryMonthsPerYear +
           ", annualRainfall=" + annualRainfall +
           ", averageTemperature=" + averageTemperature +
           '}';
}

}
