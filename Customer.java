
import java.util.*;
import java.io.*;

public class Customer extends User
{
    private static int totalCustomer=0;
    private String phoneNo;
    private Vehicle vehicle;
    private Vector <Booking> bookings;

    public Customer(String name, String email, String password, String phoneNo, Vehicle vehicle)
    {
        super(name, email, phoneNo);
        this.phoneNo = phoneNo;
        this.vehicle = vehicle;
        this.bookings = new Vector<>();
    }

    public Customer()
    {
        super("","","");
        this.phoneNo = "";
        this.vehicle = null;
        this.bookings = new Vector<>();
    }
}