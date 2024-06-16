import java.util.Locale;

class MapAnalyzer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // Change locale
        String[] inputRoutes = FileIO.readFile(args[0], false, false); // Take input data from file
        FastestRoute alg = new FastestRoute(inputRoutes, false, args[1]);
        BarelyConnectedMap bcm = new BarelyConnectedMap(inputRoutes, args[1]);
        alg.findFastestRoute(); // Find fastest route
        bcm.createBarelyConnectedMap(); // Create barely connected map
        bcm.findFastestRoute(); // Find fastest route acording to barely conneted map
        Analysis.analysis(alg, bcm, args[1]); // Write analysis to output file
    }
}