
import java.io.*;
import java.util.*; 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

class InvalidLogin extends Exception 
{ 
    public InvalidLogin(String message) 
    {
        super(message);
    }
}

interface InterfaceUser 
    {
        void register();
        void login(String email, String password) throws InvalidLogin;
    }


abstract class Booking implements BookingInterface {
    protected Customer customer; 
    protected Vehicle vehicle;
    protected LocalDate bookingDate;
    protected LocalTime bookingTime;
    protected Status status;

    protected static ArrayList<LocalTime> availableSlots = new ArrayList<>();   
    protected static ArrayList<Booking> bookingList = new ArrayList<>(); 
    
    static {
        for(int i = 9; i < 18; i++) { //kedai bukak from 0900 - 1700
            availableSlots.add(LocalTime.of(i, 0));
        }
    }

    public Booking(Customer customer, Vehicle vehicle, LocalDate bookingDate, LocalTime bookingTime) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.status = new Status(this); // pending by default
    }

    public abstract void printDetails();

    @Override
    public void confirmBooking() {this.status.confirmStatus();}

    @Override
    public void cancelBooking() {
        this.status.cancelStatus();
        availableSlots.add(bookingTime);
    }

    public LocalTime getBookingTime() {return bookingTime;}
    public LocalDate getBookingDate() {return bookingDate;}
    public Status getStatus() {return status;}
    public static boolean hasAvailableSlots() {return !availableSlots.isEmpty();}

    public static void showAvailableSlots() {
        System.out.println("== AVAILABLE TIME SLOTS ==");
        for(int i = 0; i < availableSlots.size(); i++) {
            System.out.println("[" + i + "] [" + availableSlots.get(i) + "]\t");
        }
    }

    public static LocalTime bookSlot(int i) {
        if (i < 0 || i >= availableSlots.size()) {
            throw new IllegalArgumentException("Invalid slot, try again.");
        }

        return availableSlots.remove(i);
    }

    public static void saveToFile(Booking b) {
        String filename;
        if (b instanceof MaintenanceBooking) {
            filename = "maintenance_bookings.txt";
        } else if (b instanceof CleaningBooking) {
            filename = "cleaning_bookings.txt";
        } else if (b instanceof InspectionBooking) {
            filename = "inspection_bookings.txt";
        } else {
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(b.customer.getName().trim());
            writer.println(b.vehicle.getPlateNum());
            writer.println(b.getBookingDate());
            writer.println(b.getBookingTime());
            writer.println(b.getStatus());

            if (b instanceof MaintenanceBooking mb) {
                for (String s : mb.getRecommendService()) {
                    writer.println(s);
                }
            }
            if (b instanceof CleaningBooking cb) {
                writer.println(cb.getSelectedPkg().getName());
                writer.println(cb.getSelectedPkg().getDescription());
                writer.println(cb.getSelectedPkg().getPrice(cb.vehicle.getVehicleType()));
            }

            writer.println();   
        } catch (IOException e) {
            System.out.println("Error writing booking: " + e.getMessage());
        }
    }


}

interface BookingInterface {
    void confirmBooking();
    void cancelBooking();
    void printDetails();
}

class CleaningBooking extends Booking {
    private CleaningPackage selectedPkg;

    public CleaningBooking(Customer customer, Vehicle vehicle, LocalDate date, LocalTime time, CleaningPackage selectedPkg) {
        super(customer, vehicle, date, time);
        this.selectedPkg = selectedPkg;
    }

    @Override
    public void printDetails() {
        System.out.println("\n== CLEANING BOOKING ==");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Vehicle Reg No: " + vehicle.getPlateNum()); 
        System.out.println("Package Name: " + selectedPkg.getName());
        System.out.println("Package Description: " + selectedPkg.getDescription());
        System.out.println("Package Price: " + selectedPkg.getPrice(vehicle.getVehicleType()));
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Status: " + getStatus().toString());   
    }

    public CleaningPackage getSelectedPkg() {return selectedPkg;}
}

enum CleaningPackage {
    BASIC("Basic Wash", "Exterior wash only", 7.00, 12.00, 20.00, 15, 20, 25),
    DELUXE("Deluxe Wash", "Exterior + Interior vacuum", 15.00, 20.00, 30.00, 25, 30, 45),
    PREMIUM("Premium Detail", "Full detailing, wax, tire shine", 70.00, 100.00, 150.00, 60, 80, 100);

    private final String name;
    private final String description;
    private final double priceSedan, priceSuv, priceMpv;
    private final int durationSedan, durationSuv, durationMpv;

    CleaningPackage(String name, String description, double priceSedan, double priceSuv, double priceMpv, int durationSedan, int durationSuv, int durationMpv) {
        this.name = name;
        this.description = description;
        this.priceSedan = priceSedan;
        this.priceSuv = priceSuv;
        this.priceMpv = priceMpv;
        this.durationSedan = durationSedan;
        this.durationSuv = durationSuv;
        this.durationMpv = durationMpv;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    /*
    public double getPriceSedan() { return priceSedan; }
    public double getPriceSuv() { return priceSuv; }
    public double getPriceMpv() { return priceMpv; }
    public int getDurationSedan() { return durationSedan; }
    public int getDurationSuv() { return durationSuv; }
    public int getDurationMpv() { return durationMpv; }
    */

    //Based on vehicle type
    public double getPrice(VehicleType type) { 
        switch(type) {
            case SEDAN: return priceSedan;
            case SUV: return priceSuv;
            case MPV: return priceMpv;
            default: throw new IllegalArgumentException("Invalid type");
        }
    }
    public int getDuration(VehicleType type) { 
        switch(type) {
            case SEDAN: return durationSedan;
            case SUV: return durationSuv;
            case MPV: return durationMpv;
            default: throw new IllegalArgumentException("Invalid type");
        }
    }

    public static void showAllPackages() {
        System.out.println("Available Cleaning Packages:");
        int index = 1;
        for (CleaningPackage p : CleaningPackage.values()) {
            System.out.println(index++ + ". " + p.getName());
            System.out.println("   " + p.getDescription());
            System.out.println("   Prices:");
            System.out.println("     SEDAN - RM" + p.getPrice(VehicleType.SEDAN) + " (" + p.getDuration(VehicleType.SEDAN) + " mins)");
            System.out.println("     SUV   - RM" + p.getPrice(VehicleType.SUV)   + " (" + p.getDuration(VehicleType.SUV)   + " mins)");
            System.out.println("     MPV   - RM" + p.getPrice(VehicleType.MPV)   + " (" + p.getDuration(VehicleType.MPV)   + " mins)");
        }
    }
    public static void showSuggestedPackages(VehicleType type) {
        System.out.println("Suggested Cleaning Packages:");
        int index = 1;
        for (CleaningPackage p : CleaningPackage.values()) {
            System.out.println(index++ + ". " + p.getName());
            System.out.println("   " + p.getDescription());
            System.out.println("   Prices: RM" + p.getPrice(type) + " (" + p.getDuration(type) + " mins)");
        }
    }

    public static CleaningPackage fromIndex(int index) {
        CleaningPackage[] values = CleaningPackage.values();
        if (index < 1 || index > values.length) {
            throw new IllegalArgumentException("Invalid package selection.");
        }
        return values[index - 1];
    }
}

class MaintenanceBooking extends Booking {
    private ArrayList<String> recommendService;

    public MaintenanceBooking(Customer cust, Vehicle vehicle, LocalDate date, LocalTime time) {
        super(cust, vehicle, date, time);
        this.recommendService = vehicle.serviceReminder(); 
    }

    public ArrayList<String> getRecommendService() { return recommendService; }

    @Override
    public void printDetails() {
        System.out.println("\n== MAINTENANCE BOOKING ==");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Vehicle Reg No: " + vehicle.getPlateNum()); 
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Status: " + getStatus().toString());  
        System.out.println("Based on your odometer reading, here are your recommended services:");

        for (int i = 0; i < recommendService.size(); i++) {
            System.out.println((i + 1) + ". " + recommendService.get(i));
        }
    }
}

class InspectionBooking extends Booking {
    
    public InspectionBooking(Customer customer, Vehicle vehicle, LocalDate date, LocalTime time) {
        super(customer, vehicle, date, time);
    }

    @Override
    public void printDetails() {
        System.out.println("\n== INSPECTION BOOKING ==");
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Vehicle Reg No: " + vehicle.getPlateNum()); 
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Status: " + getStatus().toString());   
    }
}

enum VehicleType {
    SEDAN, SUV, MPV
}

class 
Vehicle{
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
    }

    public Vehicle(VehicleType vType, String pN, int currentOdo){
        plateNum = pN.toUpperCase();
        vehicleType = vType;
        currentOdometer = currentOdo;
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



abstract class User implements InterfaceUser 
{
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) 
    {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}

    public void displayProfile() 
    {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }

    public abstract void showRole(); 
}


class Customer extends User 
{
    private static int totalCustomer=0;
    private String phoneNo;
    private Vehicle vehicle; 
    private Vector <Booking> bookings; 

    public Customer(String name, String email, String password, String phoneNo, Vehicle vehicle) 
    {
        super(name, email, password); 
        this.phoneNo = phoneNo;
        this.vehicle = vehicle;
        this.bookings = new Vector<>();
    }


    public Customer() 
    {
    super("", "", "");  
    this.phoneNo = "";
    this.vehicle = null;
    this.bookings = new Vector<>();
    }

    public void setPhoneNo(String phoneNo){this.phoneNo = phoneNo;}
    public String getPhoneNo(){return phoneNo;}


    @Override
    public void register() 
    {   
        totalCustomer++;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersCust.txt", true))) 
        {
        bw.write("[" + totalCustomer + "] " + getName() + "|" + getEmail() + "|" + getPassword() + "|" + phoneNo + "|" + vehicle.getPlateNum() + "|" + vehicle.getVehicleType() + "|" + vehicle.getCurrentOdometer());
        bw.newLine();
        } 
        catch (IOException e) 
        {
        System.out.println("Error writing to file.");
        }
    }

        
    @Override
    public void login(String email, String password) throws InvalidLogin 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("usersCust.txt"))) 
        {
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) 
        {
            String cleanLine = line.replaceAll("\\[\\d+\\]s*", "");

            String[] parts = cleanLine.split("\\|");
            if (parts.length >= 7) 
            {
                String fileName = parts[0];
                String fileEmail = parts[1];
                String filePassword = parts[2];
                String filePhoneNo = parts[3];
                String plateNum     = parts[4];
                String vehicleType  = parts[5];
                int odometer = Integer.parseInt(parts[6]);
                VehicleType vType = VehicleType.valueOf(vehicleType.toUpperCase()); //convert string to enum
                Vehicle vehicle = new Vehicle(vType, plateNum, odometer);

                if (fileEmail.equals(email) && filePassword.equals(password)) 
                {
                    this.setName(fileName);
                    this.setEmail(fileEmail);
                    this.setPassword(filePassword);
                    this.setPhoneNo(filePhoneNo);
                    this.setVehicle(vehicle);  
                    found = true;
                    break;
                }
            }
        }

        if (!found) 
        {
            throw new InvalidLogin("Login failed. Invalid email or password.");
        }

        System.out.println("Login successful. Welcome, " + getName() + "!");
        } 
        catch (IOException e) 
        {
        System.out.println("Error reading users.txt: " + e.getMessage());
        }
    }

    @Override
    public void showRole() 
    {
        System.out.println("I am a Customer.");
    }

    public void showVehicle() 
    {
        if (vehicle != null) 
        {
            System.out.println("Vehicle: " + vehicle.getVehicleType() + "(" + vehicle.getPlateNum() + ")");
        } 
        else 
        {
            System.out.println("No vehicle registered.");
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

class Report {
    private Vector <Booking> allBookings;
    private Vector <MaintenanceBooking> maintenance;
    private Vector <InspectionBooking> inspection;
    private Vector <CleaningBooking> cleaning;

    public Report(){
        this.allBookings = new Vector<Booking>();
    }

    public void setMaintenance(Vector<MaintenanceBooking> maintenance) {
        this.maintenance = maintenance;
    }

    public void setInspection(Vector <InspectionBooking> inspection) {
        this.inspection = inspection;
    }

    public void setCleaning(Vector <CleaningBooking> cleaning) {
        this.cleaning = cleaning;
    }

    public void refreshAllBookings() {
        allBookings.clear();
        allBookings.addAll(cleaning);
        allBookings.addAll(maintenance);
        allBookings.addAll(inspection);
    }

    public void generate(){
        System.out.println("\n===== VEHICLES BOOKING SERVICE REPORT =====");
        System.out.println("+----+-------------------------+---------------+----------------+---------------------+------------+");
        
        System.out.printf("| %-3s| %-24s| %-14s| %-14s | %-20s| %-10s |\n",
                         "No", "Customer Name", "Vehicle Plate", "Vehicle Type", "Service Type", "Status");
        System.out.println("+----+-------------------------+---------------+----------------+---------------------+------------+");
        
        try{
            int i=1;
            for (Booking b : allBookings) {
                String typeService;
                if (b instanceof MaintenanceBooking) {
                    typeService = "Maintenance";
                }else if (b instanceof InspectionBooking) {
                    typeService = "Inspection";
                } else {
                    typeService = "Cleaning";
                }
                System.out.printf("| %-3d| %-24s| %-14s| %-14s | %-20s| %-10s |\n", 
                                    i, b.customer.getName(), b.customer.getVehicle().getPlateNum(), b.vehicle.getVehicleType(), typeService, b.getStatus().toString());
                i++;
            }
            System.out.println("+----+-------------------------+---------------+----------------+---------------------+------------+");
            System.out.println("\nTotal of Maintenance Booking\t: " + maintenance.size());
            System.out.println("Total of Inspection Booking\t: " + inspection.size());
            System.out.println("Total of Cleaning Booking\t: " + cleaning.size() + "\n");
        }
        catch (NullPointerException e) {
            System.out.println("Error: Missing data in customer or vehicle records.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}

class Catalog {
    //private ArrayList <Service> servicesOffered;
    private ArrayList <Service> servicesOfferedMaintenance;
    private ArrayList <Service> servicesOfferedCleaning;
    private ArrayList <Service> servicesOfferedInspection;

    public Catalog(){
        servicesOfferedMaintenance = new ArrayList<>();
        servicesOfferedCleaning = new ArrayList<>();
        servicesOfferedInspection = new ArrayList<>();

        //Maintenance
        Service m1 = new Service(101, "Preventative Maintenance & Oil Change", "Includes replacing engine oil and oil filter to ensure proper engine lubrication and prevent early engine wear.");
        m1.setSedanDetails(120.00, 30);  // RM120, 30 mins
        m1.setSuvDetails(150.00, 40);    // RM150, 40 mins
        m1.setMpvDetails(180.00, 45);    // RM180, 45 mins

        Service m2 = new Service(102, "Tire Rotation",  "Rotating the tires to promote even tread wear, extend tire lifespan, and improve driving safety.");
        m2.setSedanDetails(50.00, 20);   // RM50, 20 mins
        m2.setSuvDetails(65.00, 25);     // RM65, 25 mins
        m2.setMpvDetails(80.00, 30);     // RM80, 30 mins

        Service m3 = new Service(103, "Wheel Balancing, Brake Inspection, and Alignment Check", "Helps reduce vibration, improve tire life, and ensure safe and responsive braking.");
        m3.setSedanDetails(90.00, 35);   // RM90, 35 mins
        m3.setSuvDetails(110.00, 40);    // RM110, 40 mins
        m3.setMpvDetails(130.00, 45);    // RM130, 45 mins

        Service m4 = new Service(104, "Cooling System, Engine, and Transmission Check", "Full inspection of radiator, hoses, engine performance, and transmission function to prevent overheating and mechanical failure.");
        m4.setSedanDetails(160.00, 45);  // RM160, 45 mins
        m4.setSuvDetails(190.00, 50);    // RM190, 50 mins
        m4.setMpvDetails(220.00, 60);    // RM220, 60 mins

        servicesOfferedMaintenance.add(m1);
        servicesOfferedMaintenance.add(m2);
        servicesOfferedMaintenance.add(m3);
        servicesOfferedMaintenance.add(m4);

        //Cleaning
        int id=200;
        for (CleaningPackage cp : CleaningPackage.values()){
            Service cleanService = new Service(id++, cp.getName(), cp.getDescription());
            cleanService.setSedanDetails(cp.getPrice(VehicleType.SEDAN), cp.getDuration(VehicleType.SEDAN));
            cleanService.setSuvDetails(cp.getPrice(VehicleType.SUV), cp.getDuration(VehicleType.SUV));
            cleanService.setMpvDetails(cp.getPrice(VehicleType.MPV), cp.getDuration(VehicleType.MPV));
            servicesOfferedCleaning.add(cleanService);
        }

        //Inspection
        Service i1 = new Service(301, "Standard Inspection", "Includes brake system check, lights, tire tread, fluid levels, and battery health.");
        i1.setSedanDetails(100, 45);
        i1.setSuvDetails(200, 60);
        i1.setMpvDetails(300, 80);
        servicesOfferedInspection.add(i1);
    }

    public void addMaintenanceService(Service service) 
    {
        servicesOfferedMaintenance.add(service);
    }

    public void addCleaningService(Service service) 
    {
        servicesOfferedCleaning.add(service);
    }

    public void addInspectionService(Service service) 
    {
        servicesOfferedInspection.add(service);
    }

        public void viewAllServices(VehicleType type) 
    {
        System.out.println("=== Maintenance Services ===");
            for (Service s : servicesOfferedMaintenance) 
            {
            s.displayInfo(type);
            }
        System.out.println("=== Cleaning Services ===");
            for (Service s : servicesOfferedCleaning) 
            {
            s.displayInfo(type);
            }
        System.out.println("=== Inspection Services ===");
            for (Service s : servicesOfferedInspection)    
            {
            s.displayInfo(type);
            }
    }


    public void viewAllServices() 
    {
        System.out.println("=== Maintenance Services ===");
            for (Service s : servicesOfferedMaintenance) 
            {
                s.displayInfo(VehicleType.SEDAN);
                s.displayInfo(VehicleType.SUV);
                s.displayInfo(VehicleType.MPV);
            }
        System.out.println("=== Cleaning Services ===");
            for (Service s : servicesOfferedCleaning) 
            {
                s.displayInfo(VehicleType.SEDAN);
                s.displayInfo(VehicleType.SUV);
                s.displayInfo(VehicleType.MPV);
            }
        System.out.println("=== Inspection Services ===");
            for (Service s : servicesOfferedInspection)    
            {
            s.displayInfo(VehicleType.SEDAN);
            s.displayInfo(VehicleType.SUV);
            s.displayInfo(VehicleType.MPV);
            }
    }
}


class Service {
    private int id;
    private String name;
    private String description;
    private double sedanPrice, suvPrice, mpvPrice;
    private int sedanDuration, suvDuration, mpvDuration;

    // Default constructor
    public Service() {
        this.id = 0;
        this.name = "";
        this.description = "";
        this.sedanPrice = 0;
        this.suvPrice = 0;
        this.mpvPrice = 0;
        this.sedanDuration = 0;
        this.suvDuration = 0;
        this.mpvDuration = 0;
    }
    
    public Service(int id, String n, String desc){
        this.id = id;
        name = n;
        description = desc;
    }

    // Setter for different category
    public void setSedanDetails(double price, int duration){
        sedanPrice = price;
        sedanDuration = duration;
    }

    public void setSuvDetails(double price, int duration){
        suvPrice = price;
        suvDuration = duration;
    }

    public void setMpvDetails(double price, int duration){
        mpvPrice = price;
        mpvDuration = duration;
    }

    //Display service info with different price and duration based on vehicle type
    public void displayInfo(VehicleType type){
        System.out.println("[" + id + "]" + name);
        System.out.println("    Description: " + description);

        switch (type) {
            case SEDAN:
            System.out.printf("    Price: RM%.2f\n", sedanPrice);
            System.out.println("    Duration: " + sedanDuration + " minutes");
            break;

            case SUV:
            System.out.printf("    Price: RM%.2f\n", suvPrice);
            System.out.println("    Duration: " + suvDuration + " minutes");
            break;

        case MPV:
            System.out.printf("    Price: RM%.2f\n", mpvPrice);
            System.out.println("    Duration: " + mpvDuration + " minutes");
            break;
        }

        System.out.println();
    }
}


class Admin extends User 
{
    private static int totalAdmin = 3;
    private Report report = new Report(); 
    //private Catalog catalog;


    public Admin(String name, String email, String password) 
    {
        super(name, email, password);
        //this.report = new Report();
        //this.catalog = new Catalog();
    }

    public Admin() 
    {
    super("", "", "");  
    }

    public void setReport(Report r)
    {
        this.report = r;
    }

    @Override
    public void register() 
    {
        totalAdmin++;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersAdmin.txt", true))) 
        {
        bw.write("[" + totalAdmin + "] " + "|" + getName() + "|" + getEmail() + "|" + getPassword());
        bw.newLine();
        } 
        catch (IOException e) 
        {
        System.out.println("Error writing to file.");
        }
    }

@Override
    public void login(String email, String password) throws InvalidLogin 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("usersAdmin.txt"))) 
        {
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) 
        {
            String[] parts = line.split("\\|");

            if (parts.length >= 3) 
            {
                String fileName = parts[0];
                String fileEmail = parts[1];
                String filePassword = parts[2];

                if (fileEmail.equals(email) && filePassword.equals(password)) 
                {
                    this.setName(fileName);
                    this.setEmail(fileEmail);
                    this.setPassword(filePassword);
                    found = true;
                    break;
                }
            }
        }

        if (!found) 
        {
            throw new InvalidLogin("Admin login failed: Invalid email or password.");
        }

        System.out.println("Admin logged in successfully. Welcome, " + getName() + "!");
        } 
        catch (IOException e) 
        {
        System.out.println("Error reading usersAdmin.txt: " + e.getMessage());
        }
    }


    @Override
    public void showRole() 
    {
        System.out.println("I am an Admin.");
    }

    public void loadBookings(Vector<MaintenanceBooking> m, Vector<InspectionBooking> i, Vector<CleaningBooking> c) {
        report.setMaintenance(m);
        report.setInspection(i);
        report.setCleaning(c);
        report.refreshAllBookings();
    }

    public void generateSystemReport() 
    {
        report.generate();
    }

    public void addServiceToCatalog(Catalog catalog) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter service name:");
        String name = sc.nextLine();
    
        System.out.println("Enter service description:");
        String description = sc.nextLine();

        System.out.println("Enter service ID:");
        int id = Integer.parseInt(sc.nextLine());

        Service newService = new Service(id, name, description);

        System.out.println("Enter Sedan price and duration:");
        newService.setSedanDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine(); 

        System.out.println("Enter SUV price and duration:");
        newService.setSuvDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine();

        System.out.println("Enter MPV price and duration:");
        newService.setMpvDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine();

        System.out.println("Choose category to add:\n1. Maintenance\n2. Cleaning\n3. Inspection\n4. Cancel");
        int choice = sc.nextInt();

        switch (choice) 
        {
            case 1:
                catalog.addMaintenanceService(newService);
                System.out.println("Service added to Maintenance catalog.");
                break;
            case 2:
                catalog.addCleaningService(newService);
                System.out.println("Service added to Cleaning catalog.");
                break;
            case 3:
                catalog.addInspectionService(newService);
                System.out.println("Service added to Inspection catalog.");
                break;
            case 4:
                System.out.println("Service discarded.");
                break;
            default:
                System.out.println("Invalid choice. Discarding service.");
        }
    }

}

enum bookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

class Status {
    private bookingStatus currentStatus;
    private Booking booking;
    //constructor, set new booking into pending
    public Status(Booking booking){
        this.currentStatus = bookingStatus.PENDING;
        this.booking = booking;
    }
    //set status to confirmed
    public void confirmStatus(){
        this.currentStatus = bookingStatus.CONFIRMED;
    }
    //set status to cancelled
    public void cancelStatus(){
        this.currentStatus = bookingStatus.CANCELLED;
        Booking.availableSlots.remove(booking.bookingTime);
    }

    public bookingStatus getStatus(){
        return currentStatus;
    }

    public String toString(){
        return currentStatus.toString().substring(0,1) + currentStatus.toString().substring(1).toLowerCase();
    }
}


public class projekOOP
{
    public static void clearScreen() 
    {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }

    public static int getValidatedInput(Scanner scanner, int min, int max){
        int choice = -1;
        boolean validInput = false;
        while (!validInput){
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                if (choice<min || choice>max) {
                    System.out.println("Invalid choice. Please choose " + min + " - " + max + " only!");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("====================================================");
                System.out.println("Invalid input. Please choose " + min + " - " + max + " only!");
                scanner.nextLine();
            }    
        }
        return choice;
    }

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        int mainChoice;        
                 
        do 
        {
            System.out.println("========== VEHICLE SERVICE BOOKING SYSTEM ==========");
            System.out.println("[1] Customer");
            System.out.println("[2] Admin");
            System.out.println("[3] Exit");
            
            mainChoice = getValidatedInput(scanner, 1, 3);
            System.out.println("====================================================");
            scanner.nextLine(); 
            clearScreen();

            switch (mainChoice) 
            {
                case 1: 
                    CustomerMenu(scanner);
                    clearScreen();
                    break;

                case 2: 
                    AdminMenu(scanner);
                    clearScreen();
                    break;

                case 3:
                    System.out.println("Thank you. Bye!");
                    break;

                default: 
                    System.out.println("Invalid choice. Please choose 1-3 only.");
            }

        } while (mainChoice != 3);
    }

    public static void CustomerMenu(Scanner scanner) 
    {
        int choice;
        do 
        {
            System.out.println("\n=============== WELCOME TO VEHICLE SERVICE BOOKING SYSTEM!! ===============");
            System.out.println("[1] I'm a new user (Register)");
            System.out.println("[2] I already have an account (Login)");
            System.out.println("[3] Return");
            
            choice = getValidatedInput(scanner, 1, 3);
            scanner.nextLine(); 
            clearScreen();

            switch (choice) 
            {
                case 1:

                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter Phone No.: ");
                    String newPhoneNo = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Enter Vehicle Type: ");
                    String vehicleType = scanner.nextLine().toUpperCase();
                    System.out.print("Enter Vehicle's Plate No.: ");
                    String vehicleNo = scanner.nextLine();
                    VehicleType v = VehicleType.valueOf(vehicleType);
                    System.out.print("Enter Current Odometer: ");
                    int odo = scanner.nextInt();
                    
                    Vehicle newVehicle = new Vehicle(v, vehicleNo, odo);
                    Customer newCustomer = new Customer(newName, newEmail, newPassword, newPhoneNo, newVehicle);
                    newCustomer.register();
                    clearScreen();
                    System.out.println("\nYou registered successfully. Please login again. :)\n");
                    break;

                case 2:
                    
                    System.out.print("Enter Email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();
                    clearScreen();

                    Customer c = new Customer();

                    try 
                    {
                        c.login(loginEmail, loginPassword);
                        customerDashboard(scanner, c);
                    } 
                    catch (InvalidLogin e) 
                    {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);
    }

    public static void viewAllCustomers() 
    {
        File file = new File("usersCust.txt");

        if (!file.exists()) 
        {
            System.out.println("No customers found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            int count = 1;

            System.out.println("======== REGISTERED CUSTOMERS ========");

            while ((line = reader.readLine()) != null) 
            {
                line = line.replaceAll("\\[\\d+\\]\\s*\\|?", "");
                String[] parts = line.split("\\|");

                if (parts.length >= 6) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2]; 
                    String phone = parts[3];
                    String plate = parts[4];
                    String type = parts[5];

                    System.out.println("Customer #" + count++);
                    System.out.println("  Name     : " + name);
                    System.out.println("  Email    : " + email);
                    System.out.println("  Phone No : " + phone);
                    System.out.println("  Vehicle  : " + type + " (Plate: " + plate + ")");
                    System.out.println("--------------------------------------");
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error reading customers: " + e.getMessage());
        }
    }

    public static void AdminMenu(Scanner scanner) 
    {
        int choice;
        Catalog catalog = new Catalog();

        System.out.print("\n========== ADMIN LOGIN ========== --\nEnter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        Admin admin = new Admin();

        try
        {
            admin.login(email, password);
        }
        catch (InvalidLogin e)
        {
            System.out.println(e.getMessage());
        }

        Vector<MaintenanceBooking> maintenanceBookings = new Vector<>();
        Vector<InspectionBooking> inspectionBookings = new Vector<>();
        Vector<CleaningBooking> cleaningBookings = new Vector<>();

        for (Booking b : Booking.bookingList) {
            if (b instanceof MaintenanceBooking mb) {
                maintenanceBookings.add(mb);
            } else if (b instanceof InspectionBooking ib) {
                inspectionBookings.add(ib);
            } else if (b instanceof CleaningBooking cb) {
                cleaningBookings.add(cb);
            }
        }

        do 
        {
            System.out.println("\n========== ADMIN MENU ==========");
            //System.out.println("[1] View Bookings"); 
            System.out.println("[1] Manage Bookings"); 
            System.out.println("[2] View Customers");
            System.out.println("[3] Generate Report");
            System.out.println("[4] Manage Catalog");
            System.out.println("[5] Back to Main Menu");
            
            choice = getValidatedInput(scanner, 1, 5);
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    if (Booking.bookingList.isEmpty()) {
                        System.out.println("No bookings available.");
                        break;
                    }

                    System.out.println("\n======= MANAGE BOOKINGS =======");
                    for (int i = 0; i < Booking.bookingList.size(); i++) {
                        System.out.println("[" + i + "]");
                        Booking.bookingList.get(i).printDetails();
                        System.out.println("----------------------------------");
                    }

                    System.out.print("Enter the index of the booking to manage: ");
                    int index;
                    try {
                        index = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        scanner.nextLine();
                        break;
                    }

                    if (index < 0 || index >= Booking.bookingList.size()) {
                        System.out.println("Invalid booking index.");
                        break;
                    }

                    Booking selected = Booking.bookingList.get(index);
                    System.out.println("\nSelected Booking:");
                    selected.printDetails();

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("[1] Confirm Booking");
                    System.out.println("[2] Cancel Booking");
                    System.out.println("[3] Do Nothing");
                    
                    int subChoice = getValidatedInput(scanner, 1, 3);
                    scanner.nextLine();

                    switch (subChoice) {
                        case 1:
                            selected.status.confirmStatus();
                            System.out.println("Booking confirmed.");
                            break;
                        case 2:
                            selected.status.cancelStatus();
                            System.out.println("Booking cancelled.");
                            break;
                        case 3:
                            System.out.println("No action taken.");
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }

                    break;
                case 2:
                    viewAllCustomers();
                    break;

                case 3:
                    //generate report
                    admin.loadBookings(maintenanceBookings, inspectionBookings, cleaningBookings);
                    admin.generateSystemReport();
                    break;

                case 4:
                    boolean running = true;

                    while (running) 
                    {
                    System.out.println("\n=== CATALOG PANEL ===");
                    System.out.println("1. Add Service to Catalog");
                    System.out.println("2. View Catalog");
                    System.out.println("3. Exit");
                    
                    int cho = getValidatedInput(scanner, 1, 3);
                    scanner.nextLine(); 

                    switch (cho) 
                    {
                    case 1:
                        admin.addServiceToCatalog(catalog);
                        break;
                    case 2:
                        catalog.viewAllServices();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                    }
                    }
                    System.out.println("Exiting admin panel.");

                case 5:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

    }

    public static void customerDashboard(Scanner scanner, Customer customer) 
    {
        Catalog catalog = new Catalog();
        int choice;
        do 
        {
            System.out.println("\n\n============ Welcome, " + customer.getName() + "!! ============");
            System.out.println("[1] View Available Services");
            System.out.println("[2] Book a Service");
            System.out.println("[3] Manage Vehicle");
            System.out.println("[4] View Booking Status");
            System.out.println("[5] Logout");
            
            choice = getValidatedInput(scanner, 1, 5);
            scanner.nextLine(); 

            switch (choice) 
            {
                case 1:
                catalog.viewAllServices(customer.getVehicle().getVehicleType());
                break;

            case 2:
                System.out.println("\nWhich service would you like to book?");
                System.out.println("[1] Maintenance Service");
                System.out.println("[2] Cleaning Service");
                System.out.println("[3] Inspection Service");
                
                int serviceChoice = getValidatedInput(scanner, 1, 3);
                scanner.nextLine();

                LocalDate date = null;
                while (true) {
                    try {
                        System.out.print("Enter booking date (YYYY-MM-DD): ");
                        String input = scanner.nextLine();
                        date = LocalDate.parse(input);
                        break; 
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
                    }
                }

                if(!Booking.hasAvailableSlots()) { 
                    System.out.println("Sorry! No available slots today :(");
                    break;
                }
                //no inputexception
                Booking.showAvailableSlots();
                System.out.print("Choose a time slot (enter index): ");
                int slotChoice = scanner.nextInt();
                scanner.nextLine();

                LocalTime time;

                try {
                    time = Booking.bookSlot(slotChoice);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    break;
                }

                Vehicle custVehicle = customer.getVehicle();

                switch (serviceChoice) {
                case 1: // Maintenance
                    MaintenanceBooking mb = new MaintenanceBooking(customer, custVehicle, date, time);
                    mb.printDetails();
                    Booking.bookingList.add(mb);
                    Booking.saveToFile(mb);
                    break;

                case 2: // Cleaning
                    CleaningPackage.showAllPackages();
                    
                    //System.out.print("Enter package choice [1-" + CleaningPackage.values().length + "]: ");
                    int pkgChoice = getValidatedInput(scanner, 1, CleaningPackage.values().length);
                    scanner.nextLine();

                    try {
                        CleaningPackage selectedPkg = CleaningPackage.fromIndex(pkgChoice);
                        CleaningBooking cb = new CleaningBooking(customer, custVehicle, date, time, selectedPkg);
                        cb.printDetails();
                        Booking.bookingList.add(cb);
                        Booking.saveToFile(cb);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3: // Inspection
                    InspectionBooking ib = new InspectionBooking(customer, custVehicle, date, time);
                    ib.printDetails();
                    Booking.bookingList.add(ib);
                    Booking.saveToFile(ib);
                    break; 
                }

                break;

            case 3:
                manageVehicle(scanner, customer);
                break;

            case 4:
            System.out.println("\n========== YOUR BOOKINGS ==========");
            boolean found = false;

            for (Booking b : Booking.bookingList) {
                if (b.customer.getName().equalsIgnoreCase(customer.getName()) &&
                    b.vehicle.getPlateNum().equalsIgnoreCase(customer.getVehicle().getPlateNum())) {

                    found = true;
                    System.out.println("\n==============================");
                    System.out.println("Date       : " + b.getBookingDate());
                    System.out.println("Time       : " + b.getBookingTime());

                    if (b instanceof MaintenanceBooking) {
                        System.out.println("Service    : Maintenance");
                    } else if (b instanceof CleaningBooking cb) {
                        System.out.println("Service    : Cleaning");
                        System.out.println("Package    : " + cb.getSelectedPkg().getName());
                        System.out.println("Price      : RM" + cb.getSelectedPkg().getPrice(cb.vehicle.getVehicleType()));
                    } else if (b instanceof InspectionBooking) {
                        System.out.println("Service    : Inspection");
                    }

                    System.out.println("\nStatus     : " + b.getStatus());
                }
            }

            if (!found) {
                System.out.println("No bookings found under your name.");
            }

            break;


            case 5:
                clearScreen();
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice. Please choose 1-3.");
        }
    } while (choice != 5);
    }

    public static void manageVehicle(Scanner scanner, Customer customer) {
        int choice;
        do {
            System.out.println("\n========== VEHICLE MANAGEMENT ==========");
            System.out.println("[1] View Vehicle Information");
            System.out.println("[2] Update Vehicle Information");
            System.out.println("[3] Vehicle Service Reminder");
            System.out.println("[4] Back to Dashboard");
            
            choice = getValidatedInput(scanner, 1, 4);
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    viewVehicleInfo(customer);
                    break;
                case 2:
                    updateVehicleInfo(scanner, customer);
                    break;
                case 3:
                    viewServiceReminder(customer);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    
    private static void viewVehicleInfo(Customer customer) {
        System.out.println("\n========== VEHICLE INFORMATION ==========");
        customer.getVehicle().displayInfo();
    }
    
    public static void updateVehicleInfo(Scanner scanner, Customer customer) {
        try {
            Vehicle vehicle = customer.getVehicle();
            System.out.println("\n========== UPDATE VEHICLE INFORMATION ==========");
            
            System.out.println("\nCurrent Vehicle Information:");
            vehicle.displayInfo();
            
            System.out.println("\nEnter new details (leave blank to keep current value):");
            
            System.out.print("Vehicle Type (" + vehicle.getVehicleType() + "): ");
            String typeInput = scanner.nextLine();
            if (!typeInput.isEmpty()) {
                vehicle.setVehicleType(VehicleType.valueOf(typeInput.toUpperCase()));
            }
            
            System.out.print("Plate Number (" + vehicle.getPlateNum() + "): ");
            String plateInput = scanner.nextLine();
            if (!plateInput.isEmpty()) {
                vehicle.setPlateNum(plateInput);
            }
            
            System.out.print("Brand (" + vehicle.getBrand() + "): ");
            String brandInput = scanner.nextLine();
            if (!brandInput.isEmpty()) {
                vehicle.setBrand(brandInput);
            }
            
            System.out.print("Model (" + vehicle.getModel() + "): ");
            String modelInput = scanner.nextLine();
            if (!modelInput.isEmpty()) {
                vehicle.setModel(modelInput);
            }
            
            System.out.print("Color (" + vehicle.getColour() + "): ");
            String colorInput = scanner.nextLine();
            if (!colorInput.isEmpty()) {
                vehicle.setColour(colorInput);
            }
            
            System.out.print("Current Odometer (" + vehicle.getCurrentOdometer() + "): ");
            String odoInput = scanner.nextLine();
            if (!odoInput.isEmpty()) {
                vehicle.setCurrentOdometer(Integer.parseInt(odoInput));
            }
            
            // Update last service odometer readings
            System.out.println("\nUpdate Last Service Odometer Readings:");
            System.out.print("Preventative Maintenance (" + vehicle.getlastServiceOdometerType1() + "): ");
            String service1Input = scanner.nextLine();
            if (!service1Input.isEmpty()) {
                vehicle.setlastServiceOdometerType1(Integer.parseInt(service1Input));
            }
            
            System.out.print("Tire Rotation (" + vehicle.getlastServiceOdometerType2() + "): ");
            String service2Input = scanner.nextLine();
            if (!service2Input.isEmpty()) {
                vehicle.setlastServiceOdometerType2(Integer.parseInt(service2Input));
            }
            
            System.out.print("Wheel Balancing (" + vehicle.getlastServiceOdometerType3() + "): ");
            String service3Input = scanner.nextLine();
            if (!service3Input.isEmpty()) {
                vehicle.setlastServiceOdometerType3(Integer.parseInt(service3Input));
            }
            
            System.out.print("Cooling System Check (" + vehicle.getlastServiceOdometerType4() + "): ");
            String service4Input = scanner.nextLine();
            if (!service4Input.isEmpty()) {
                vehicle.setlastServiceOdometerType4(Integer.parseInt(service4Input));
            }
            
            System.out.println("\nVehicle information updated successfully!");
        } catch (Exception e) {
            System.out.println("Error occured! Please entered information in correct format! \nError: "+e.getMessage());
        }
    }
    
    public static void viewServiceReminder(Customer customer){
        customer.getVehicle().reminderDisplay();
    }

    public static void displayMaintenanceBookings(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No bookings found in " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                System.out.println("========== Booking #" + count++ + " ==========");
                System.out.println("Customer   : " + reader.readLine());
                System.out.println("Vehicle No : " + reader.readLine());
                System.out.println("Date       : " + reader.readLine());
                System.out.println("Time       : " + reader.readLine());
                System.out.println("Status     : " + reader.readLine());
                System.out.println("Recommended Services:");

                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    System.out.println(" - " + line);
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }

    public static void displayCleaningBookings(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No bookings found in " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                System.out.println("========== Booking #" + count++ + " ==========");
                System.out.println("Customer   : " + reader.readLine());
                System.out.println("Vehicle No : " + reader.readLine());
                System.out.println("Date       : " + reader.readLine());
                System.out.println("Time       : " + reader.readLine());
                System.out.println("Status     : " + reader.readLine());
                System.out.println("Package    : " + reader.readLine());
                System.out.println("Description: " + reader.readLine());  
                System.out.println("Price      : RM" + reader.readLine());

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }

    public static void displayInspectionBookings(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No bookings found in " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int count = 1;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                System.out.println("========== Booking #" + count++ + " ==========");
                System.out.println("Customer   : " + reader.readLine());
                System.out.println("Vehicle No : " + reader.readLine());
                System.out.println("Date       : " + reader.readLine());
                System.out.println("Time       : " + reader.readLine());
                System.out.println("Status     : " + reader.readLine());

                reader.readLine();
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }
}
