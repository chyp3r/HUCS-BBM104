import java.util.HashMap;

class BNFManager {
    private String[] args;

    public BNFManager(String args[]) {
        this.args = args;
    }

    /**
     * Using the given HashMap, the terminal recursively finds items and writes them to the file.
     * 
     * @param bnfMap BNF data of type Hashmap
     * @param key    Terminal items searched for non-terminal to printing
     */
    public void recBNFSolver(HashMap<String, String> bnfMap, String key) {
        FileIO.writeFile(this.args[1], "(", true, false);
        String argument = bnfMap.get(key); // Find the "non-terminal" item to write 
        char[] argArray = argument.toCharArray(); 
        for (char t : argArray) {
            if (!bnfMap.containsKey(String.valueOf(t))) {
                FileIO.writeFile(this.args[1], String.valueOf(t), true, false); // Print "terminal" items
            } else {
                recBNFSolver(bnfMap, String.valueOf(t)); // If they are not "terminal" items keep looking for terminals
            }
        }
        FileIO.writeFile(this.args[1], ")", true, false);
    }

    /**
     * Divides the lines in the given input data with "->" and adds them as hashmape key value pairs
     * 
     * @param data Data to be converted to HashMap
     * @return  Created HashMap
     */
    public static HashMap<String, String> mapCreater(String[] data) {
        HashMap<String,String> bnfMap = new HashMap<String, String>();
        for (String temp : data) {
            bnfMap.put(temp.split("->")[0], temp.split("->")[1]);
        }
        return bnfMap;
    }
}
