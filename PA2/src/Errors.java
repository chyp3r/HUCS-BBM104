class CommandErroneousUsage extends Exception {
    public CommandErroneousUsage(String commandName) {
        super(String.format("ERROR: Erroneous usage of \"%s\" command!", commandName));
    }
}

class IdMustBePositiveException extends Exception {
    public IdMustBePositiveException(String id) {
        super(String.format("ERROR: %s is not a positive integer, ID of a voyage must be a positive integer!", id));
    }
}

class NumberOfSeatMustBePositiveException extends Exception {
    public NumberOfSeatMustBePositiveException(String rows) {
        super(String.format(
                "ERROR: %s is not a positive integer, number of seat rows of a voyage must be a positive integer!",
                rows));
    }

}

class PriceMustBePositiveException extends Exception {
    public PriceMustBePositiveException(String price) {
        super((String.format(
                "ERROR: %s is not a positive number, price must be a positive number!",
                price)));
    }
}

class RefundCutMustBePositiveException extends Exception {
    public RefundCutMustBePositiveException(String refundCut) {
        super(String.format(
                "ERROR: %s is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!",
                refundCut));
    }
}

class PremiumFeeMustBeNonNegativeException extends Exception {
    public PremiumFeeMustBeNonNegativeException(String preFee) {
        super(String.format(
                "ERROR: %s is not a non-negative integer, premium fee must be a non-negative integer!",
                preFee));
    }
}

class SameIdExistException extends Exception {
    public SameIdExistException(String id) {
        super(String.format(
                "ERROR: There is already a voyage with ID of %s!", id));
    }
}

class IdNotExistException extends Exception {
    public IdNotExistException(String id) {
        super(String.format("ERROR: There is no voyage with ID of %s!", id));
    }
}

class SeatNotExistException extends Exception {
    public SeatNotExistException() {
        super(String.format("ERROR: There is no such a seat!"));
    }
}

class SeatNumberMustBePositiveException extends Exception {
    public SeatNumberMustBePositiveException(String seat) {
        super(String.format("ERROR: %s is not a positive integer, seat number must be a positive integer!", seat));
    }
}

class SoldSeatException extends Exception {
    public SoldSeatException() {
        super(String.format("ERROR: One or more seats already sold!"));
    }
}

class EmptySeatException extends Exception {
    public EmptySeatException() {
        super(String.format("ERROR: One or more seats are already empty!"));
    }
}

class MinibusRefundException extends Exception {
    public MinibusRefundException() {
        super(String.format("ERROR: Minibus tickets are not refundable!"));
    }
}

class DuplicateSeatException extends Exception {
    public DuplicateSeatException() {
        super(String.format("ERROR: Duplicate seat does not allowed!"));
    }
}