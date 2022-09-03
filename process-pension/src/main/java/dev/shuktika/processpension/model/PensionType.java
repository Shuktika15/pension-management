package dev.shuktika.processpension.model;

import dev.shuktika.processpension.exception.PensionTypeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PensionType {
    SELF("self"),
    FAMILY("family");

    private final String pensionTypeName;

    public static PensionType getPensionType(String pensionTypeName) {
        return Arrays.stream(PensionType.values())
                .filter(pensionType -> pensionType.getPensionTypeName().equals(pensionTypeName))
                .findFirst()
                .orElseThrow(() -> new PensionTypeNotFoundException(String.format(
                        "Pension type %s is not accepted", pensionTypeName)));
    }
}
