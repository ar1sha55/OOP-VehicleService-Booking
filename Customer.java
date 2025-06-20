public class Customer extends User 
{
    private Vehicle vehicle; // Aggregation

    public Customer(String id, String name, String email, String password, Vehicle vehicle) 
    {
        super(id, name, email, password); // calling superclass constructor
        this.vehicle = vehicle;
    }

    @Override
    public void register() 
    {
        saveToFile("Customer");
        System.out.println("Customer registered successfully.");
    }

    @Override
    public void login(String email, String password) throws InvalidLogin
    {
        if (!getEmail().equals(email) || !getPassword().equals(password)) 
        {
            throw new InvalidLogin("Login failed for customer.");
        }
        System.out.println("Customer logged in.");
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
            System.out.println("Vehicle: " + vehicle.getModel());
        } 
        else 
        {
            System.out.println("No vehicle registered.");
        }
    }
}
