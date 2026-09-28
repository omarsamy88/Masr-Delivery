package Exceptions;

public class RiderBusyException extends RuntimeException {
    public RiderBusyException(String message) {
        super(message);
    }
}
