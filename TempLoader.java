import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class TempLoader {

    private Map<String, Double> tempCounts = new HashMap<>();

    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length >= 2) {
                    String county = parts[0].replace(" County", "").trim().toLowerCase();
                    double temp = Double.parseDouble(parts[1].trim());
                    tempCounts.put(county, temp);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load temp counts: " + e.getMessage());
        }
    }

    public int getTempCount(String county) {
        return (int) Math.round(tempCounts.getOrDefault(county.toLowerCase(), 68.0)); // 68°F default
    }
}
