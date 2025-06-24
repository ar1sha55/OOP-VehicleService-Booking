
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
                VehicleType vType = VehicleType.valueOf(vehicleType.toUpperCase()); // ✅ convert string to enum
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