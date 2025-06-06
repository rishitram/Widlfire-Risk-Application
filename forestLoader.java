import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class forestLoader {

    // Map to store state names and their forest counts
    private Map<String, Integer> forestCounts = new HashMap<>();

    // Loads forest count data from a CSV file
    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            
            // Skip the header line
            br.readLine(); 

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Split the line into parts, allowing for empty fields
                String[] parts = line.split(",", -1);

                // Make sure we have at least two parts: state and count
                if (parts.length >= 2) {
                    // Get the state name and trim extra spaces
                    String state = parts[0].trim();

                    // Convert the second part to an integer (forest count)
                    int count = Integer.parseInt(parts[1].trim());

                    // Put the state and count into the map
                    forestCounts.put(state, count);
                }
            }
        } catch (Exception e) {
            // Print error message if something goes wrong
            System.err.println("Failed to load forest counts: " + e.getMessage());
        }
    }

    // Returns the forest count for a state, or 0 if not found
    public int getForestCount(String state) {
        return forestCounts.getOrDefault(state, 0);
    }
}
