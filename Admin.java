import java.io.*;
import java.util.*;

public class Admin extends User
{
    private static int totalAdmin = 3;
    private ReportGenerator report;
    private Catalog catalog;

    public Admin(String name, String email, String password)
    {
        super(name, email, password);
    }

    public Admin()
    {
        super("","","");
    }
}