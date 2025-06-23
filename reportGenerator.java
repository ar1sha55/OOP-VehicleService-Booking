import java.io.*;
//import java.time.LocalDate;

public class reportGenerator {
    private Vector <Vehicle> vehicles;
    private Vector <Booking> bookings;
    private Vector <Customer> customers; 

    public reportGenerator(){
        this.vehicles = new Vector<>(vehicles);
        this.bookings = new Vector<>(bookings);
        this.customers = new Vector<>(customers);
    }

    public void generate(){
        System.out.println("\n===== CUSTOMER VEHICLES REPORT =====");
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------+");
        
        System.out.printf("| %-3s| %-24s| %-14s| %-14s| %-14s| %-14s| %-14s| %-14s |\n",
                         "No", "Customer Name", "Vehicle Model", "Vehicle Type", "Vehicle Plate", "Vehicle Brand", "Vehicle Model", "Vehicle Color");
        System.out.println("+----+-------------------------+---------------+---------------+---------------+---------------+---------------+----------------+");
        
        try{
            int i=1;
            for (Customer v : customers) {
                System.out.printf("| %-3d %-25s %-15s %-15s %-15s %-15s %-15s %-15s |\n", 
                                    i, v.getName(), v.getVehicle().getModel(), v.getVehicle().getVehicleType(), v.getVehicle().getPlateNum(), v.getVehicle().getBrand(), v.getVehicle().getModel(), v.getVehicle().getColour());
                i++;
            }
            System.out.println("+----+-------------------------+---------------+---------------+---------------+---------------+---------------+----------------+\n");
        }
        catch (NullPointerException e) {
            System.out.println("Error: Missing data in customer or vehicle records.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
