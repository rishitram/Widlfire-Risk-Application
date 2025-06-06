import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//rigor
public class RiskLoader {

    // Map to store county names and their risk ratings
    private Map<String, String> countyRiskMap = new HashMap<>();

    // Loads data from a CSV file and fills the map
    public void load(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            br.readLine(); 

            String line;
            // Read each line one by one
            while ((line = br.readLine()) != null) {
                // Split line into two parts: county and risk rating
                String[] parts = line.split(",");

                // Only process lines that have exactly two parts
                if (parts.length == 2) {
                    // Get county name, trim spaces, and make lowercase
                    String county = parts[0].trim().toLowerCase();
                    // Get risk rating and trim spaces
                    String riskRating = parts[1].trim();

                    // Add to the map
                    countyRiskMap.put(county, riskRating);
                }
            }
        } catch (IOException e) {
            // Print error info if something goes wrong
            e.printStackTrace();
        }
    }

    // Returns the risk rating for a county, or "Unknown" if it's not in the map
    public String getRiskRating(String countyName) {
        return countyRiskMap.getOrDefault(countyName.toLowerCase(), "Unknown");
    }
}
