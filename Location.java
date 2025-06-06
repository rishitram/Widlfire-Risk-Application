public class Location {
    private String county;
    private String state;
    private double proximityToVegetation; 

    public Location(String county, String state, double proximityToVegetation) {
        this.county = county;
        this.state = state;

        this.proximityToVegetation = proximityToVegetation;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public double getProximityToVegetation() {
        return proximityToVegetation;
    }

    public void setProximityToVegetation(double proximityToVegetation) {
        this.proximityToVegetation = proximityToVegetation;
    }
    
    public String toString() {
        return "Location{" +
           "county='" + county + '\'' +
           ", state='" + state + '\'' +
           ", proximityToVegetation=" + proximityToVegetation +
           '}';
}

}
