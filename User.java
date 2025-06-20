import java.io.*;

public abstract class User implements InterfaceUser 
{
  private String id;
  private String name;
  private String email;
  private String password;

  public User(String id, String name, String email, String password) 
  {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public String getId() {return id;}
  public String getName() {return name;}
  public String getEmail() {return email;}
  public String getPassword() {return password;}

  public void setId(String id) {this.id = id;}
  public void setName(String name) {this.name = name;}
  public void setEmail(String email) {this.email = email;}
  public void setPassword(String password) {this.password = password;}

  public void displayProfile() 
  {
    System.out.println("ID: " + id);
    System.out.println("Name: " + name);
    System.out.println("Email: " + email);
  }

  public abstract void showRole();

  public void saveToFile(int x) 
  {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) 
    {
      bw.write("[" + x + "] " + id + "|" + name + "|" + email + "|" + password);
      bw.newLine();
    } 
    catch (IOException e) 
    {
      System.out.println("Error writing to file.");
    }
  }
}
