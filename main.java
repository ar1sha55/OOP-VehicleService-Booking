import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class main 
{
    public static void clearScreen() 
    {
    System.out.print("\033[H\033[2J");
    System.out.flush();
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
            System.out.print("Enter your choice: ");
            mainChoice = scanner.nextInt();
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
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
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
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            clearScreen();

            switch (choice) 
            {
                case 1:

                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter Phone No.: ");
                    String newPhoneNo = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Enter Vehicle Type: ");
                    String vehicleType = scanner.nextLine();
                    System.out.print("Enter Vehicle's Plate No.: ");
                    String vehicleNo = scanner.nextLine();

                    
                    Vehicle newVehicle = new Vehicle(vehicleNo, vehicleType);
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

            do 
            {
                System.out.println("\n========== ADMIN MENU ==========");
                System.out.println("[1] View Bookings");
                System.out.println("[2] View Customers");
                System.out.println("[3] Generate Report");
                System.out.println("[4] View Services Ordered");
                System.out.println("[5] Manage Catalog");
                System.out.println("[6] Back to Main Menu");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.println("\n======= ALL BOOKINGS =======");

                        System.out.println("\n--- MAINTENANCE BOOKINGS ---");
                        displayBookings("maintenance_bookings.txt");

                        System.out.println("\n--- CLEANING BOOKINGS ---");
                        displayBookings("cleaning_bookings.txt");

                        System.out.println("\n--- INSPECTION BOOKINGS ---");
                        displayBookings("inspection_bookings.txt");

                        break;
                    case 2:
                        viewAllCustomers();
                        break;

                    case 3:
                        //generate report
                        break;

                    case 4:
                        // service diorder currently
                        break;

                    case 5:
                        boolean running = true;

                        while (running) 
                        {
                        System.out.println("\n=== ADMIN PANEL ===");
                        System.out.println("1. Add Service to Catalog");
                        System.out.println("2. View Catalog");
                        System.out.println("3. Exit");
                        System.out.print("Enter choice: ");
                        int choice = scanner.nextInt();
                        scanner.nextLine(); 

                        switch (choice) 
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
                        
                    case 6:
                        System.out.println("Returning to main menu...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 6);

    }

    public static void customerDashboard(Scanner scanner, Customer customer) 
    {
        int choice;
        do 
        {
            System.out.println("\n\n============ Welcome, " + customer.getName() + "!! ============");
            System.out.println("[1] View Available Services");
            System.out.println("[2] Book a Service");
            System.out.println("[3] Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) 
            {
                case 1:
                //service catalog
                break;

            case 2:
                System.out.println("\nWhich service would you like to book?");
                System.out.println("[1] Maintenance Service");
                System.out.println("[2] Cleaning Service");
                System.out.println("[3] Inspection Service");
                System.out.print("Enter your choice: ");
                int serviceChoice = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter booking date (YYYY-MM-DD): ");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                if(!Booking.hasAvailableSlots()) {
                    System.out.println("Sorry! No available slots today :(");
                    break;
                }

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
                    System.out.println("\n-- MAINTENANCE SERVICE BOOKING --");
                    System.out.print("Enter maintenance type (e.g. Oil Change): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter current odometer reading: ");
                    int odo = scanner.nextInt();
                    scanner.nextLine();

                    MaintenanceBooking mb = new MaintenanceBooking(customer, custVehicle, date, time, type, odo);
                    mb.printDetails();
                    Booking.bookingList.add(mb);
                    Booking.saveToFile();
                    break;

                case 2: // Cleaning
                    System.out.println("\n-- CLEANING SERVICE BOOKING --");
                    CleaningPackage.showAllPackages();
                    
                    System.out.print("Enter package choice [1-" + CleaningPackage.values().length + "]: ");
                    int pkgChoice = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        CleaningPackage selectedPkg = CleaningPackage.fromIndex(pkgChoice);
                        CleaningBooking cb = new CleaningBooking(customer, custVehicle, date, time, selectedPkg);
                        cb.printDetails();
                        Booking.bookingList.add(cb);
                        Booking.saveToFile();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3: // Inspection
                    System.out.println("\n-- INSPECTION SERVICE BOOKING --");
                    InspectionBooking ib = new InspectionBooking(customer, custVehicle, date, time);
                    ib.printDetails();
                    Booking.bookingList.add(ib);
                    Booking.saveToFile();
                    break; 
                }

            case 3:
                clearScreen();
                System.out.println("Logging out...");
                break;

            default:
                System.out.println("Invalid choice. Please choose 1-3.");
        }
    } while (choice != 3);
    }

    public static void displayBookings(String filename) { //kena check lagi nanti
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No bookings found in " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int bookingNum = 1;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip blank lines

                System.out.println("Booking #" + bookingNum++);
                System.out.println("Booking ID : " + line);
                System.out.println("Customer   : " + reader.readLine());
                System.out.println("Vehicle No : " + reader.readLine());
                System.out.println("Date       : " + reader.readLine());
                System.out.println("Time       : " + reader.readLine());
                System.out.println("Status     : " + reader.readLine());
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }


}