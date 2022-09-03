package dev.shuktika.authorization.service;

import dev.shuktika.authorization.client.PensionerClient;
import dev.shuktika.authorization.entity.Pensioner;
import dev.shuktika.authorization.exception.AadharMismatchException;
import dev.shuktika.authorization.exception.PensionerDetailServiceException;
import dev.shuktika.authorization.model.PensionerDetails;
import dev.shuktika.authorization.repository.PensionerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PensionerServiceTest {
    @Mock
    private PensionerRepository pensionerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PensionerClient pensionerClient;
    @InjectMocks
    private PensionerService underTest;

    @Test
    void addPensioner() {
        //Given
        Pensioner expected = Pensioner.builder()
                .password("43933")
                .build();

        //When
        Mockito.when(passwordEncoder.encode(expected.getPassword()))
                .thenReturn("43933");
        Mockito.when(pensionerRepository.save(Mockito.any(Pensioner.class)))
                .thenReturn(expected);

        //Then
        Pensioner actual = underTest.addPensioner(expected);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPensioner() {
        //Given
        Long aadharNumber = 234151234234L;
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(aadharNumber.toString(), null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        PensionerDetails pensionerDetail = PensionerDetails.builder()
                .aadharNumber(aadharNumber)
                .build();
        Pensioner expected = Pensioner.builder()
                .aadharNumber(aadharNumber  )
                .password("43933")
                .build();
        ResponseEntity<PensionerDetails> pensionerDetailsResponseEntity = ResponseEntity.ok(pensionerDetail);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(Optional.of(expected));
        Mockito.when(pensionerClient.getPensioner(234151234234L))
                .thenReturn(pensionerDetailsResponseEntity);

        //Then
        PensionerDetails actual = underTest.getPensioner();
        Assertions.assertThat(actual.getAadharNumber()).isEqualTo(expected.getAadharNumber());
    }

    @Test
    @DisplayName("Should return pensioner details if authenticated with user details")
    void getPensionerFromUserDetails() {
        //Given
        Long aadharNumber = 234151234234L;
        Pensioner expected = Pensioner.builder()
                .aadharNumber(aadharNumber)
                .password("43933")
                .build();
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(expected, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        PensionerDetails pensionerDetail = PensionerDetails.builder()
                .aadharNumber(aadharNumber)
                .build();
        ResponseEntity<PensionerDetails> pensionerDetailsResponseEntity = ResponseEntity.ok(pensionerDetail);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(Optional.of(expected));
        Mockito.when(pensionerClient.getPensioner(234151234234L))
                .thenReturn(pensionerDetailsResponseEntity);

        //Then
        PensionerDetails actual = underTest.getPensioner();
        Assertions.assertThat(actual.getAadharNumber()).isEqualTo(expected.getAadharNumber());
    }

    @Test
    @DisplayName("Should throw exception when pensioner with aadhar number not found")
    void shouldThrowExceptionWhenPensionerWithAadharNumberNotFound() {
        // Given
        Long aadharNumber = 234151234234L;
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(aadharNumber, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Optional<Pensioner> optionalPensioner = Optional.empty();
        String expectedErrorMsg = String.format("PensionerService.getPensioner () :" +
                "Pensioner not found with aadhar number %s", aadharNumber);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(optionalPensioner);

        // Then
        Assertions.assertThatThrownBy(() -> {
                    underTest.getPensioner();
                }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(expectedErrorMsg);
    }

    @Test
    @DisplayName("Should throw exception when aadhar number not found")
    void shouldThrowExceptionWhenAadharNumberNotFound() {
        // Given
        Long aadharNumber = 234151234234L;
        Pensioner pensioner = Pensioner.builder()
                .aadharNumber(aadharNumber)
                .password("43933")
                .build();
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(aadharNumber, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Optional<Pensioner> optionalPensioner = Optional.of(pensioner);
        String expectedErrorMsg = String.format("Aadhar number %s not found", aadharNumber);
        ResponseEntity<PensionerDetails> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(optionalPensioner);
        Mockito.when(pensionerClient.getPensioner(aadharNumber))
                .thenReturn(responseEntity);


        //Then
        Assertions.assertThatThrownBy(() -> {
                    underTest.getPensioner();
                }).isInstanceOf(AadharMismatchException.class)
                .hasMessage(expectedErrorMsg);
    }

    @Test
    @DisplayName("Should throw exception when Pensioner Detail Service internal server error occurs")
    void shouldThrowExceptionWhenPensionerDetailServiceInternalServerErrorOccurs() {
        // Given
        Long aadharNumber = 234151234234L;
        Pensioner pensioner = Pensioner.builder()
                .aadharNumber(aadharNumber)
                .password("43933")
                .build();
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(aadharNumber, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Optional<Pensioner> optionalPensioner = Optional.of(pensioner);
        String expectedErrorMsg = "Pensioner Detail Service internal server error";
        ResponseEntity<PensionerDetails> responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(optionalPensioner);
        Mockito.when(pensionerClient.getPensioner(aadharNumber))
                .thenReturn(responseEntity);


        //Then
        Assertions.assertThatThrownBy(() -> underTest.getPensioner())
                .isInstanceOf(PensionerDetailServiceException.class)
                .hasMessage(expectedErrorMsg);
    }

    @Test
    void loadUserByUsername() {
        //Given
        Pensioner expected = Pensioner.builder()
                .aadharNumber(234151234234L)
                .password("43933")
                .build();
        //When
        Mockito.when(pensionerRepository.findById(expected.getAadharNumber()))
                .thenReturn(Optional.of(expected));
        //Then
        UserDetails actual = underTest.loadUserByUsername(expected.getUsername());
        Assertions.assertThat(actual).isNotNull();
        Assertions.assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
    }

    @Test
    @DisplayName("loadByUserName Should throw exception when pensioner with aadhar number not found")
    void LoadByUserNameShouldThrowExceptionWhenPensionerWithAadharNumberNotFound() {
        // Given
        Long aadharNumber = 234151234234L;
        String username = aadharNumber.toString();
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(aadharNumber, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        Optional<Pensioner> optionalPensioner = Optional.empty();
        String expectedErrorMsg = String.format("Pensioner with Aadhar Number %s not found", username);

        //When
        Mockito.when(pensionerRepository.findById(aadharNumber))
                .thenReturn(optionalPensioner);

        // Then
        Assertions.assertThatThrownBy(() -> {
                    underTest.loadUserByUsername(username);
                }).isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(expectedErrorMsg);
    }
}