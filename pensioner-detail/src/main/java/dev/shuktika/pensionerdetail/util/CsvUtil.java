package dev.shuktika.pensionerdetail.util;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.shuktika.pensionerdetail.entity.BankDetails;
import dev.shuktika.pensionerdetail.entity.Pensioner;
import dev.shuktika.pensionerdetail.exception.ParsingCSVException;
import dev.shuktika.pensionerdetail.model.PensionerCsv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CsvUtil {
    @Value("classpath:pensioners.csv")
    private Resource resource;

    public List<Pensioner> readPensionerFromCsv() {
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            log.info("Reading pensioner data from pensioners.csv");
            return new CsvToBeanBuilder<PensionerCsv>(reader)
                    .withType(PensionerCsv.class)
                    .build()
                    .parse()
                    .stream()
                    .map(p -> Pensioner.builder()
                            .aadharNumber(p.getAadharNumber())
                            .name(p.getName())
                            .dateOfBirth(p.getDateOfBirth())
                            .pan(p.getPan())
                            .salaryEarned(p.getSalaryEarned())
                            .allowances(p.getAllowances())
                            .pensionType(p.getPensionType())
                            .bankDetails(
                                    BankDetails.builder()
                                            .bankName(p.getBankName())
                                            .accountNumber(p.getAccountNumber())
                                            .bankType(p.getBankType())
                                            .build()
                            )
                            .build()
                    )
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Problem reading csv : {}", e.getMessage());
            throw new ParsingCSVException(e.getMessage());
        }
    }
}
