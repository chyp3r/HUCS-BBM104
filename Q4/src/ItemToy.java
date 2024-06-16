class ItemToy extends Item {
    private String color;

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public ItemToy(String[] command) {
        this.setOrderType("Color");
        this.setName(command[2]);
        this.setColor(command[3]);
        this.setBarcode(command[4]);
        this.setPrice(Float.parseFloat(command[5]));
    }

    // Create text for displaying
    public String toString() {
        return String.format("Color of the %s is %s. Its barcode is %s and its price is %s", this.getName(),
                this.getColor(), this.getBarcode(), this.getPrice());
    }
}
