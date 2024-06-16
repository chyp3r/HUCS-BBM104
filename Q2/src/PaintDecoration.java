class PaintDecoration extends Decoration {
    public PaintDecoration(String name, int price) {
        this.name = name;
        this.price = price;
    }

    float calculateCost(float classArea) {
        return this.price * classArea;
    }

    String usedMessage(float classArea) {
        return String.format("%dm2 of Paint", (int) (Math.ceil(classArea)));
    }
}
