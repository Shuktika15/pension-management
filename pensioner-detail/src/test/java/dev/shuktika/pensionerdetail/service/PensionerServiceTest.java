package dev.shuktika.pensionerdetail.service;

import dev.shuktika.pensionerdetail.entity.Pensioner;
import dev.shuktika.pensionerdetail.exception.PensionerNotFoundException;
import dev.shuktika.pensionerdetail.repository.PensionerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class PensionerServiceTest {
    @Mock
    private PensionerRepository pensionerRepository;
    @InjectMocks
    private PensionerService underTest;

    @BeforeEach
    void setUp() {
//        this.pensionerService = new PensionerService(this.pensionerRepository);
    }

    @Test
    @DisplayName("Should save the pensioner successfully")
    void shouldSaveThePensionerSuccessfully() {
        //Given
        Pensioner expected = Pensioner.builder()
                .name("Ritam")
                .pan("28490498756")
                .build();

        //When
        Mockito.when(pensionerRepository.save(Mockito.any(Pensioner.class)))
                .thenReturn(expected);
        underTest.savePensioner(expected);
        //Then
        Mockito.verify(pensionerRepository, Mockito.atLeastOnce()).save(expected);
        Pensioner actual = underTest.savePensioner(expected);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should fetch pensioner successfully when aadhar number is correct")
    void shouldFetchPensionerSuccessfullyWhenAadharNumberIsCorrect() {
        // Given
        Pensioner expected = Pensioner.builder()
                .name("Suki")
                .pensionType("self")
                .build();
        Long aadharNumber = 234151234234L;

        // When
        Mockito.when(pensionerRepository.findByAadharNumber(Mockito.anyLong()))
                .thenReturn(Optional.of(expected));

        // Then
        Pensioner actual = underTest.fetchPensionerByAadharNumber(aadharNumber);
        Mockito.verify(pensionerRepository, Mockito.atLeastOnce()).findByAadharNumber(aadharNumber);
        Assertions.assertThat(actual).isEqualTo(expected);
        Assertions.assertThat(actual.getPensionType()).isEqualTo("self");
    }

    @Test
    @DisplayName("Should throw exception when aadhar number is incorrect")
    void shouldThrowExceptionWhenAadharNumberIsIncorrect() {
        //Given
        Pensioner expected = Pensioner.builder()
                .name("Tom")
                .pensionType("family")
                .build();
        Long aadharNumber = 234151234267L;
        String expectedMessage = String.format("Pensioner with Aadhar Number %s not found", aadharNumber);

        //When
        BDDMockito.given(pensionerRepository.findByAadharNumber(Mockito.anyLong()))
                .willThrow(PensionerNotFoundException.class);

        //Then
        Assertions.assertThatThrownBy(() -> underTest.fetchPensionerByAadharNumber(aadharNumber))
                .isInstanceOf(PensionerNotFoundException.class);
    }
}