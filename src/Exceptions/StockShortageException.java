package Exceptions;

public class StockShortageException extends RuntimeException {
    public StockShortageException(String message) {
        super(message);
    }
}
