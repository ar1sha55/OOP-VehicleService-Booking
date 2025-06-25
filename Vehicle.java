class Vehicle{
    private String plateNum;
    private int lastServiceOdometerType1;
    private int lastServiceOdometerType2;
    private int lastServiceOdometerType3;
    private int lastServiceOdometerType4;
    private int currentOdometer;
    private VehicleType vehicleType; //sedan,suv,mpv
    private String colour;
    private String brand;
    private String model;

    //Constructor
    public Vehicle(String pN, int lSodo1,int lSodo2,int lSodo3,int lSodo4, int cOdo, VehicleType vType, String c, String b, String m){
        try {
            plateNum = pN.toUpperCase();
            lastServiceOdometerType1 = lSodo1;
            lastServiceOdometerType2 = lSodo2;
            lastServiceOdometerType3 = lSodo3;
            lastServiceOdometerType4 = lSodo4;
            currentOdometer = cOdo;
            vehicleType = vType;
            colour = c.toUpperCase();
            brand = b.toUpperCase();
            model = m.toUpperCase();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid input entered, Please make sure correct format! \nError: " + e.getMessage());
        }
    }

    public Vehicle(VehicleType vType, String pN, int currentOdo){
        this(pN, 0, 0, 0, 0, currentOdo, vType, "", "", "");
    }

    //Accessor
    public String getPlateNum () {return plateNum;}
    public int getlastServiceOdometerType1 () {return lastServiceOdometerType1;}
    public int getlastServiceOdometerType2 () {return lastServiceOdometerType2;}
    public int getlastServiceOdometerType3 () {return lastServiceOdometerType3;}
    public int getlastServiceOdometerType4 () {return lastServiceOdometerType4;}
    public int getCurrentOdometer () {return currentOdometer;}
    public VehicleType getVehicleType () {return vehicleType;}
    public String getColour () {return colour;}
    public String getBrand () {return brand;}
    public String getModel() {return model;}

    //Setter
    public void setPlateNum (String plateNum) {this.plateNum = plateNum;}
    public void setlastServiceOdometerType1 (int lastServiceOdometerType1) {this.lastServiceOdometerType1 = lastServiceOdometerType1;}
    public void setlastServiceOdometerType2 (int lastServiceOdometerType2) {this.lastServiceOdometerType2 = lastServiceOdometerType2;}
    public void setlastServiceOdometerType3 (int lastServiceOdometerType3) {this.lastServiceOdometerType3 = lastServiceOdometerType3;}
    public void setlastServiceOdometerType4 (int lastServiceOdometerType4) {this.lastServiceOdometerType4 = lastServiceOdometerType4;}
    public void setCurrentOdometer (int currentOdometer) {this.currentOdometer = currentOdometer;}
    public void setVehicleType (VehicleType vehicleType) {this.vehicleType = vehicleType;}
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
        ArrayList<String> reminder = new ArrayList<>();

            switch (vehicleType){
                case SEDAN:
                    if ((lastServiceOdometerType1 + 5000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType1 + 5000) - currentOdometer) + "KM left for Preventative Maintenance & Oil Change");
                    }
                    else {reminder.add("Need Preventative Maintenance & Oil Change, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType2 + 10000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType2 + 10000) - currentOdometer) + "KM left for Tire Rotation");
                    }
                    else {reminder.add("Need Tire Rotation, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType3 + 20000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType3 + 20000) - currentOdometer) + "KM left for Wheel Balancing, Brake Inspection, and Alignment Check");
                    }
                    else {reminder.add("Need Wheel Balancing, Brake Inspection, and Alignment Check, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType4 + 40000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType4 + 40000) - currentOdometer) + "KM left for Cooling System, Engine, and Transmission Check");
                    }
                    else {reminder.add("Need Cooling System, Engine, and Transmission Check, Set Up a service appointment as soon as possible");}

                    break;

                    case SUV:
                    if ((lastServiceOdometerType1 + 7000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType1 + 7000) - currentOdometer) + "KM left for Preventative Maintenance & Oil Change");
                    }
                    else {reminder.add("Need Preventative Maintenance & Oil Change, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType2 + 12500) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType2 + 12500) - currentOdometer) + "KM left for Tire Rotation");
                    }
                    else {reminder.add("Need Tire Rotation, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType3 + 25000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType3 + 25000) - currentOdometer) + "KM left for Wheel Balancing, Brake Inspection, and Alignment Check");
                    }
                    else {reminder.add("Need Wheel Balancing, Brake Inspection, and Alignment Check, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType4 + 45000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType4 + 45000) - currentOdometer) + "KM left for Cooling System, Engine, and Transmission Check");
                    }
                    else {reminder.add("Need Cooling System, Engine, and Transmission Check, Set Up a service appointment as soon as possible");}

                    break;

                    case MPV:
                    if ((lastServiceOdometerType1 + 10000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType1 + 10000) - currentOdometer) + "KM left for Preventative Maintenance & Oil Change");
                    }
                    else {reminder.add("Need Preventative Maintenance & Oil Change, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType2 + 15000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType2 + 15000) - currentOdometer) + "KM left for Tire Rotation");
                    }
                    else {reminder.add("Need Tire Rotation, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType3 + 30000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType3 + 30000) - currentOdometer) + "KM left for Wheel Balancing, Brake Inspection, and Alignment Check");
                    }
                    else {reminder.add("Need Wheel Balancing, Brake Inspection, and Alignment Check, Set Up a service appointment as soon as possible");}

                    if ((lastServiceOdometerType4 + 50000) - currentOdometer >0) {
                        reminder.add(((lastServiceOdometerType4 + 50000) - currentOdometer) + "KM left for Cooling System, Engine, and Transmission Check");
                    }
                    else {reminder.add("Need Cooling System, Engine, and Transmission Check, Set Up a service appointment as soon as possible");}

                    break;

                default:
                    reminder.add("Please consult a technician");
                    break;
                }
        return reminder;
    }

    //Display owned vehicle information
    public void displayInfo(){
        System.out.println("\n-------------- Vehicle Info --------------");
        System.out.println("Plate Number: " + plateNum);
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Colour: " + colour);
        System.out.println("Vehicle Type: " + vehicleType);
        System.out.println("Current Odometer: " + currentOdometer);
        System.out.println("\n-- Last Service Odometer Info --");
        System.out.println("Preventative Maintenance & Oil Change: " + lastServiceOdometerType1);
        System.out.println("Tire Rotation: " + lastServiceOdometerType2);
        System.out.println("Wheel Balancing, Brake Inspection, and Alignment Check: " + lastServiceOdometerType3);
        System.out.println("Cooling System, Engine, and Transmission Check: " + lastServiceOdometerType4);
    }

    public void reminderDisplay(){
        System.out.println("\n-- Upcoming Service Reminder --");
        ArrayList<String> reminders = serviceReminder();
        for (int i = 0; i < reminders.size(); i++) {
            System.out.println((i+1) + ". " + reminders.get(i));
        }
    }


    @Override
    public String toString() {
        return "Vehicle (" + plateNum + ")" + brand + " - " + model + "(" + colour + ", " + vehicleType + ")";
    }
}
