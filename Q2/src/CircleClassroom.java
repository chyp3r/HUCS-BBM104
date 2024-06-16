import java.lang.Math;

class CircleClassroom extends Classroom {
    public CircleClassroom(String name, float width, float lenght, float height) {
        this.name = name;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
        this.floorArea = calculateFloorArea();
        this.floorPerimeter = calculateFloorPerimeter();
        this.wallArea = calculateWallArea();
    }

    public float calculateFloorArea() {
        return (float) (this.width / 4 * this.lenght * Math.PI);
    }

    public float calculateFloorPerimeter() {
        return (float) ((this.width) * Math.PI);
    }

    public float calculateWallArea() {
        return this.floorPerimeter * this.height;
    }
}
