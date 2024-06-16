abstract class Decoration {
    protected String name;
    protected int price;

    abstract float calculateCost(float classArea);

    abstract String usedMessage(float classArea);
}