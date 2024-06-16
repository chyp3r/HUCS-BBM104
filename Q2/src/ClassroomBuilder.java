class ClassroomBuilder implements IBuilder {

    private Classroom classShape;
    private Decoration wallDecoration;
    private Decoration floorDecoration;

    
    public Decoration createDecoraitonItem(String name, String type, int price, int area) {
        if (type.equals("Tile")) {
            return new TileDecoration(name, price, area);
        } else if (type.equals("Paint")) {
            return new PaintDecoration(name, price);
        } else if (type.equals("Wallpaper")) {
            return new WallpaperDecoration(name, price);
        }
        return null;
    }

    public Classroom createClassroom(String name, String shape, float width, float lenght, float height) {
        if (shape.equals("Circle")) {
            return new CircleClassroom(name, width, lenght, height);
        } else if (shape.equals("Rectangle")) {
            return new RectangleClassroom(name, width, lenght, height);
        }
        return null;

    }

    public void setClassroom(Classroom classroom) {
        this.classShape = classroom;
    }

    public void setFloorDecoration(Decoration decoration) {
        this.floorDecoration = decoration;
    }

    public void setWallDecoration(Decoration decoration) {
        this.wallDecoration = decoration;
    }

    public BuildedClassroom build(String[] args) {
        return new BuildedClassroom(this.classShape, this.wallDecoration, this.floorDecoration, args);
    }

}
