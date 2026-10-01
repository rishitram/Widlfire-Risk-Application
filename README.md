# Wildfire Risk Housing App

A Java-based command-line application that helps buyers and sellers assess wildfire risk for residential properties. The app combines local environmental data, county risk information, housing materials, and safety features to estimate a wildfire risk score from 1 to 10.

## Overview

This project models a simple real-estate workflow:

- Sellers add a home listing with property and location details
- Buyers search for homes by state and review a risk score
- The application uses local climate and wildfire-risk datasets to produce an estimate
- Additional risk-reduction features can lower the score for a property

The app is designed as a lightweight CLI tool and stores listings in `houses.csv`.

## Features

- Seller workflow to enter property details
- Buyer workflow to browse listings by state
- Wildfire risk scoring based on:
  - building material
  - roof material
  - distance to vegetation
  - dry months per year
  - annual rainfall
  - average temperature
  - county wildfire risk tier
  - state forest density
  - optional fire-safety features
- CSV-based listing storage for quick searching and comparison

## Risk scoring model

Risk is calculated in `RiskCalculator.java` using a base score and then adjusted for protective features. The scoring logic includes:

- Higher risk for wood framing or wood roofs
- Increased risk when homes are close to vegetation
- Additional risk when rainfall is low, temperatures are high, or the area has many dry months
- County-specific risk ratings imported from `NRI_Table_Counties.csv`
- Forest density data from `NationalForests.csv`
- Safety features such as sprinkler systems and protective vents reduce the risk score

The final score is clamped to a range of 1 to 10.

## Data sources

The app loads the following datasets:

- `temp.csv` — temperature data used to support climate context
- `rain.csv` — rainfall data used to estimate climate risk
- `NationalForests.csv` — forest counts by state
- `NRI_Table_Counties(1).csv` — county wildfire risk classifications

These are parsed by loader classes such as:

- `TempLoader.java`
- `RainLoader.java`
- `forestLoader.java`
- `RiskLoader.java`

## Prerequisites

- Java JDK 8 or newer
- A terminal or command prompt

## Getting started

1. Open a terminal in the project folder.
2. Compile the Java files:

```bash
javac *.java
```

3. Run the application:

```bash
java Main
```

## How to use the app

When the program starts, it prompts with:

- `buyer`
- `seller`
- `exit`

### Seller flow

If you choose `seller`, the app asks for:

- house material
- roof material
- proximity to vegetation (in feet)
- county
- state
- average dry months per year
- optional fire protection features

Example features include:

- fire resistant windows
- sprinkler system
- protective vents
- spark arresters

The program calculates a risk score and appends the listing to `houses.csv`.

### Buyer flow

If you choose `buyer`, the app asks for a state or `all` to view all listings. It reads the saved CSV records, recreates the houses in memory, and prints each listing with its estimated wildfire risk score.

## Example workflow

```text
Welcome to Wildfire Risk Housing App
Are you a buyer, seller, or do you want to exit?
Enter your choice: seller
Enter house material: wood
Enter roof material: asphalt
Enter proximity to vegetation (in feet): 25
Enter county: Los Angeles
Enter state: California
Enter average dry months per year: 7
Enter any additional features (e.g. fire resistant windows, sprinkler system, vents): sprinkler system
House listed successfully! Risk Score: 7
```

## Notes

- The project is intentionally simple and uses CSV files instead of a database.
- Risk estimates are heuristic and intended for educational or prototype use.
- Data quality and local conditions may affect how closely the result matches real-world wildfire risk.

## License

This project does not currently include a license file. If you plan to share or distribute it, add a suitable open-source license before public use.

## Contributing

Contributions are welcome. A good starting point would be to improve the scoring model, add validation for user input, support additional counties/states, or convert the app to a graphical interface or web app.
