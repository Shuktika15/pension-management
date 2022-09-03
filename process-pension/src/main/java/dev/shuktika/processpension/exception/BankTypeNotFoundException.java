package dev.shuktika.processpension.exception;

import java.util.NoSuchElementException;

public class BankTypeNotFoundException extends NoSuchElementException {
    public BankTypeNotFoundException(String msg) {
        super(msg);
    }
}
