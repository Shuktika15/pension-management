package dev.shuktika.processpension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pensioner {
    private Long aadharNumber;
    private String name;
    private LocalDate dateOfBirth;
    private String pan;
    private Integer salaryEarned;
    private Integer allowances;
    private String pensionType;
    private BankDetails bankDetails;
}

