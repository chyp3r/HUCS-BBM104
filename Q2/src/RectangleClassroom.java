class RectangleClassroom extends Classroom {
    public RectangleClassroom(String name, Float width, Float lenght, Float height) {
        this.name = name;
        this.width = width;
        this.lenght = lenght;
        this.height = height;
        this.floorArea = calculateFloorArea();
        this.floorPerimeter = calculateFloorPerimeter();
        this.wallArea = calculateWallArea();
    }

    public float calculateFloorArea() {
        return (float) (this.width * this.lenght);
    }

    public float calculateFloorPerimeter() {
        return (float) ((this.width + this.lenght) * 2);
    }

    public float calculateWallArea() {
        return this.floorPerimeter * this.height;
    }
}
