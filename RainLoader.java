import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class RainLoader {

    private Map<String, Integer> rainMap = new HashMap<>();

    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length >= 2) {
                    String state = parts[0].trim().toLowerCase();

                    // Parse as double to allow decimal values
                    double inches = Double.parseDouble(parts[1].trim());

                    // Convert inches to millimeters and round to nearest int
                    int rain = (int) Math.round(inches * 25.4);

                    rainMap.put(state, rain);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load rain counts: " + e.getMessage());
        }
    }

    public int getRainCount(String state) {
        return rainMap.getOrDefault(state.toLowerCase(), 0);
    }
}
