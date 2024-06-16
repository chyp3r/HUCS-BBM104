public class VoyagePremium extends VoyageBase {

    public VoyagePremium(String type, String id, String from, String to, String rows, String price, String refundCut,
            String preFee, String outputFile)
            throws Exception {
        this.type = type;
        this.from = from;
        this.to = to;
        checkID(id);
        checkRows(rows);
        checkPrice(price);
        checkCut(refundCut);
        checkFee(preFee);
        this.cutPrice = this.price - (this.price / 100.0f * (float) this.refundCut);
        this.prePrice = this.price + (this.price / 100.0f * (float) this.preFee);
        this.precCutPrice = this.prePrice - (this.prePrice / 100.0f * (float) this.refundCut);
        this.leftSeatCount = this.rows * 1;
        this.rightSeatCount = this.rows * 2;
        FileIO.writeFile(outputFile, String.format(
                "Voyage %d was initialized as a premium (1+2) voyage from %s to %s with %.2f TL priced %d regular seats and %.2f TL priced %d premium seats. Note that refunds will be %d%% less than the paid amount.",
                this.id, this.from, this.to, this.price, this.rightSeatCount, this.prePrice, this.leftSeatCount,
                this.refundCut), true, true);
    }

    /*
     * It also runs when the PRINT_VOYAGE command is called. Adds a summary of the
     * vehicle's current status to the output file
     */
    public void print(String outputFile) {
        FileIO.writeFile(outputFile, "Voyage " + this.id, true, true);
        FileIO.writeFile(outputFile, String.format("%s-%s", this.from, this.to), true, true);
        for (int i = 1; i <= this.leftSeatCount + this.rightSeatCount; i++) {
            if (i % 3 != 0)
                FileIO.writeFile(outputFile, String.format("%s ", isEmptySeat(i) == true ? "*" : "X"), true, false);
            else
                FileIO.writeFile(outputFile, String.format("%s", isEmptySeat(i) == true ? "*" : "X"), true, true);
            if (i % 3 == 1)
                FileIO.writeFile(outputFile, "| ", true, false);
        }
        FileIO.writeFile(outputFile, String.format("Revenue: %.2f", this.revenue), true, true);
    }
}
