public class Admin extends User 
{
    private ReportGenerator reportGenerator = new ReportGenerator(); /

    public Admin(String id, String name, String email, String password) 
    {
        super(id, name, email, password);
    }

    @Override
    public void register() 
    {
        saveToFile("Admin");
        System.out.println("Admin registered successfully.");
    }

    @Override
    public void login(String email, String password) throws InvalidLogin 
    {
        if (!getEmail().equals(email) || !getPassword().equals(password)) 
        {
            throw new InvalidLogin("Login failed for admin.");
        }
        System.out.println("Admin logged in.");
    }

    @Override
    public void showRole() 
    {
        System.out.println("I am an Admin.");
    }

    public void generateSystemReport() 
    {
        reportGenerator.generate();
    }
}
