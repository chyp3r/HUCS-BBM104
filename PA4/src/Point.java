import java.util.ArrayList;

class Point {
    private final String name;
    private final int totalMinDistance; // Least distance to reach that point
    private final RoadArray neighbourRoadArray; // Neighbour roads of point

    // Getters and setters
    public String getName() {
        return this.name;
    }

    public int getTotalMinDistance() {
        return this.totalMinDistance;
    }

    public RoadArray getNeighbourRoadArray() {
        return this.neighbourRoadArray;
    }

    /**
     * Const. for point 
     * 
     * @param name     name of point
     * @param distance least distance to reach that point
     */
    public Point(String name, int distance) {
        this.name = name;
        this.totalMinDistance = distance;
        this.neighbourRoadArray = new RoadArray();
    }

    /**
     * Try to find point in a arraylist
     * 
     * @param pointArray array to search
     * @param point      search point
     * @return founded point or null
     */
    public static Point findPoint(ArrayList<Point> pointArray, Point point) {
        for (Point tempPoint : pointArray) {
            if (tempPoint.getName().equals(point.getName())) {
                return tempPoint;
            }
        }
        return null;
    }
}
