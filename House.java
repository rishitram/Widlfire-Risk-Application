public class House {
    private String material; 
    private Location location;
    private Climate climate;
    private String roofMaterial;
    private String otherFactors;
    
    //rigor
    public House(String material, String roofMaterial, Location location, Climate climate) {
        this.material = material;
        this.roofMaterial = roofMaterial;
        this.location = location;
        this.climate = climate;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    
    public String getRoofMaterial() {
        return roofMaterial;
    }

    public void setRoofMaterial(String roofMaterial) {
        this.material = roofMaterial;
    }
    
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }
    public String toString() {
        return "House{" +
           "material='" + material + '\'' +
           ", roofMaterial='" + roofMaterial + '\'' +
           ", location=" + location +
           ", climate=" + climate +
           '}';
}

    
    
}