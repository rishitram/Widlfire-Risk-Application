public class RiskCalculator {

    // Loaders to import external data sources needed for risk calculation
    private static RiskLoader riskLoader = new RiskLoader();
    private static forestLoader forestLoader = new forestLoader();
    private static RainLoader rainLoader = new RainLoader();
    private static TempLoader tempLoader = new TempLoader();


    /*
     * Static block to initialize and load all necessary data files
     * These files provide context like rainfall, temperature, forests, and risk ratings by county
     */
    static {
        riskLoader.load("NRI_Table_Counties.csv");
        forestLoader.load("NationalForests.csv");
        tempLoader.load("temp.csv");
        rainLoader.load("rain.csv");
    }

    /*
     * This method calculates a base wildfire risk score for a given house.
     * The score is based on material, proximity to vegetation, climate, county risk rating, and forest count.
     */
    public static int calculateRiskScore(House house) {
        int score = 0;

        // Evaluate construction material
        String material = house.getMaterial().toLowerCase();
        if (material.contains("wood")) {
            score += 3;
        } else if (material.contains("brick")) {
            score -= 2;
        } else if (material.contains("concrete")) {
            score -= 1;
        } else {
            score -= 2;
        }

        // Evaluate roof material
        String roofMaterial = house.getRoofMaterial().toLowerCase();
        if (roofMaterial.contains("wood")) {
            score += 3;
        } else if (roofMaterial.contains("brick")) {
            score -= 2;
        } else if (roofMaterial.contains("concrete")) {
            score -= 1;
        } else {
            score -= 2;
        }

        // Assess proximity to vegetation
        double proximity = house.getLocation().getProximityToVegetation();
        if (proximity < 10) {
            score += 2;
        } else if (proximity < 30) {
            score += 1;
        } else if (proximity < 100) {
            score -= 1;
        } else {
            score -= 2;
        }

        // Evaluate climate risk factors
        Climate climate = house.getClimate();
        if (climate.getDryMonthsPerYear() > 6) score += 2;
        if (climate.getAnnualRainfall() < 500) score += 2;
        if (climate.getAverageTemperature() > 30) score += 2;

        // Get regional wildfire risk rating by county
        String county = house.getLocation().getCounty();
        String rating = riskLoader.getRiskRating(county);
        if (rating != null) {
            switch (rating.toLowerCase()) {
                case "very high":
                    score += 3;
                    break;
                case "relatively high":
                    score += 2;
                    break;
                case "relatively moderate":
                    break; // no score change
                case "relatively low":
                    score -= 2;
                    break;
                case "very low":
                    score -= 3;
                    break;
            }
        }

        // Count national forests in the state
        String state = house.getLocation().getState();
        int forestCount = forestLoader.getForestCount(state);
        if (forestCount > 10) {
            score += 3;
        } else if (forestCount > 5) {
            score += 1;
        } else {
            score -= 1;
        }

        return score;
    }

    /*
     * This overloaded method adds more precision to the risk score by including
     * user-specified safety features (stored in OtherFactors).
     * It adjusts the score downward for each protective measure.
     */
    public static int calculateRiskScore(House house, OtherFactors factors) {
        int score = calculateRiskScore(house); // Get base score

        // Adjust score based on additional fire safety features
        if (factors.usesFireResistantWindows()) score -= 1;
        if (factors.hasProtectiveVents()) score -= 1;
        if (factors.hasSprinklerSystem()) score -= 2;
        if (factors.hasSparkArresters()) score -= 1;

        // Clamp score between 1 (lowest risk) and 10 (highest risk)
        return Math.max(1, Math.min(score, 10));
    }
}
