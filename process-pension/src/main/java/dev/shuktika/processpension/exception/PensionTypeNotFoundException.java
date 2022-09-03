package dev.shuktika.processpension.exception;

import java.util.NoSuchElementException;

public class PensionTypeNotFoundException extends NoSuchElementException {
    public PensionTypeNotFoundException(String msg) {
        super(msg);
    }
}
