public class Admin extends User 
{
    private static int totalAdmin = 3;
    //private ReportGenerator report = new ReportGenerator(); 

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
        bw.write("[" + x + "] " + "|" + name + "|" + email + "|" + password);
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
}
