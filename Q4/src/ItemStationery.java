class ItemStationery extends Item {
    private String kind;

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return this.kind;
    }

    public ItemStationery(String[] command) {
        this.setOrderType("Kind");
        this.setName(command[2]);
        this.setKind(command[3]);
        this.setBarcode(command[4]);
        this.setPrice(Float.parseFloat(command[5]));
    }

    // Create text for displaying
    public String toString() {
        return String.format("Kind of the %s is %s. Its barcode is %s and its price is %s", this.getName(),
                this.getKind(), this.getBarcode(), this.getPrice());
    }
}
