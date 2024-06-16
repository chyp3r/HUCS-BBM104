interface IBuilder {
    Decoration createDecoraitonItem(String name, String type, int price, int area);

    Classroom createClassroom(String name, String shape, float width, float lenght, float height);

    void setClassroom(Classroom classroom);

    void setFloorDecoration(Decoration decoration);

    void setWallDecoration(Decoration decoration);

    BuildedClassroom build(String[] args);
}
