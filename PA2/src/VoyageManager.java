import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class VoyageManager {
    private static List<VoyageBase> voyageList; // Created voyages
    private String outputFile;

    public static List<VoyageBase> getVoyageList() {
        return voyageList;
    }

    public VoyageManager(String outputfile) {
        voyageList = new ArrayList<>();
        this.outputFile = outputfile;
    }

    /**
     * Checks the requirements of the INIT VOYAGE command and executes it
     * 
     * @param voyageInfo Command data for calculations
     *                   <ul>
     *                   <li>Format:
     *                   <ul>
     *                   <li>INIT_VOYAGE Minibus ID FROM TO ROWS PRICE</li>
     *                   </ul>
     *                   <ul>
     *                   <li>INIT_VOYAGE Standard ID FROM TO ROWS PRICE
     *                   REFUND_CUT</li>
     *                   </ul>
     *                   <ul>
     *                   <li>INIT_VOYAGE Premium ID FROM TO ROWS PRICE REFUND_CUT
     *                   PREMIUM_FEE</li>
     *                   </ul>
     *                   </ul>
     */
    public void initVoyage(String[] voyageInfo) {
        try {
            if (voyageInfo.length == 7 && voyageInfo[1].equals("Minibus")) {
                voyageList.add(new VoyageMinibus(voyageInfo[1],
                        voyageInfo[2], voyageInfo[3], voyageInfo[4],
                        voyageInfo[5], voyageInfo[6], this.outputFile));

            } else if (voyageInfo.length == 8 && voyageInfo[1].equals("Standard")) {
                voyageList.add(new VoyageStandard(voyageInfo[1],
                        voyageInfo[2], voyageInfo[3], voyageInfo[4],
                        voyageInfo[5], voyageInfo[6],
                        voyageInfo[7], this.outputFile));

            } else if (voyageInfo.length == 9 && voyageInfo[1].equals("Premium")) {
                voyageList.add(new VoyagePremium(voyageInfo[1],
                        voyageInfo[2], voyageInfo[3], voyageInfo[4],
                        voyageInfo[5], voyageInfo[6],
                        voyageInfo[7], voyageInfo[8], this.outputFile));
            } else {
                throw new CommandErroneousUsage("INIT_VOYAGE");
            }
        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }

    }

    /**
     * Checks the requirements of the SELL TICKET command and executes it
     * 
     * @param ticketInfo Ticket informations for calculations. Format -> SELL_TICKET
     *                   VOYAGE_ID SEAT_NUMBER_SEAT_NUMBER...
     */
    public void sellTicket(String[] ticketInfo) {
        VoyageBase tempVoyage;
        try {
            if (ticketInfo.length != 3)
                throw new CommandErroneousUsage("SELL_TICKET");

            tempVoyage = findVoyage(ticketInfo[1]);

            // Control for ticket informations
            int seatcounter = 0;
            for (String seat : ticketInfo[2].split("_")) {
                try {
                    if (seat == "")
                        try {
                            if (ticketInfo[2].split("_")[seatcounter + 1] == "_")
                                throw new SeatNumberMustBePositiveException("_");
                            else
                                throw new SeatNumberMustBePositiveException("");

                        } catch (SeatNumberMustBePositiveException e) {
                            throw new SeatNumberMustBePositiveException("_");
                        } catch (Exception e) {
                            // In last index of ticket info
                        }
                    if (Integer.parseInt(seat) <= 0)
                        throw new SeatNumberMustBePositiveException(seat);
                    if (Integer.parseInt(seat) > tempVoyage.leftSeatCount + tempVoyage.rightSeatCount)
                        throw new SeatNotExistException();

                    seatcounter++;
                } catch (NumberFormatException e) {
                    throw new SeatNumberMustBePositiveException(seat);
                }
                if (ticketInfo[2].endsWith("_"))
                    throw new SeatNumberMustBePositiveException("_");
                for (int noempty : tempVoyage.notEmptySeats) {
                    if (noempty == Integer.parseInt(seat))
                        throw new SoldSeatException();
                }
            }

            // Duplicate input check
            LinkedHashSet<String> tempSet = new LinkedHashSet<String>();
            List<String> tempList = new ArrayList<>();
            for (int i = 0; i < ticketInfo[2].split("_").length; i++) {
                tempSet.add(ticketInfo[2].split("_")[i]);
                tempList.add(ticketInfo[2].split("_")[i]);
            }
            if (tempSet.size() != tempList.size()) {
                throw new DuplicateSeatException();
            }

            // Ticket sale
            double lastRevenue = tempVoyage.revenue; //
            for (String seat : ticketInfo[2].split("_")) {
                tempVoyage.notEmptySeats.add(Integer.parseInt(seat));
                if (tempVoyage.type.equals("Premium")) {
                    if (Integer.parseInt(seat) % 3 == 1)
                        tempVoyage.revenue += tempVoyage.prePrice;
                    else
                        tempVoyage.revenue += tempVoyage.price;

                } else
                    tempVoyage.revenue += tempVoyage.price;

            }
            FileIO.writeFile(outputFile, String.format(
                    "Seat %s of the Voyage %d from %s to %s was successfully sold for %.2f TL.",
                    ticketInfo[2].replace("_", "-"), tempVoyage.id, tempVoyage.from, tempVoyage.to,
                    tempVoyage.revenue - lastRevenue), true,
                    true);

        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }
    }

    /**
     * Checks the requirements of the REFUND TICKET command and executes it
     * 
     * @param ticketInfo Ticket informations for calculations. Format REFUND_TICKET
     *                   VOYAGE_ID SEAT_NUMBER_SEAT_NUMBER...
     */
    public void refundTicket(String[] ticketInfo) {
        VoyageBase tempVoyage;
        try {
            if (ticketInfo.length != 3)
                throw new CommandErroneousUsage("REFUND_TICKET");

            tempVoyage = findVoyage(ticketInfo[1]);

            // Minibus hasn't got refund command
            if (tempVoyage.type.equals("Minibus"))
                throw new MinibusRefundException();

            // Refund control
            int seatcounter = 0;
            for (String seat : ticketInfo[2].split("_")) {
                boolean foundSeat = false;
                try {
                    if (seat == "")
                    try {
                        if (ticketInfo[2].split("_")[seatcounter + 1] == "_")
                            throw new SeatNumberMustBePositiveException("_");
                        else
                            throw new SeatNumberMustBePositiveException("");

                    } catch (SeatNumberMustBePositiveException e) {
                        throw new SeatNumberMustBePositiveException("_");
                    } catch (Exception e) {
                        // In last index of ticket info
                    }
                    if (Integer.parseInt(seat) <= 0)
                        throw new SeatNumberMustBePositiveException(seat);
                    if (Integer.parseInt(seat) > tempVoyage.leftSeatCount + tempVoyage.rightSeatCount)
                        throw new SeatNotExistException();
                    seatcounter++;
                } catch (NumberFormatException e) {
                    throw new SeatNumberMustBePositiveException(seat);
                }
                for (int fullSeat : tempVoyage.notEmptySeats) {
                    if (fullSeat == Integer.parseInt(seat))
                        foundSeat = true;

                }
                if (!foundSeat) {
                    throw new EmptySeatException();
                }
            }

            // Duplicate input check
            LinkedHashSet<String> tempSet = new LinkedHashSet<String>();
            List<String> tempList = new ArrayList<>();
            for (int i = 0; i < ticketInfo[2].split("_").length; i++) {
                tempSet.add(ticketInfo[2].split("_")[i]);
                tempList.add(ticketInfo[2].split("_")[i]);
            }
            if (tempSet.size() != tempList.size()) {
                throw new DuplicateSeatException();
            }

            // Ticket refund
            double lastRevenue = tempVoyage.revenue;
            for (String seat : ticketInfo[2].split("_")) {
                tempVoyage.notEmptySeats.removeIf(sn -> sn == Integer.parseInt(seat));
                if (tempVoyage.type.equals("Premium")) {
                    if (Integer.parseInt(seat) % 3 == 1)
                        tempVoyage.revenue -= tempVoyage.precCutPrice;
                    else
                        tempVoyage.revenue -= tempVoyage.cutPrice;
                } else
                    tempVoyage.revenue -= tempVoyage.cutPrice;

            }
            FileIO.writeFile(outputFile, String.format(
                    "Seat %s of the Voyage %d from %s to %s was successfully refunded for %.2f TL.",
                    ticketInfo[2].replace("_", "-"), tempVoyage.id, tempVoyage.from, tempVoyage.to,
                    lastRevenue - tempVoyage.revenue), true,
                    true);

        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }
    }

    /**
     * Print the voyage informations to output file
     * 
     * @param voyageInfo Command details for start printing. Format -> PRINT_VOYAGE
     *                   VOYAGE_ID
     */
    public void printVoyage(String[] voyageInfo) {
        try {
            if (voyageInfo.length != 2)
                throw new CommandErroneousUsage("PRINT_VOYAGE");

            findVoyage(voyageInfo[1]).print(this.outputFile);
        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }
    }

    /**
     * Cancel the voyage and delete from voyageList
     * 
     * @param voyageInfo Command details for canceling voyage. Format ->
     *                   CANCEL_VOYAGE VOYAGE_ID
     */
    public void cancelVoyage(String[] voyageInfo) {
        VoyageBase tempVoyage;

        try {
            if (voyageInfo.length != 2)
                throw new CommandErroneousUsage("CANCEL_VOYAGE");

            // Cancel and delete voyage
            tempVoyage = findVoyage(voyageInfo[1]);
            voyageList.removeIf(vb -> vb.id == Integer.parseInt(voyageInfo[1]));
            FileIO.writeFile(outputFile, String.format(
                    "Voyage %s was successfully cancelled!\nVoyage details can be found below:", voyageInfo[1]), true,
                    true);

            // Revenue fix
            for (int seat : tempVoyage.notEmptySeats) {
                if (tempVoyage.type.equals("Premium")) {
                    if (seat % 3 == 1)
                        tempVoyage.revenue -= tempVoyage.prePrice;
                    else
                        tempVoyage.revenue -= tempVoyage.price;
                } else
                    tempVoyage.revenue -= tempVoyage.price;

            }
            tempVoyage.print(this.outputFile);
        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }
    }

    /**
     * Prints out all of theavailable voyages at that moment in the ascending order
     * of their ID
     * 
     * @param voyageInfo Format -> Z_REPORT
     */
    public void zReport(String[] voyageInfo) {
        try {
            if (voyageInfo.length != 1)
                throw new CommandErroneousUsage("Z_REPORT");
            voyageList.sort((voyage2, voyage1) -> {
                return voyage2.id - voyage1.id;
            });

            FileIO.writeFile(this.outputFile, "Z Report:", true, true);
            if (voyageList.size() > 0) {
                for (VoyageBase vb : voyageList) {
                    FileIO.writeFile(this.outputFile, "----------------", true, true);
                    vb.print(this.outputFile);
                }
                FileIO.writeFile(this.outputFile, "----------------", true, true);
            } else {
                FileIO.writeFile(this.outputFile, "----------------", true, true);
                FileIO.writeFile(this.outputFile, "No Voyages Available!", true, true);
                FileIO.writeFile(this.outputFile, "----------------", true, true);
            }
        } catch (Exception e) {
            FileIO.writeFile(this.outputFile, e.getMessage(), true, true);
        }

    }

    /**
     * Finds the Voyage given its ID
     *
     * @param id ID to be found
     * @return Founded voyage
     * @throws IdNotExistException       The ID given is not valid
     * @throws IdMustBePositiveException ID is not positive
     */
    public VoyageBase findVoyage(String id) throws IdNotExistException, IdMustBePositiveException {
        try {
            if (Integer.parseInt(id) <= 0)
                throw new IdMustBePositiveException(id);
        } catch (Exception e) {
            throw new IdMustBePositiveException(id);
        }
        for (VoyageBase vb : voyageList) {
            if (vb.id == Integer.parseInt(id))
                return vb;
        }
        throw new IdNotExistException(id);
    }

}
