class TileDecoration extends Decoration {
    private int area;

    public TileDecoration(String name, int price, int area) {
        this.name = name;
        this.price = price;
        this.area = area;
    }

    float calculateCost(float classArea) {
        return (float) (this.price * (Math.ceil((classArea / this.area))));
    }

    String usedMessage(float classArea) {
        return String.format("%d Tiles",
                (int) (Math.ceil(classArea / this.area)));
    }
}
