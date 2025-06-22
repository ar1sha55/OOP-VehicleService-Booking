import java.util.*;

class Vehicle{
    private String plateNum;
    private int lastServiceOdometer;
    private int currentOdometer;
    private String vehicleType; //sedan,suv,mpv
    private String colour;
    private String brand;
    private String model;

    //Constructor
    public Vehicle(String pN, int lSodo, int cOdo, String vType, String c, String b, String m){
        plateNum = pN;
        lastServiceOdometer = lSodo;
        currentOdometer = cOdo;
        vehicleType = vType;
        colour = c;
        brand = b;
        model = m;
    }

    //Accessor
    public String getPlateNum () {return plateNum;}
    public int getlastServiceOdometer () {return lastServiceOdometer;}
    public int getCurrentOdometer () {return currentOdometer;}
    public String getVehicleType () {return vehicleType;}
    public String getColour () {return colour;}
    public String getBrand () {return brand;}
    public String getModel() {return model;}

    //Setter
    public void setPlateNum (String plateNum) {this.plateNum = plateNum;}
    public void setlastServiceOdometer (int lastServiceOdometer) {this.lastServiceOdometer = lastServiceOdometer;}
    public void setCurrentOdometer (int currentOdometer) {this.currentOdometer = currentOdometer;}
    public void setVehicleType (String vehicleType) {this.vehicleType = vehicleType;}
    public void setColour (String colour) {this.colour = colour;}
    public void setBrand (String brand) {this.brand = brand;}
    public void setModel(String model) {this.model = model;}

    //Update odometer
    public void updateOdometer(int newOdo){
        if (newOdo > currentOdometer){
            currentOdometer = newOdo;
        }
        else {
            System.out.println("Latest odometer reading must be higher than current odometer reading");
        }
    }


    //Service reminder based on odometer
    public ArrayList<String> serviceReminder(){
        ArrayList<String> services = new ArrayList<>();
            switch (vehicleType.toUpperCase()){
                case "SEDAN":
                    if ((currentOdometer-lastServiceOdometer) >= 5000) {
                        services.add("Preventative Maintenance & Oil Change ");
                    }
                    if ((currentOdometer-lastServiceOdometer) >= 10000) {
                        services.add("Tire Rotation (every 6 months / 10,000km)");
                    }
                    if ((currentOdometer-lastServiceOdometer) >= 20000) {
                        services.add("Wheel Balancing, Brake Inspection, and Alignment Check (every 12 months / 20,000km)");
                    }
                    if ((currentOdometer-lastServiceOdometer) >= 40000) {
                        services.add("Cooling System Check, Engine Service, and Transmission Inspection (every 24 months / 40,000km)");
                    }
            }
    }

}