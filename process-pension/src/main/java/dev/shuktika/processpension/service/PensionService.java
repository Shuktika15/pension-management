package dev.shuktika.processpension.service;

import dev.shuktika.processpension.client.PensionerClient;
import dev.shuktika.processpension.configuration.PropertyValueConfiguration;
import dev.shuktika.processpension.exception.AadharMismatchException;
import dev.shuktika.processpension.exception.PensionerDetailServiceException;
import dev.shuktika.processpension.model.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PensionService {
    private final PensionerClient pensionerClient;
    private final PropertyValueConfiguration propertyValueConfiguration;

    private PensionDetail calculatePension(Pensioner pensioner) {
        var pensionType = PensionType.getPensionType(pensioner.getPensionType());
        var bankType = BankType.getBankType(pensioner.getBankDetails().getBankType());
        var salary = Double.valueOf(pensioner.getSalaryEarned());
        var allowances = Double.valueOf(pensioner.getAllowances());
        Double pensionPercentage = propertyValueConfiguration.getPension(pensionType);
        Integer bankServiceCharge = propertyValueConfiguration.getBankCharges(bankType);
        double pensionAmount = salary * pensionPercentage + allowances;

        return PensionDetail.builder()
                .pensionAmount(pensionAmount)
                .bankServiceCharge(bankServiceCharge)
                .build();
    }

    public PensionDetail processPension(ProcessPensionInput processPensionInput) {
        Long aadharNumber = processPensionInput.getAadharNumber();

        try {
            ResponseEntity<Pensioner> pensionerResponseEntity = pensionerClient.getPensioner(aadharNumber);
            PensionDetail pensionDetail = null;

            if (pensionerResponseEntity.getStatusCode().is2xxSuccessful() && pensionerResponseEntity.hasBody()) {
                var pensioner = pensionerResponseEntity.getBody();
                pensionDetail = calculatePension(Objects.requireNonNull(pensioner));
            } else if (pensionerResponseEntity.getStatusCode().is4xxClientError()) {
                throw new AadharMismatchException(String.format("Aadhar number %s not found", aadharNumber));
            } else if (pensionerResponseEntity.getStatusCode().is5xxServerError()) {
                throw new PensionerDetailServiceException("Pensioner Detail Service internal server error");
            }

            return pensionDetail;
        } catch (FeignException e) {
            var httpStatus = HttpStatus.resolve(e.status());

            if (httpStatus != null) {
                if (httpStatus.is4xxClientError()) {
                    throw new AadharMismatchException(String.format("Aadhar number %s not found", aadharNumber));
                } else if (httpStatus.is5xxServerError()) {
                    throw new PensionerDetailServiceException("Pensioner Detail Service internal server error");
                }
            }

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Process Pension Service internal servier error");
        }
    }
}
