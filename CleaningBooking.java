import java.time.LocalDate;
import java.time.LocalTime;

class CleaningBooking extends Booking {
    private static int cID = 2000;
    private CleaningPackage selectedPkg;

    public CleaningBooking(Customer customer, Vehicle vehicle, LocalDate date, LocalTime time, CleaningPackage selectedPkg) {
        super(cID++, customer, vehicle, date, time);
        this.selectedPkg = selectedPkg;
    }

    @Override
    public void printDetails() {
        System.out.println("== CLEANING BOOKING ==");
        System.out.println("Booking ID: " + cID);
        System.out.println("Customer Name: " + customer.getName());
        System.out.println("Vehicle Reg No: " + customer.getName()); //tunggu vehicle class siap
        System.out.println("Package Name: " + selectedPkg.getName());
        System.out.println("Package Description: " + selectedPkg.getDescription());
        System.out.println("Package Price: " + selectedPkg.getPrice());
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Booking Time: " + bookingTime);
        System.out.println("Status: " + status);   
    }

    public CleaningPackage getSelectedPkg() {return selectedPkg;}
}