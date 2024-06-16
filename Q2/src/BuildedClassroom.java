class BuildedClassroom {
    private Classroom classrom;
    private Decoration wallDecoration;
    private Decoration floorDecoration;
    private double cost;
    private static float totalCost;

    /**
     * Class built based on given parameters 
     * When the class is created, creation information is written to a 2-indexed file.
     * 
     * @param classrom        Empty classroom (without decorations)
     * @param wallDecoration  Decoration for wall 
     * @param floorDecoration Decoration for ground floor
     * @param args            Arguments taken from terminal
     */
    public BuildedClassroom(Classroom classrom, Decoration wallDecoration, Decoration floorDecoration, String[] args) {
        this.classrom = classrom;
        this.wallDecoration = wallDecoration;
        this.floorDecoration = floorDecoration;
        this.cost = Math.ceil(
                wallDecoration.calculateCost(classrom.wallArea) + floorDecoration.calculateCost(classrom.floorArea));
        totalCost += this.cost;
        FileIO.writeFile(args[2],
                String.format("Classroom %s used %s for walls and used %s for flooring, these costed %dTL.",
                        this.classrom.name,
                        this.wallDecoration.usedMessage(this.classrom.wallArea),
                        this.floorDecoration.usedMessage(this.classrom.floorArea),
                        (int) cost),
                true, true);
    }

    static float getTotalCost() {
        return totalCost;
    }
}