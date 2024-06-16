public class Product {
    private String name;
    private int price;
    private float protein;
    private float carbohydrate;
    private float fat; 
    private float calorie;  

    public void setName(String name){this.name = name;}
    public void setPrice(int price){this.price = price;}
    public void setProtein(float protein){this.protein = protein;}
    public void setCarbonhydrate(float carbohydrate){this.carbohydrate = carbohydrate;}
    public void setFat(float fat){this.fat = fat;}
    public void setCalorie(float calorie){this.calorie = calorie;}

    public String getName(){return this.name;}
    public int getPrice(){return this.price;}
    public float getProtein(){return this.protein;}
    public float getCarbonhydrate(){return this.carbohydrate;}
    public float getFat(){return this.fat;}
    public float getCalorie(){return this.calorie;}

    public Product(String name, int price, float protein, float carbohydrate, float fat)
    {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calorie = 4*this.protein+4*this.carbohydrate+9*this.fat;
    }

}
