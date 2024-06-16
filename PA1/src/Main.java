class Main
{
    public static void main(String[] args)
    {
        String[] produtctsFromFile = FileIO.readFile(args[0], false, false);
        String[] purchasesFromFile = FileIO.readFile(args[1], false, false);
        Product[] productsFromMachine = new Product[produtctsFromFile.length];  
        Gmm machine = new Gmm(args,produtctsFromFile,purchasesFromFile,productsFromMachine);
        machine.createSlots();   
        machine.createProducts();
        machine.buyProducts();
        machine.writeSlotInfo();
    }
}

