import java.util.*;

public class Catalog {
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
