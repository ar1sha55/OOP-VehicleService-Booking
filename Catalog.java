import java.util.*;

public class Catalog {
    private ArrayList <Service> servicesOffered;
    private ArrayList <Service> servicesOfferedMaintenance;
    private ArrayList <Service> servicesOfferedCleaning;
    private ArrayList <Service> servicesOfferedInspection;

    public Catalog(){
        servicesOffered = new ArrayList<>();
        servicesOfferedMaintenance = new ArrayList<>();
        servicesOfferedCleaning = new ArrayList<>();
        servicesOfferedInspection = new ArrayList<>();

        //Add general service offered by the service center
        servicesOffered.add(new Service(1, "Vehicle Maintenance", "Includes oil change, tire rotation, brake check, and engine tune-up to keep your car running smoothly."));
        servicesOffered.add(new Service(2, "Vehicle Cleaning", "Interior and exterior cleaning with options for wash, vacuum, wax, and full detailing."));
        servicesOffered.add(new Service(3, "Vehicle Inspection", "Covers safety and performance checks on brakes, tires, lights, and fluid levels."));

        //Maintenance
        Service m1 = new Service(1, "Preventative Maintenance & Oil Change", "Includes replacing engine oil and oil filter to ensure proper engine lubrication and prevent early engine wear.");
        m1.setSedanDetails(0, 0);
        m1.setSuvDetails(0, 0);
        m1.setMpvDetails(0, 0);

        Service m2 = new Service(2, "Tire Rotation", "Rotating the tires to promote even tread wear, extend tire lifespan, and improve driving safety.");
        m2.setSedanDetails(0, 0);
        m2.setSuvDetails(0, 0);
        m2.setMpvDetails(0, 0);
        
        Service m3 = new Service(3, "Wheel Balancing, Brake Inspection, and Alignment Check", "Helps reduce vibration, improve tire life, and ensure safe and responsive braking.");
        m3.setSedanDetails(0, 0);
        m3.setSuvDetails(0, 0);
        m3.setMpvDetails(0, 0);

        Service m4 = new Service(4, "Cooling System, Engine, and Transmission Check", "Full inspection of radiator, hoses, engine performance, and transmission function to prevent overheating and mechanical failure.");
        m4.setSedanDetails(0, 0);
        m4.setSuvDetails(0, 0);
        m4.setMpvDetails(0, 0);

        servicesOfferedMaintenance.add(m1);
        servicesOfferedMaintenance.add(m2);
        servicesOfferedMaintenance.add(m3);
        servicesOfferedMaintenance.add(m4);

        //Cleaning
    }
}