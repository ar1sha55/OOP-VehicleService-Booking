public class Service {
    private int id;
    private String name;
    private String description;
    private double sedanPrice, suvPrice, mpvPrice;
    private int sedanDuration, suvDuration, mpvDuration;

    public Service(int id, String n, String desc){
        this.id = id;
        name = n;
        description = desc;
    }

    // Setter for different category
    public void setSedanDetails(double price, int duration){
        sedanPrice = price;
        sedanDuration = duration;
    }

    public void setSuvDetails(double price, int duration){
        suvPrice = price;
        suvDuration = duration;
    }

    public void setMpvDetails(double price, int duration){
        mpvPrice = price;
        mpvDuration = duration;
    }

    //Display service info with different price and duration based on vehicle type
    public void displayInfo(VehicleType type){
        System.out.println("[" + id + "]" + name);
        System.out.println("    Description" + description);

        switch (type) {
            case SEDAN:
            System.out.printf("    Price: RM%.2f\n" + sedanPrice);
            System.out.println("    Duration: " + sedanDuration + "minutes");
            break;

            case SUV:
            System.out.printf("    Price: RM%.2f\n" + suvPrice);
            System.out.println("    Duration: " + suvDuration + "minutes");
            break;

        case MPV:
            System.out.printf("    Price: RM%.2f\n" + mpvPrice);
            System.out.println("    Duration: " + mpvDuration + "minutes");
            break;
        }

        System.out.println();
    }
}
