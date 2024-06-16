class WallpaperDecoration extends Decoration {
    public WallpaperDecoration(String name, int price) {
        this.name = name;
        this.price = price;
    }
    
    float calculateCost(float classArea) {
        return this.price * classArea;
    }

    String usedMessage(float classArea) {
        return String.format("%dm2 of Wallpaper", (int) (Math.ceil(classArea)));
    }
}
