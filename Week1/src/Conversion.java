public class Conversion {
    public static void main(String[] args) {
        // making sure table headers are aligned properly
        System.out.printf("%-10s%-10s    %-10s%-10s%n",
                "Feet", "Meters", "Meters", "Feet");
        System.out.println("------------------------------------------");

        // calculation
        double meters = 20.0;

        for (int feet = 1; feet <= 10; feet++) {
            System.out.printf(
                    "%-10.1f%-10.3f    %-10.1f%-10.3f%n",
                    (double) feet,
                    footToMeter(feet),
                    meters,
                    meterToFoot(meters)
            );
            meters += 5;
        }
    }

    public static double footToMeter(double foot) {
        return 0.305 * foot;
    }

    public static double meterToFoot(double meter) {
        return meter / 0.305;
    }
}
