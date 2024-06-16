class ItemBook extends Item {
    private String author;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public ItemBook(String[] command) {
        this.setOrderType("Author");
        this.setName(command[2]);
        this.setAuthor(command[3]);
        this.setBarcode(command[4]);
        this.setPrice(Float.parseFloat(command[5]));
    }

    // Create text for displaying
    public String toString() {
        return String.format("Author of the %s is %s. Its barcode is %s and its price is %s", this.getName(),
                this.getAuthor(), this.getBarcode(), this.getPrice());
    }
}
