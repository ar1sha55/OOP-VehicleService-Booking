public class Customer extends User 
{
    private static int totalCustomer=0;
    private Vehicle vehicle; 
    private Vector <Booking> bookings; 

    public Customer(String id, String name, String email, String password, Vehicle vehicle) 
    {
        super(id, name, email, password); 
        this.vehicle = vehicle;
        this.bookings = new Vector<>();
    }

    public Customer() 
    {
    super("", "", "", "");  
    this.vehicle = null;
    this.bookings = new Vector<>();
    }


    @Override
    public void register() 
    {   
        totalCustomer++;
        saveToFile(totalCustomer);
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
            String cleanLine = line.replaceAll("\\[\\d+\\]", "");

            String[] parts = cleanLine.split("\\|");
            if (parts.length >= 4) 
            {
                String fileId = parts[0];
                String fileName = parts[1];
                String fileEmail = parts[2];
                String filePassword = parts[3];

                if (fileEmail.equals(email) && filePassword.equals(password)) 
                {
                    this.setId(fileId);
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
            //System.out.println("Vehicle: " + vehicle.getModel());
        } 
        else 
        {
            System.out.println("No vehicle registered.");
        }
    }
}
