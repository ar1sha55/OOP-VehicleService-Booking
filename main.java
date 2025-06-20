import java.util.Scanner;
import java.io.*;

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
            scanner.nextLine(); 

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
            System.out.println("\n=============== WELCOME TO VEHICLE SERVICE BOOKING SYSTEM ===============");
            System.out.println("\n-- CUSTOMER MENU --");
            System.out.println("[1] I'm a new user (Register)");
            System.out.println("[2] I already have an account (Login)");
            System.out.println("[3] Back to Main Menu");
            //System.out.pritnln("[4] Book Service"); uncomment bila dah siap service class
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) 
            {
                case 1:
                    
                    System.out.print("Enter ID: ");
                    String newId = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String newPassword = scanner.nextLine();

                    System.out.print("Enter Vehicle Type: ");
                    String vehicleType = scanner.nextLine;
                    System.out.print("Enter Vehicle's Plate No.: ");
                    String vehicleNo = scanner.nextLine();

                    
                    Vehicle newVehicle = new Vehicle(vehicleNo, vehicleType);
                    Customer newCustomer = new Customer(newId, newName, newEmail, newPassword, newVehicle);
                    newCustomer.register();
                    break;

                case 2:
                    
                    System.out.print("Enter Email: ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();

                    
                    Customer dummyCustomer = new Customer("C001", "Alice", "alice@example.com", "1234", null);

                    try 
                    {
                        dummyCustomer.login(loginEmail, loginPassword);
                        
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

    public static void AdminMenu(Scanner scanner) 
    {
        int choice;
        Admin admin = new Admin("A001", "Bob", "admin@example.com", "admin123");

        try 
        {
            System.out.print("\n-- ADMIN LOGIN --\nEnter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            admin.login(email, password);

            do 
            {
                System.out.println("\n-- ADMIN MENU --");
                System.out.println("[1] View Bookings");
                System.out.println("[2] View Customers");
                System.out.println("[3] Generate Report");
                System.out.println("[4] View Services Ordered");
                System.out.println("[5] Back to Main Menu");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        //bookings
                        break;

                    case 2:
                        System.out.println("======= CUSTOMERS =======");
                        break;

                    case 3:
                        //generate report
                        break;

                    case 4:
                        // service diorder currently
                        break;

                    case 5:
                        System.out.println("Returning to main menu...");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 5);

        } catch (InvalidLogin e) {
            System.out.println(e.getMessage());
        }
    }
}
