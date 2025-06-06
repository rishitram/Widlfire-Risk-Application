import java.io.*;
import java.util.*;

public class Main {
    private static final String HOUSE_FILE = "houses.csv"; // File to store listed house data

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main menu loop allows user to keep interacting until they choose to exit
        while (true) {
            System.out.println("=================================");
            System.out.println("Welcome to Wildfire Risk Housing App");
            System.out.println("Are you a buyer, seller, or do you want to exit?");
            System.out.print("Enter your choice: ");
            String role = scanner.nextLine().trim().toLowerCase();

            // Route to appropriate function based on user input
            if (role.equals("seller")) {
                sellHouse(scanner);
            } else if (role.equals("buyer")) {
                buyHouse(scanner);
            } else if (role.equals("exit")) {
                System.out.println("Thank you for using the app. Goodbye!");
                break; // Exit the loop and end the program
            } else {
                System.out.println("Invalid input. Please enter 'buyer', 'seller', or 'exit'.");
            }
        }

        scanner.close(); // Close the scanner once the loop ends
    }

    // Method for sellers to input a new house listing
    private static void sellHouse(Scanner scanner) {
        try (FileWriter fw = new FileWriter(HOUSE_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Loaders to fetch rainfall and temperature info
            RainLoader rainLoader = new RainLoader();
            TempLoader tempLoader = new TempLoader();

            // Gather house details from seller
            System.out.println("Enter house material:");
            String material = scanner.nextLine();
            System.out.println("Enter roof material:");
            String roofMaterial = scanner.nextLine();
            System.out.println("Enter proximity to vegetation (in feet):");
            double proximity = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter county:");
            String county = scanner.nextLine();
            System.out.println("Enter state:");
            String state = scanner.nextLine();
            System.out.println("Enter average dry months per year:");
            int dryMonths = Integer.parseInt(scanner.nextLine());

            // Optional fire safety features
            System.out.println("Enter any additional features (e.g. fire resistant windows, sprinkler system, vents):");
            String features = scanner.nextLine();
            OtherFactors otherFactors = new OtherFactors(false, false, false, false);
            otherFactors.calculateFactors(features);

            // Construct house objects from input
            Location location = new Location(county, state, proximity);
            Climate climate = new Climate(dryMonths, rainLoader.getRainCount(state), tempLoader.getTempCount(county));
            House house = new House(material, roofMaterial, location, climate);

            // Calculate wildfire risk score using user's input
            int score = RiskCalculator.calculateRiskScore(house, otherFactors);

            // Save the listing to the CSV file
            out.printf("%s,%s,%f,%s,%s,%d,%d,%d,%s\n",
                    material, roofMaterial, proximity, county, state,
                    dryMonths, rainLoader.getRainCount(state), tempLoader.getTempCount(county), features.replaceAll(",", ";"));

            System.out.println("House listed successfully! Risk Score: " + score);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error saving house: " + e.getMessage());
        }
    }

    // Method for buyers to browse house listings
    private static void buyHouse(Scanner scanner) {
        System.out.println("Enter the state you're interested in or enter \"all\" to view all houses:");
        String desiredState = scanner.nextLine().trim().toLowerCase();

        List<House> matchedHouses = new ArrayList<>();
        List<String> featuresList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(HOUSE_FILE))) {
            String line;
            br.readLine(); // Skip the header line if present

            // Read through each line and filter by state
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) continue; // Skip malformed lines

                // Extract house details from file
                String material = fields[0];
                String roofMaterial = fields[1];
                double proximity = Double.parseDouble(fields[2]);
                String county = fields[3];
                String state = fields[4];
                int dryMonths = Integer.parseInt(fields[5]);
                int rainfall = Integer.parseInt(fields[6]);
                int temperature = Integer.parseInt(fields[7]);
                String features = fields.length >= 9 ? fields[8].replace(";", ",") : "";

                // Filter based on user's selected state
                if (desiredState.equals("all") || state.toLowerCase().equals(desiredState)) {
                    RainLoader rainLoader = new RainLoader();
                    TempLoader tempLoader = new TempLoader();
                    Location location = new Location(county, state, proximity);
                    Climate climate = new Climate(dryMonths, rainfall, temperature);
                    House house = new House(material, roofMaterial, location, climate);
                    OtherFactors otherFactors = new OtherFactors(false, false, false, false);
                    otherFactors.calculateFactors(features);

                    matchedHouses.add(house);
                    featuresList.add(features);
                }
            }

            // Display results
            if (matchedHouses.isEmpty()) {
                System.out.println("No houses found.");
                return;
            }

            System.out.println("Available Houses:");
            int index = 1;
            for (int i = 0; i < matchedHouses.size(); i++) {
                House house = matchedHouses.get(i);
                String features = featuresList.get(i);
                OtherFactors otherFactors = new OtherFactors(false, false, false, false);
                otherFactors.calculateFactors(features);
                int score = RiskCalculator.calculateRiskScore(house, otherFactors);

                System.out.println("-- House " + index + " --");
                System.out.println(house); // toString() prints location + climate + material info
                System.out.println("Features: " + features);
                System.out.println("Risk Score: " + score);
                index++;
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading houses: " + e.getMessage());
        }
    }
}
