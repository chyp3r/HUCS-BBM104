abstract class Classroom implements IClassroom {
    protected String name = null;
    protected float width = 0;
    protected float lenght = 0;
    protected float height = 0;
    protected float floorArea = 0;
    protected float floorPerimeter = 0;
    protected float wallArea = 0;

    abstract public float calculateFloorArea();

    abstract public float calculateFloorPerimeter();

    abstract public float calculateWallArea();
}
