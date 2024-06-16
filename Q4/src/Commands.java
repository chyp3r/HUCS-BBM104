import static java.util.Comparator.comparing;

abstract class Command {
    private String[] commandDetails; // Command arguments

    public String[] getCommandDetails() {
        return this.commandDetails;
    }

    public void setCommandDetails(String[] detail) {
        this.commandDetails = detail;
    }

    abstract public void executeCommand();
}

class AddCommand extends Command {
    /**
     * Create items. Sample commands:
     * <li>ADD[tab]Book[tab]name[tab]author[tab]barcode[tab]price</li>
     * <li>ADD[tab]Toy[tab]name[tab]color[tab]barcode[tab]price</li>
     * <li>ADD[tab]Stationery[tab]name[tab]kind[tab]barcode[tab]price</li>
     * 
     * @param command Argument list for creating objects
     */
    public AddCommand(String[] command) {
        setCommandDetails(command);
    }

    public void executeCommand() {
        switch (getCommandDetails()[1]) { // Create Objects
            case "Toy":
                Item.addItemArray(new ItemToy(getCommandDetails()));
                break;
            case "Book":
                Item.addItemArray(new ItemBook(getCommandDetails()));
                break;
            case "Stationery":
                Item.addItemArray(new ItemStationery(getCommandDetails()));
                break;
        }
    }

}

class RemoveCommand extends Command {
    /**
     * Removing items due to barcode number. Sample commands:
     * <li>REMOVE[tab]barcode</li>
     * 
     * @param command Argument list for remove objects
     */
    public RemoveCommand(String[] command) {
        setCommandDetails(command);
    }

    public void executeCommand() {
        FileIO.writeFile(CommandManager.getOutputPath(), ("REMOVE RESULTS:"), true, true);
        Item tempItem = SearchTools.findWithBarcode(getCommandDetails()[1]); // Try to find item due to barcode
        if (tempItem != null) {
            Item.deleteItemArray(tempItem); // Delete item
            FileIO.writeFile(CommandManager.getOutputPath(), ("Item is removed."), true, true);
        } else
            FileIO.writeFile(CommandManager.getOutputPath(), ("Item is not found."), true, true);
        FileIO.writeFile(CommandManager.getOutputPath(), ("------------------------------"), true, true);
    }
}

class SearchByBarcodeCommand extends Command {
    /**
     * Find item due to barcode. Sample command:
     * <li>SEARCHBYBARCODE[tab]barcode</li>
     * 
     * @param command Argument list for find objects
     */
    public SearchByBarcodeCommand(String[] command) {
        setCommandDetails(command);
    }

    public void executeCommand() {
        SearchTools.searchResult(SearchTools.findWithBarcode(getCommandDetails()[1]));
    }
}

class SearchByNameCommand extends Command {
    /**
     * Find item due to name. Sample command:
     * <li>SEARCHBYNAME[tab]name</li>
     * 
     * @param command Argument list for find objects
     */
    public SearchByNameCommand(String[] command) {
        setCommandDetails(command);
    }

    public void executeCommand() {
        SearchTools.searchResult(SearchTools.findWithName(getCommandDetails()[1]));
    }
}

class SearchTools {
    /**
     * Find item due to name
     * 
     * @param <E>  Type of name
     * @param name Search argumenet
     * @return founded item or if item does not exists null
     */
    public static <E> Item findWithName(E name) {
        for (Item item : Item.getItemArray()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Find item due to barcode
     * 
     * @param <E>  Type of barcode
     * @param name Search argumenet
     * @return founded item or if item does not exists null
     */
    public static <E> Item findWithBarcode(E barcode) {
        for (Item item : Item.getItemArray()) {
            if (item.getBarcode().equals(barcode)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Write result of search to output file
     * @param <E>  type of item
     * @param item founded item or null
     */
    public static <E> void searchResult(E item) {
        FileIO.writeFile(CommandManager.getOutputPath(), ("SEARCH RESULTS:"), true, true);
        if (item != null)
            FileIO.writeFile(CommandManager.getOutputPath(), (item.toString()), true, true);
        else
            FileIO.writeFile(CommandManager.getOutputPath(), ("Item is not found."), true, true);
        FileIO.writeFile(CommandManager.getOutputPath(), ("------------------------------"), true, true);
    }
}

class DisplayCommand extends Command {
    /**
     * Display all items. Sample command:
     * <li>DISPLAY</li>
     * 
     * @param command Argument list for display objects
     */
    public DisplayCommand(String[] command) {
        setCommandDetails(command);
    }

    public void executeCommand() {
        Item.getItemArray().sort(comparing(Item::getType));
        FileIO.writeFile(CommandManager.getOutputPath(), ("INVENTORY:"), true, true);
        for (Item item : Item.getItemArray()) {
            display(item);
        }
        FileIO.writeFile(CommandManager.getOutputPath(), ("------------------------------"), true, true);
    }

    public static <E> void display(E item) {
        FileIO.writeFile(CommandManager.getOutputPath(), (item.toString()), true, true);
    }
}
