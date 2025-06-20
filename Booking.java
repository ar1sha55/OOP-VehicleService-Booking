import java.time.*; //for booking slots
import java.util.*;

abstract class Booking implements BookingInterface {
    protected int bookingID; //use protected so accessible within package and all subclasses
    protected Customer customer;
    protected Vehicle vehicle;
    protected LocalDate bookingDate;
    protected LocalTime bookingTime;
    protected String status;

    protected static ArrayList<LocalTime> availableSlots = new ArrayList<>();    
    
    static {
        for(int i = 9; i < 18; i++) { //kedai bukak from 0900 - 1700
            availableSlots.add(LocalTime.of(i, 0));
        }
    }

    public Booking(int bookingID, Customer customer, Vehicle vehicle, LocalDate bookingDate, LocalTime bookingTime) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.vehicle = vehicle;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.status = "Pending"; //by default
    }

    public abstract void printDetails();

    @Override
    public void confirmBooking() {this.status = "Confirmed";}

    @Override
    public void cancelBooking() {
        this.status = "Cancelled";
        availableSlots.add(bookingTime);
    }

    public int getBookingID() {return bookingID;}
    public LocalTime getBookingTime() {return bookingTime;}
    public LocalDate getBookingDate() {return bookingDate;}
    public String getStatus() {return status;}
    public static boolean hasAvailableSlots() {return !availableSlots.isEmpty();}

    public static void showAvailableSlots() {
        System.out.println("== AVAILABLE TIME SLOTS ==");
        for(int i = 0; i < availableSlots.size(); i++) {
            System.out.println("[" + availableSlots.get(i) + "]\t");
        }
    }

    public static LocalTime bookSlot(int i) {
        if (i < 0 || i >= availableSlots.size()) {
            throw new IllegalArgumentException("Invalid slot, try again.");
        }

        return availableSlots.remove(i);
    }

}


