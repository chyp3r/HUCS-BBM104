import java.util.Collection;
import java.util.Collections;

class BuildStarter {
    /**
     * Builds classes sequentially based on input files
     * 
     * @param decorationData Array containing basic information of the decoration 
     * @param classData      Array containing basic information of the class
     * @param args           Arguments taken from terminal      
     * @param cs             Builder structure for the class to be built
     * @param bd             Director structure for the class to be built
     */
    static void startBuilding(String[] decorationData, String[] classData, String[] args, ClassroomBuilder cs,
            BuilderDirector bd) {
        String[] classroomData = new String[1];
        String[] wallDecorationData = new String[1];
        String[] floorDecoraitonData = new String[1];

        BuildedClassroom buildedClassroom = null;
        for (String data : decorationData) {
            String[] splitedDecorationData = data.split("\t");

            for (String cdata : classData) {
                String[] splitedClassData = cdata.split("\t");
                if (splitedDecorationData[0].equals(splitedClassData[1]) && splitedClassData[0].equals("CLASSROOM")) { // Find classrom information
                    classroomData = splitedClassData;
                } else if (splitedDecorationData[1].equals(splitedClassData[1]) && splitedClassData[0].equals("DECORATION")) { // Find wall decoration information
                    wallDecorationData = splitedClassData;
                } else if (splitedDecorationData[2].equals(splitedClassData[1]) && splitedClassData[0].equals("DECORATION")) { // Find floor decoration information
                    floorDecoraitonData = splitedClassData;
                }
            }
            bd.buildProduct(classroomData, wallDecorationData, floorDecoraitonData, cs); // Create components of classrooms
            buildedClassroom = cs.build(args); // Create buildedClassrom
        }
        FileIO.writeFile(args[2], "Total price is: " + (int) buildedClassroom.getTotalCost() + "TL.", true, false); // Write total cost line to output file
    }
}
