public class Slot {
    private int count=0;
    private Product product = new Product("___", 0, 0, 0, 0);

    public int getCount(){return this.count;}
    public void setCount(int count){this.count = count;}
    public Product getProduct(){return this.product;}
    public void setProduct(Product pr){this.product = pr;}
    public void resetSlot(){this.product = new Product("___", 0, 0, 0, 0);}
}
