import java.util.ArrayList;

abstract class Item {
    private String orderType; // Sort parameter 
    private String name;
    private String barcode;
    private float price;
    private static ArrayList<Item> itemArray = new ArrayList<Item>(); // ArrayList for hold all items
    
    public String getType()
    {
        return this.orderType;
    }
    public void setOrderType(String type)
    {
        this.orderType = type;
    }
    public static ArrayList<Item> getItemArray() {
        return itemArray;
    }

    public static void addItemArray(Item item) {
        itemArray.add(item);
    }

    public static void deleteItemArray(Item item) {
        itemArray.remove(item);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
