import java.time.*; //for booking slots
import java.util.*;
import java.io.*;

abstract class Booking implements BookingInterface {
    protected int bookingID; //use protected so accessible within package and all subclasses
    protected Customer customer; 
    protected Vehicle vehicle;
    protected LocalDate bookingDate;
    protected LocalTime bookingTime;
    protected String status;

    protected static ArrayList<LocalTime> availableSlots = new ArrayList<>();   
    protected static ArrayList<Booking> bookingList = new ArrayList<>(); 
    
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
        this.status = new Status(); //by default
    }

    public abstract void printDetails();

    @Override
    public void confirmBooking() {this.status.confirmedStatus();}

    @Override
    public void cancelBooking() {
        this.status.cancelledStatus();
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

    public static void saveToFile() {
        try {
            PrintWriter mWriter = new PrintWriter(new FileWriter("maintenance_bookings.txt")); //FileWriter to append, prevent overwrite
            PrintWriter cWriter = new PrintWriter(new FileWriter("cleaning_bookings.txt")); //FileWriter to append, prevent overwrite
            PrintWriter iWriter = new PrintWriter(new FileWriter("inspection_bookings.txt")); //FileWriter to append, prevent overwrite

            for(Booking b : bookingList) {
                PrintWriter wr = null;

                if(b instanceof MaintenanceBooking) {
                    wr = mWriter;
                } else if (b instanceof CleaningBooking) {
                    wr = cWriter;
                } else if (b instanceof InspectionBooking) {
                    wr = iWriter;
                }

                if(wr != null) {
                    wr.println(b.getBookingID());
                    wr.println(b.customer.getName());
                    //wr.println(mb.vehicle.getPlateNumber()); tunggu vehicle class
                    wr.println(b.getBookingDate());
                    wr.println(b.getBookingTime());
                    wr.println(b.getStatus());

                    if (b instanceof MaintenanceBooking mb) {
                        wr.println(mb.getServiceType());
                        wr.println(mb.getOdometer());
                        for (String s : mb.getRecommendService()) {
                            wr.println(s);
                        }
                    }

                    if (b instanceof CleaningBooking cb) {
                        wr.println(cb.getSelectedPkg().getName());
                        wr.println(cb.getSelectedPkg().getDescription());
                        wr.println(cb.getSelectedPkg().getPrice());
                    }

                    wr.println(); //line between bookings
                }
            }

            mWriter.close();
            cWriter.close();
            iWriter.close();
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}


