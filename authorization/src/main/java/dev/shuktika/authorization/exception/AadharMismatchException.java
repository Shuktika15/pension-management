package dev.shuktika.authorization.exception;

public class AadharMismatchException extends RuntimeException {
    public AadharMismatchException(String msg) {
        super(msg);
    }
}
