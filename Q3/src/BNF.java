/**
 * The preferred collections structure for storing BNF data is "HashMap", which is a Map.
 * The biggest reason for choosing HashMap is that storing the data in the form of key 
 * values ​​will make the search process easier, and in a large recursive operation, the 
 * map type can find the key-values ​​sought at much higher speeds than lists or other 
 * collections.
 */

import java.util.HashMap;

class BNF {
    public static void main(String[] args) {
        String[] inputBNF = FileIO.readFile(args[0], true, true);
        HashMap<String, String> bnfMap = BNFManager.mapCreater(inputBNF);
        BNFManager manager = new BNFManager(args);
        manager.recBNFSolver(bnfMap, "S");
    }
}