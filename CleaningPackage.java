public enum CleaningPackage {
    BASIC("Basic Wash", "Exterior wash only", 7.00, 12.00, 20.00, 15, 20, 25),
    DELUXE("Deluxe Wash", "Exterior + Interior vacuum", 15.00, 20.00, 30.00, 25, 30, 45),
    PREMIUM("Premium Detail", "Full detailing, wax, tire shine", 70.00, 100.00, 150.00, 60, 80, 100);

    private final String name;
    private final String description;
    private final double priceSedan, priceSuv, priceMpv;
    private final int durationSedan, durationSuv, durationMpv;

    CleaningPackage(String name, String description, double priceSedan, double priceSuv, double priceMpv, int durationSedan, int durationSuv, int durationMpv) {
        this.name = name;
        this.description = description;
        this.priceSedan = priceSedan;
        this.priceSuv = priceSuv;
        this.priceMpv = priceMpv;
        this.durationSedan = durationSedan;
        this.durationSuv = durationSuv;
        this.durationMpv = durationMpv;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    /*
    public double getPriceSedan() { return priceSedan; }
    public double getPriceSuv() { return priceSuv; }
    public double getPriceMpv() { return priceMpv; }
    public int getDurationSedan() { return durationSedan; }
    public int getDurationSuv() { return durationSuv; }
    public int getDurationMpv() { return durationMpv; }
    */

    //Based on vehicle type
    public double getPrice(VehicleType type) { 
        switch(type) {
            case SEDAN: return priceSedan;
            case SUV: return priceSuv;
            case MPV: return priceMpv;
            default: throw new IllegalArgumentException("Invalid type");
        }
    }
    public int getDuration(VehicleType type) { 
        switch(type) {
            case SEDAN: return durationSedan;
            case SUV: return durationSuv;
            case MPV: return durationMpv;
            default: throw new IllegalArgumentException("Invalid type");
        }
    }

    public static void showAllPackages() {
        System.out.println("Available Cleaning Packages:");
        int index = 1;
        for (CleaningPackage p : CleaningPackage.values()) {
            System.out.println(index++ + ". " + p.getName());
            System.out.println("   " + p.getDescription());
            System.out.println("   Prices:");
            System.out.println("     SEDAN - RM" + p.getPrice(VehicleType.SEDAN) + " (" + p.getDuration(VehicleType.SEDAN) + " mins)");
            System.out.println("     SUV   - RM" + p.getPrice(VehicleType.SUV)   + " (" + p.getDuration(VehicleType.SUV)   + " mins)");
            System.out.println("     MPV   - RM" + p.getPrice(VehicleType.MPV)   + " (" + p.getDuration(VehicleType.MPV)   + " mins)");
        }
    }
    public static void showSuggestedPackages(VehicleType type) {
        System.out.println("Suggested Cleaning Packages:");
        int index = 1;
        for (CleaningPackage p : CleaningPackage.values()) {
            System.out.println(index++ + ". " + p.getName());
            System.out.println("   " + p.getDescription());
            System.out.println("   Prices: RM" + p.getPrice(type) + " (" + p.getDuration(type) + " mins)");
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
