public class VoyageMinibus extends VoyageBase {
    public VoyageMinibus(String type, String id, String from, String to, String rows, String price, String outputFile)
            throws Exception {
        this.type = type;
        this.from = from;
        this.to = to;
        checkID(id);
        checkRows(rows);
        checkPrice(price);
        this.leftSeatCount = 0;
        this.rightSeatCount = this.rows * 2;
        FileIO.writeFile(outputFile, String.format(
                "Voyage %d was initialized as a minibus (2) voyage from %s to %s with %.2f TL priced %d regular seats. Note that minibus tickets are not refundable.",
                this.id, this.from, this.to, this.price, this.rightSeatCount), true, true);
    }

    /*
     * It also runs when the PRINT_VOYAGE command is called. Adds a summary of the
     * vehicle's current status to the output file
     */
    public void print(String outputFile) {
        FileIO.writeFile(outputFile, "Voyage " + this.id, true, true);
        FileIO.writeFile(outputFile, String.format("%s-%s", this.from, this.to), true, true);
        for (int i = 1; i <= this.leftSeatCount + this.rightSeatCount; i++) {
            if (i % 2 != 0)
                FileIO.writeFile(outputFile, String.format("%s ", isEmptySeat(i) == true ? "*" : "X"), true, false);
            else
                FileIO.writeFile(outputFile, String.format("%s", isEmptySeat(i) == true ? "*" : "X"), true, true);
        }
        FileIO.writeFile(outputFile, String.format("Revenue: %.2f", this.revenue), true, true);
    }
}
