public class Analysis {
    /**
     * Found Material Usage and Distance ratio between fra and bcm
     * 
     * @param fra Fastest Route Algorithm for analysis
     * @param bcm Barely Connetcted Map for analysis
     */
    public static void analysis(FastestRoute fra, BarelyConnectedMap bcm, String path) {
        FileIO.writeFile(path, "Analysis:", true, true);
        FileIO.writeFile(path,
                String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f",
                        bcm.getMaterial() / fra.getMaterial()),
                true, true);
        FileIO.writeFile(path, String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f",
                bcm.getFinalDistance() / fra.getFinalDistance()), true, false);
    }

    /**
     * Found total used material in maps
     * 
     * @param inputRoutes used route
     * @return total used material
     */
    public static float foundMaterialDistance(String[] inputRoutes) {
        float totalMaterial = 0;
        for (int i = 1; i < inputRoutes.length; i++) {
            totalMaterial += Float.parseFloat(inputRoutes[i].split("\t")[2]);
        }
        return totalMaterial;
    }
}
