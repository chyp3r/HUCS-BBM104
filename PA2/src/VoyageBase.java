import java.util.ArrayList;

abstract class VoyageBase {
    // All variables used by Minibus Standard and Premium vehicles
    // They are used as protected so that they can be inherited. That's why there
    // are no getter and setter functions
    // All variables have been combined in the base class so that features can be
    // transferred between vehicles with future updates.
    protected String type;
    protected int id;
    protected String from;
    protected String to;
    protected int rows;
    protected double price;
    protected int refundCut;
    protected int preFee;
    protected int leftSeatCount;
    protected int rightSeatCount;
    protected double revenue;
    protected double cutPrice;
    protected double prePrice;
    protected double precCutPrice;
    protected ArrayList<Integer> notEmptySeats = new ArrayList<>();

    /**
     * Checks the qualifications of the ID
     * 
     * @param id ID for checking
     * @throws IdMustBePositiveException Situations where ID is not positive
     * @throws SameIdExistException      Situations where ID has been used before
     */
    public void checkID(String id) throws IdMustBePositiveException, SameIdExistException {
        try {
            this.id = Integer.parseInt(id);
            if (this.id <= 0)
                throw new IdMustBePositiveException(id);
            for (VoyageBase vb : VoyageManager.getVoyageList()) {
                if (vb.id == this.id)
                    throw new SameIdExistException(id);
            }
        } catch (NumberFormatException e) {
            throw new IdMustBePositiveException(id);
        }
    }

    /**
     * Checks the qualifications of the Rows
     * 
     * @param rows Rows for checking
     * @throws NumberOfSeatMustBePositiveException Situations where Rows is not
     *                                             positive
     */
    public void checkRows(String rows) throws NumberOfSeatMustBePositiveException {
        try {
            this.rows = Integer.parseInt(rows);
            if (this.rows <= 0)
                throw new NumberOfSeatMustBePositiveException(rows);
        } catch (NumberFormatException e) {
            throw new NumberOfSeatMustBePositiveException(rows);
        }
    }

    /**
     * Checks the qualifications of the Price
     * 
     * @param price Price for checking
     * @throws PriceMustBePositiveException Situations where Price is not positive
     */
    public void checkPrice(String price) throws PriceMustBePositiveException {
        try {
            this.price = Float.parseFloat(price);
            if (this.price <= 0)
                throw new PriceMustBePositiveException(price);
        } catch (NumberFormatException e) {
            throw new PriceMustBePositiveException(price);
        }
    }

    /**
     * Checks the qualifications of the Refund Cut
     * 
     * @param refundCut Refund Cut for checking
     * @throws RefundCutMustBePositiveException Situations where Refund Cut is not
     *                                          in the range [0,100]
     */
    public void checkCut(String refundCut) throws RefundCutMustBePositiveException {
        try {
            this.refundCut = Integer.parseInt(refundCut);
            if (this.refundCut > 100 || this.refundCut < 0)
                throw new RefundCutMustBePositiveException(refundCut);
        } catch (NumberFormatException e) {
            throw new RefundCutMustBePositiveException(refundCut);
        }
    }

    /**
     * Checks the qualifications of the Premium Fee
     * 
     * @param preFee Premium Fee for checking
     * @throws PremiumFeeMustBeNonNegativeException Situations where Fee is not
     *                                              positive
     */
    public void checkFee(String preFee) throws PremiumFeeMustBeNonNegativeException {
        try {
            this.preFee = Integer.parseInt(preFee);
            if (this.preFee > 100 || this.preFee < 0)
                throw new PremiumFeeMustBeNonNegativeException(preFee);
        } catch (NumberFormatException e) {
            throw new PremiumFeeMustBeNonNegativeException(preFee);
        }

    }

    /**
     * Checks whether the seat to be used is empty or not
     * 
     * @param seatNumberforControl Seat number to check
     * @return true if the seat is available, otherwise false
     */
    public boolean isEmptySeat(int seatNumberforControl) {
        for (int seatNumber : this.notEmptySeats) {
            if (seatNumber == seatNumberforControl)
                return false;
        }
        return true;
    }

    abstract void print(String outputFile);

}
