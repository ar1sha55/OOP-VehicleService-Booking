public class Admin extends User 
{
    private static int totalAdmin = 3;
    private ReportGenerator report = new ReportGenerator(); 
    private Catalog catalog = new Catalog();

    public Admin(String name, String email, String password) 
    {
        super(name, email, password);
    }

    public Admin() 
    {
    super("", "", "");  
    }

    @Override
    public void register() 
    {
        totalAdmin++;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersAdmin.txt", true))) 
        {
        bw.write("[" + totalAdmin + "] " + "|" + getName() + "|" + getEmail() + "|" + getPassword());
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
        try (BufferedReader reader = new BufferedReader(new FileReader("usersAdmin.txt"))) 
        {
        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) 
        {
            String[] parts = line.split("\\|");

            if (parts.length >= 3) 
            {
                String fileName = parts[0];
                String fileEmail = parts[1];
                String filePassword = parts[2];

                if (fileEmail.equals(email) && filePassword.equals(password)) 
                {
                    this.setName(fileName);
                    this.setEmail(fileEmail);
                    this.setPassword(filePassword);
                    found = true;
                    break;
                }
            }
        }

        if (!found) 
        {
            throw new InvalidLogin("Admin login failed: Invalid email or password.");
        }

        System.out.println("Admin logged in successfully. Welcome, " + getName() + "!");
        } 
        catch (IOException e) 
        {
        System.out.println("Error reading usersAdmin.txt: " + e.getMessage());
        }
    }


    @Override
    public void showRole() 
    {
        System.out.println("I am an Admin.");
    }

    public void generateSystemReport() 
    {
        report.generate();
    }

    public void addServiceToCatalog(Catalog catalog) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter service name:");
        String name = sc.nextLine();
    
        System.out.println("Enter service description:");
        String description = sc.nextLine();

        System.out.println("Enter service ID:");
        int id = Integer.parseInt(sc.nextLine());

        Service newService = new Service(id, name, description);

        System.out.println("Enter Sedan price and duration:");
        newService.setSedanDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine(); 

        System.out.println("Enter SUV price and duration:");
        newService.setSuvDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine();

        System.out.println("Enter MPV price and duration:");
        newService.setMpvDetails(sc.nextDouble(), sc.nextInt());
        sc.nextLine();

        System.out.println("Choose category to add:\n1. General\n2. Maintenance\n3. Cleaning\n4. Inspection\n5. Cancel");
        int choice = sc.nextInt();

        switch (choice) 
        {
            case 1:
                catalog.addGeneralService(newService);
                System.out.println("Service added to General catalog.");
                break;
            case 2:
                catalog.addMaintenanceService(newService);
                System.out.println("Service added to Maintenance catalog.");
                break;
            case 3:
                catalog.addCleaningService(newService);
                System.out.println("Service added to Cleaning catalog.");
                break;
            case 4:
                catalog.addInspectionService(newService);
                System.out.println("Service added to Inspection catalog.");
                break;
            case 5:
                System.out.println("Service discarded.");
                break;
            default:
                System.out.println("Invalid choice. Discarding service.");
        }
    }

}