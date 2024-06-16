class BuilderDirector {
        /**
         * It manages the parameters of the classes to be built and gives the construction commands of the parts with the necessary parameters.
         * 
         * @param classroomData Array containing basic information of the class
         * @param wallData      Array containing basic information about wall decoration
         * @param floorData     Array containing basic information about floor decoration
         * @param cs            Builder structure for the class to be built
         */
        public void buildProduct(String[] classroomData, String[] wallData, String[] floorData, ClassroomBuilder cs) {
                cs.setClassroom(cs.createClassroom(classroomData[1], classroomData[2],
                                Float.parseFloat(classroomData[3]),
                                Float.parseFloat(classroomData[4]), Float.parseFloat(classroomData[5])));
                cs.setWallDecoration(cs.createDecoraitonItem(wallData[1], wallData[2], Integer.parseInt(wallData[3]),
                                wallData.length == 5 ? Integer.parseInt(wallData[4]) : 1)); // 4th parameter takes value for "tile"
                cs.setFloorDecoration(
                                cs.createDecoraitonItem(floorData[1], floorData[2], Integer.parseInt(floorData[3]),
                                                floorData.length == 5 ? Integer.parseInt(floorData[4]) : 1)); // 4th parameter takes value for "tile"
        }
}
