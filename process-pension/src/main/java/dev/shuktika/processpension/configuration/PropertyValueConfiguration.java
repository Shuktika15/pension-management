package dev.shuktika.processpension.configuration;

import dev.shuktika.processpension.model.BankType;
import dev.shuktika.processpension.model.PensionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "pension-processing")
@NoArgsConstructor
@Setter
@Getter
public class PropertyValueConfiguration {
    private Map<String, Integer> bankCharges;
    private Map<String, Double> pension;

    public Integer getBankCharges(BankType bankType) {
        return bankCharges.get(bankType.getBankTypeName());
    }

    public Double getPension(PensionType pensionType) {
        return pension.get(pensionType.getPensionTypeName());
    }
}
