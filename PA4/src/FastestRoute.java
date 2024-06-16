import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

class FastestRoute {
    private RoadArray roadArray; // Created routes
    private LinkedHashMap<Point, Road> roadMap; // Fastest Route (FRA) destinations
    private Point startPoint; // Start point of destination
    private Point endPoint; // End point of destination
    private String[] inputRoutes; // Input data
    private float finalDistance; // Minumum distance for fastest route
    private float material; // BCM Construction material
    private ArrayList<Road> outputData; // Formated output data
    private boolean isBarelyConnetedMapRoute; // True or false to set the sentence to be written to the output file.
    private String path; // Output path
    // Getters and setters

    public float getFinalDistance() {
        return this.finalDistance;
    }

    public RoadArray getRoadArray() {
        return this.roadArray;
    }

    public float getMaterial() {
        return this.material;
    }

    /**
     * Const. for Fastest Route Algorithm (FRA)
     * 
     * @param inputRoutes              input datas from input file
     * @param isBarelyConnetedMapRoute true or false to set the sentence to be
     *                                 written to the output file.
     * @param path                     output path
     */
    public FastestRoute(String[] inputRoutes, boolean isBarelyConnetedMapRoute, String path) {
        this.startPoint = new Point(inputRoutes[0].split("\t")[0], 0);
        this.endPoint = new Point(inputRoutes[0].split("\t")[1], 0);
        this.roadMap = new LinkedHashMap<Point, Road>();
        this.outputData = new ArrayList<Road>();
        this.roadArray = new RoadArray();
        this.inputRoutes = inputRoutes;
        this.finalDistance = 0;
        this.isBarelyConnetedMapRoute = isBarelyConnetedMapRoute;
        this.path = path;
    }

    /**
     * Default FRA start function. Called when trying to start FRA without argument
     */
    public void findFastestRoute() {
        findFastestRoute(this.startPoint); // Start algortihm with start point
        this.createOutput(this.roadMap, this.endPoint); // Add datas to output file
    }

    /**
     * Find fastest route from start point to end point
     * 
     * @param point Starting point of route
     */
    public void findFastestRoute(Point point) {
        Road.findConnetedRoads(point, Arrays.copyOfRange(this.inputRoutes, 1, this.inputRoutes.length), roadArray,
                this); // Create neighobur roads of point
        // Try to find fastest route
        roadArray.sortArray();
        for (int i = 0; i < roadArray.size(); i++) {
            point = roadArray.get(i).getOutput(); // Change point
            if (!Road.isRoadMapIncludePoint(roadMap, point)) { // Ignore included roads from roadMap
                roadMap.put(point, roadArray.get(i));
                break;
            }
        }

        if (point.getName().equals(endPoint.getName())) { // When come to end point
            this.endPoint = point;
            this.finalDistance = point.getTotalMinDistance();
            this.material = Analysis.foundMaterialDistance(inputRoutes);
        } else {
            findFastestRoute(point); // Continue to other road
        }
    }

    /**
     * Generate output acording to founded route from FRA
     * 
     * @param roadMap // Fastest Route (FRA) destinations
     * @param point   // Point from roads
     */
    public void createOutput(LinkedHashMap<Point, Road> roadMap, Point point) {
        if (roadMap.get(point) != null)
            outputData.add(roadMap.get(point));
        try {
            createOutput(roadMap, roadMap.get(point).getInput()); // Move neighbour point
        } catch (Exception e) { // When point is null (End of roadMap) start write data to output file
            if (!isBarelyConnetedMapRoute) // Normal FRA output
                FileIO.writeFile(this.path, String.format("Fastest Route from %s to %s (%d KM):", startPoint.getName(),
                        endPoint.getName(), endPoint.getTotalMinDistance()), true, true);
            else // BCM output
                FileIO.writeFile(this.path,
                        String.format("Fastest Route from %s to %s on Barely Connected Map (%d KM):",
                                startPoint.getName(), endPoint.getName(), endPoint.getTotalMinDistance()),
                        true, true);

            // Generate output
            Collections.reverse(outputData);
            int lastDistanceDifference = 0;
            for (Road r : outputData) {
                FileIO.writeFile(this.path, r.toString(lastDistanceDifference), true, true);
                lastDistanceDifference = r.getDistance();
            }
            outputData = new ArrayList<Road>(); // Reset output data
        }
    }
}
