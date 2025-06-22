import java.io.*;

public abstract class User implements InterfaceUser 
{
  private String name;
  private String email;
  private String password;

  public User(String name, String email, String password) 
  {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public String getName() {return name;}
  public String getEmail() {return email;}
  public String getPassword() {return password;}

  public void setName(String name) {this.name = name;}
  public void setEmail(String email) {this.email = email;}
  public void setPassword(String password) {this.password = password;}

  public void displayProfile() 
  {
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);
  }

  public abstract void showRole();

  public void saveToFile(int x) 
  {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("usersCust.txt", true))) 
    {
      bw.write("[" + x + "] " + "|" + name + "|" + email + "|" + password);
      bw.newLine();
    } 
    catch (IOException e) 
    {
      System.out.println("Error writing to file.");
    }
  }
}
