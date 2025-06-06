public class OtherFactors {
    private boolean fireResistantWindows;
    private boolean protectiveVents;
    private boolean sprinklerSystem;
    private boolean sparkArresters;

    public OtherFactors(boolean fireResistantWindows, boolean protectiveVents, boolean sprinklerSystem, boolean sparkArresters) {
        this.fireResistantWindows = fireResistantWindows;
        this.protectiveVents = protectiveVents;
        this.sprinklerSystem = sprinklerSystem;
        this.sparkArresters = sparkArresters;
    }

    public boolean usesFireResistantWindows() {
        return fireResistantWindows;
    }

    public boolean hasProtectiveVents() {
        return protectiveVents;
    }

    public boolean hasSprinklerSystem() {
        return sprinklerSystem;
    }

    public boolean hasSparkArresters() {
        return sparkArresters;
    }

    public void calculateFactors(String input) {
        String lower = input.toLowerCase();
        fireResistantWindows = lower.contains("fire resistant windows");
        protectiveVents = lower.contains("vents");
        sprinklerSystem = lower.contains("sprinkler");
        sparkArresters = lower.contains("spark");
    }
}
