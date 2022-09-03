package dev.shuktika.processpension.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PensionDetail {
    private Double pensionAmount;
    private Integer bankServiceCharge;
}
