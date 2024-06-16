import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class RoadArray extends ArrayList<Road> {
    /**
     * Sort array according to distance (first tiebraker addTime data second one roadID)
     */
    public void sortArray() {
        Collections.sort(this, Comparator.comparingInt(Road::getDistance).thenComparingInt(Road::getAddTime).thenComparingInt(Road::getRoadID));
    }

    /**
     * Is a road in this RoadArray
     * 
     * @param splitedRoadLine input and output point of a road
     * @return true if road inclueded in this RoadArray
     */
    public boolean isInclude(String[] splitedRoadLine) {
        for (Road road : this) {
            if ((road.getInput().getName().equals(splitedRoadLine[0])
                    && road.getOutput().getName().equals(splitedRoadLine[1]))
                    || road.getInput().getName().equals(splitedRoadLine[1])
                            && road.getOutput().getName().equals(splitedRoadLine[0])) {
                return true;
            }
        }
        return false;
    }

}
