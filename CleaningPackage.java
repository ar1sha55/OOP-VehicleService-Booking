public enum CleaningPackage {
    BASIC("Basic Wash", "Exterior wash only", 20.00),
    DELUXE("Deluxe Wash", "Exterior + Interior vacuum", 35.00),
    PREMIUM("Premium Detail", "Full detailing, wax, tire shine", 70.00);

    private final String name;
    private final String description;
    private final double price;

    CleaningPackage(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }

    public static void showAllPackages() {
        System.out.println("Available Cleaning Packages:");
        int index = 1;
        for (CleaningPackage p : CleaningPackage.values()) {
            System.out.println(index++ + ". " + p.name + " - RM" + p.price);
            System.out.println("   " + p.description);
        }
    }

    public static CleaningPackage fromIndex(int index) {
        CleaningPackage[] values = CleaningPackage.values();
        if (index < 1 || index > values.length) {
            throw new IllegalArgumentException("Invalid package selection.");
        }
        return values[index - 1];
    }
}
