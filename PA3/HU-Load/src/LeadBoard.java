class LeadBoard {
    private static final String path = "lead.txt";
    
    /**
     * Read lead data from path
     * 
     * @return String[] data from file
     */
    public static String[] getLeadData() {
        return FileIO.readFile(path, false, false);
    }

    /**
     * Write to file name and point information
     * 
     * @param name  // Name for write to file
     * @param point // Point data for write to file
     */
    public static void addLeadData(String name,int point) {
        FileIO.writeFile(path, String.format("%s: %d Point",name,point), true, true);
    }
}
