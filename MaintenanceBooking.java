import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

class MaintenanceBooking extends Booking {
    private static int mID = 1000; //maintenance id starts from 1000
    private String serviceType; //maintenance type (replace tyres, windshield, oil etc.)
    private int odometer; //ke mileage...tah
    private ArrayList<String> recommendService;

    public MaintenanceBooking(Customer cust, Vehicle vehicle, LocalDate date, LocalTime time, String serviceType, int odometer) {
        super(mID++, cust, vehicle, date, time);
        this.serviceType = serviceType;
        this.odometer = odometer;
        this.recommendService = determineService(odometer);
    }

    public String getServiceType() {return serviceType;}
    public int getOdometer() {return odometer;}
    public ArrayList<String> getRecommendService() {return recommendService;}

    private ArrayList<String> determineService(int o) {
        ArrayList<String> services = new ArrayList<>();

        //store all maintenances in array
        if (o >= 5000) {
            services.add("Preventative Maintenance & Oil Change (every 3 months / 5,000km)");
        }
        if (o >= 10000) {
            services.add("Tire Rotation (every 6 months / 10,000km)");
        }
        if (o >= 20000) {
            services.add("Wheel Balancing, Brake Inspection, and Alignment Check (every 12 months / 20,000km)");
        }
        if (o >= 40000) {
            services.add("Cooling System Check, Engine Service, and Transmission Inspection (every 24 months / 40,000km)");
        }

        if (services.isEmpty()) {
            services.add("No scheduled maintenance required based on current odometer.");
        }

        return services;
    }

    @Override
    public void printDetails() {
        System.out.println("## MAINTENANCE BOOKING ##");
        System.out.println("Booking ID: " + mID);
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Vehicle Reg No: " + customer.getName());  //tunggu vehicle class siap      
        System.out.println("Service: " + serviceType);
        System.out.println("Current Odometer Reading: ");
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Status: " + status);  
        System.out.println("Based on your odometer reading, here is your recommended services:");
        for(int i = 0; i < recommendService.size(); i++) {
            System.out.println((i+1) + ". " + recommendService);
        }
    }
}
