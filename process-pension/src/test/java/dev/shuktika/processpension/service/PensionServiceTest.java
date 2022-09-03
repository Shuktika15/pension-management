package dev.shuktika.processpension.service;

import dev.shuktika.processpension.client.PensionerClient;
import dev.shuktika.processpension.configuration.PropertyValueConfiguration;
import dev.shuktika.processpension.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PensionServiceTest {
    @Mock
    private PensionerClient pensionerClient;
    @Mock
    private PropertyValueConfiguration propertyValueConfiguration;
    @InjectMocks
    private PensionService underTest;

    @Test
    @DisplayName("Should calculate pension successfully when Aadhar Number is correct")
    void shouldCalculatePensionSuccessfullyWhenAadharNumberIsCorrect() {
        //Given
        Long aadharNumber = 234151234234L;
        Pensioner pensioner = Pensioner.builder()
                .salaryEarned(90000)
                .allowances(20000)
                .pensionType("self")
                .bankDetails(
                        BankDetails.builder()
                                .bankType("private")
                                .build()
                )
                .build();
        ProcessPensionInput processPensionInput = ProcessPensionInput.builder()
                .aadharNumber(aadharNumber)
                .build();
        PensionDetail expected = PensionDetail.builder()
                .pensionAmount(65000.0)
                .bankServiceCharge(500)
                .build();

        //When
        Mockito.when(pensionerClient.getPensioner(Mockito.anyLong()))
                .thenReturn(ResponseEntity.ok(pensioner));
        Mockito.when(propertyValueConfiguration.getPension(PensionType.getPensionType("self")))
                .thenReturn(0.5);
        Mockito.when(propertyValueConfiguration.getBankCharges(BankType.getBankType("private")))
                .thenReturn(500);

        //Then
        PensionDetail actual = underTest.processPension(processPensionInput);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}