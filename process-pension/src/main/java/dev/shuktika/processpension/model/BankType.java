package dev.shuktika.processpension.model;

import dev.shuktika.processpension.exception.BankTypeNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BankType {
    PRIVATE("private"),
    PUBLIC("public");

    private final String bankTypeName;

    public static BankType getBankType(String bankTypeName) {
        return Arrays.stream(BankType.values())
                .filter(bankType -> bankType.getBankTypeName().equals(bankTypeName))
                .findFirst()
                .orElseThrow(() -> new BankTypeNotFoundException(String.format(
                        "Bank type %s is not accepted", bankTypeName)));
    }
}
