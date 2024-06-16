import java.util.ArrayList;
import java.util.LinkedHashMap;

class Road {
    private final int distance; // Distance from starting point to end of this road
    private final int roadID; // Unique id for road
    private final Point input; // Input point of road
    private final Point output; // Output point of road
    private final int reverse; // -1 or 1 if input and output reversed anywhere of code 1 otherwise -1
    private final int addTime; // Founded time of road in map tiebraker for sort RoadArrays

    // Getters and setters
    public int getDistance() {
        return this.distance;
    }

    public int getRoadID() {
        return this.roadID;
    }

    public Point getInput() {
        return this.input;
    }

    public Point getOutput() {
        return this.output;
    }

    public int getReverse() {
        return this.reverse;
    }

    public int getAddTime() {
        return this.addTime;
    }

    /**
     * Const. for Road
     * 
     * @param input    start of road
     * @param output   end of road
     * @param distance distance between start point and output point
     * @param roadID   id of road
     * @param reverse  is road reversed (input -> output or output -> input) -> 1
     *                 reversed, -1 not reversed
     */
    public Road(Point input, Point output, int distance, int roadID, int reverse,int addTime) {
        this.distance = distance;
        this.roadID = roadID;
        this.input = input;
        this.output = output;
        this.reverse = reverse;
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return this.toString(0);
    }

    public String toString(int number) {
        if (this.reverse == -1)
            return this.getInput().getName() + "\t" + this.getOutput().getName() + "\t" + (this.getDistance() - number)
                    + "\t" + this.getRoadID();
        else
            return this.getOutput().getName() + "\t" + this.getInput().getName() + "\t" + (this.getDistance() - number)
                    + "\t" + this.getRoadID();
    }

    /**
     * Find neighbour of point and add them to a Fastest Route's roadArray
     * 
     * @param point     neighbors wanted spot
     * @param roads     input roads from input file
     * @param roadArray road array from Fastest Route
     * @param fra       Fastest Route which included point
     */
    public static void findConnetedRoads(Point point, String[] roads, RoadArray roadArray, FastestRoute fra) {
        ArrayList<String> tempRoadArray = new ArrayList<>();
        for (String roadLine : roads) {
            String[] splitedRoadLine = roadLine.split("\t");
            if (roadArray.isInclude(splitedRoadLine))
                continue;
            if (point.getName().equals(splitedRoadLine[0])) { // If end point is splitedRoadLine[0]
                tempRoadArray.add(roadLine + "\t-1");
            }
            if (point.getName().equals(splitedRoadLine[1])) { // If end point is splitedRoadLine[1]
                tempRoadArray.add(splitedRoadLine[1] + "\t" + splitedRoadLine[0] + "\t" + splitedRoadLine[2] + "\t"
                        + splitedRoadLine[3] + "\t1"); // Reverse splitedRoadLine[0] and splitedRoadLine[1]
            }
        }

        // Generate road and add them to Fastest Route's roadArray
        RoadArray ra = new RoadArray();
        for (String roadLine : tempRoadArray) {
            String[] splitedRoadLine = roadLine.split("\t");
            ra.add(new Road(point,
                    new Point(splitedRoadLine[1],
                            Integer.parseInt(splitedRoadLine[2]) + point.getTotalMinDistance()),
                    Integer.parseInt(splitedRoadLine[2]) + point.getTotalMinDistance(),
                    Integer.parseInt(splitedRoadLine[3]), Integer.parseInt(splitedRoadLine[4]),fra.getRoadArray().size()));
        }
        ra.sortArray();
        fra.getRoadArray().addAll(ra);
    }

    /**
     * Create all roads fro Barely Connected Map
     * 
     * @param roads Roads for creating
     * @param bcm   Barely Connected Map which has roads
     */
    public static void createAllRoads(String[] roads, BarelyConnectedMap bcm) {
        for (String roadLine : roads) {
            String[] splitedRoadLine = roadLine.split("\t");
            Point point1 = new Point(splitedRoadLine[0], 0); // Input point
            Point point2 = new Point(splitedRoadLine[1], 0); // Output point 
            Point tempPoint1 = Point.findPoint(bcm.getPointArray(), point1); 
            Point tempPoint2 = Point.findPoint(bcm.getPointArray(), point2);
            boolean[] pointStatus = { false, false }; // point1 == tempPoint1 and point2 == tempPoint2

            // Is point1 or point2 inclueded in point arrays
            if (tempPoint1 != null) {
                point1 = tempPoint1;
                pointStatus[0] = true;
            }
            if (tempPoint2 != null) {
                point2 = tempPoint2;
                pointStatus[1] = true;
            }
            if (!pointStatus[0]) {
                bcm.getPointArray().add(point1);
            }
            if (!pointStatus[1]) {
                bcm.getPointArray().add(point2);
            }
            Road tempRoad = new Road(point1, point2,
                    Integer.parseInt(splitedRoadLine[2]),
                    Integer.parseInt(splitedRoadLine[3]), -1,0); // Generate road

            point1.getNeighbourRoadArray().add(tempRoad);
            point2.getNeighbourRoadArray().add(tempRoad);
        }
    }

    /**
     * Checks whether the given point is on the map.
     * 
     * @param map   Fastest Route roadMap
     * @param point search point
     * @return true or false according to is point included
     */
    public static boolean isRoadMapIncludePoint(LinkedHashMap<Point, Road> map, Point point) {
        for (Point mapPoint : map.keySet()) {
            if (mapPoint.getName().equals(point.getName())) {
                return true;
            }
        }
        return false;
    }

}
