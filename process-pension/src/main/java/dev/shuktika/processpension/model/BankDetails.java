package dev.shuktika.processpension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    private String bankName;
    private Long accountNumber;
    private String bankType;
}