import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BarelyConnectedMap {
    private RoadArray roadArray; // Created roads
    private ArrayList<Point> pointArray; // Created points
    private String[] inputRoutes; // Road datas from input file
    private RoadArray barelyConnectedMap; // Barely Conneted Map(BCM) roads
    private Set<Point> visitedPoints; // Visited point when checking points and roads
    private float finalDistance; // Minumum distance for fastest route
    private float material; // BCM Construction material
    private String path; // Output path

    // Getters and setters
    public ArrayList<Point> getPointArray() {
        return this.pointArray;
    }

    public float getFinalDistance() {
        return this.finalDistance;
    }

    public float getMaterial() {
        return this.material;
    }

    /**
     * Const. for Barely Conneted Map(BCM)
     * 
     * @param inputRoutes input datas from input file
     * @param path        output path
     */
    public BarelyConnectedMap(String[] inputRoutes, String path) {
        this.roadArray = new RoadArray();
        this.pointArray = new ArrayList<>();
        this.barelyConnectedMap = new RoadArray();
        this.visitedPoints = new HashSet<>();
        this.inputRoutes = inputRoutes;
        this.finalDistance = 0;
        this.material = 0;
        this.path = path;

        Road.createAllRoads(Arrays.copyOfRange(this.inputRoutes, 1, this.inputRoutes.length), this); // Create all roads
                                                                                                     // from input file
        pointArray.sort(Comparator.comparing(Point::getName)); // Sort all point acording to name order
    }

    /**
     * Default BCM generator. Called when trying to generate BCM without argument
     * given
     */
    public void createBarelyConnectedMap() {
        createBarelyConnectedMap(this.pointArray.get(0)); // Start algortihm with first element of pointArray
        FileIO.writeFile(this.path, "Roads of Barely Connected Map is:", true, true);
        this.barelyConnectedMap.sortArray();
        for (Road r : this.barelyConnectedMap) {
            FileIO.writeFile(this.path, r.toString(), true, true); // Add map to output file
        }
    }

    /**
     * Generate BCM with recursive algorithm
     * 
     * @param point Starting point of map
     */
    public void createBarelyConnectedMap(Point point) {
        visitedPoints.add(point);
        roadArray.addAll(point.getNeighbourRoadArray()); // Add all visited point's neighbours
        roadArray.sortArray(); // Sort roads acording to id (id for tiebraker)
        // Try found to lowest distance road
        for (int i = 0; i < roadArray.size(); i++) {
            Road road = roadArray.get(i); // Take road
            if (!barelyConnectedMap.contains(road)) { // Check is road in BCM
                if (!(visitedPoints.contains(road.getOutput()) && visitedPoints.contains(road.getInput()))) {
                    barelyConnectedMap.add(road); // Add to BCM
                    // Go to other point of road
                    if (visitedPoints.contains(road.getOutput())) {
                        createBarelyConnectedMap(road.getInput());
                    } else if (visitedPoints.contains(road.getInput())) {
                        createBarelyConnectedMap(road.getOutput());
                    }
                }
            }
        }

    }

    /**
     * Start Fastest Route Algorithm (FRA) for BCM
     */
    public void findFastestRoute() {
        this.barelyConnectedMap.sortArray();
        String[] formatedOutputData = this.createFastestRouteRoad(barelyConnectedMap, inputRoutes); // Generate array
                                                                                                    // for FRA
        FastestRoute fra = new FastestRoute(formatedOutputData, true, this.path);
        fra.findFastestRoute(); // Start FRA
        this.finalDistance = fra.getFinalDistance();
        this.material = Analysis.foundMaterialDistance(formatedOutputData);
    }

    /**
     * Generate a String array for FRA
     * 
     * @param roadArray   roads from BCM
     * @param inputRoutes input road data
     * @return a string array which include roads from BCM
     */
    public String[] createFastestRouteRoad(RoadArray roadArray, String[] inputRoutes) {
        ArrayList<String> tempRoadArray = new ArrayList<>(); // Create a temp arraylist for hold roads
        tempRoadArray.add(inputRoutes[0]); // Take start and end points
        for (int i = 1; i < inputRoutes.length; i++) {
            // Find roads from BCM
            for (Road road : roadArray) {
                if (road.toString().equals(inputRoutes[i])) {
                    tempRoadArray.add(inputRoutes[i]);
                    break;
                }
            }
        }
        // Turn array to list
        String[] listToArray = new String[tempRoadArray.size()];
        listToArray = tempRoadArray.toArray(listToArray);
        return listToArray;
    }
}
