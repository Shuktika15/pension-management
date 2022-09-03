package dev.shuktika.authorization.service;

import dev.shuktika.authorization.client.PensionerClient;
import dev.shuktika.authorization.entity.Pensioner;
import dev.shuktika.authorization.exception.AadharMismatchException;
import dev.shuktika.authorization.exception.PensionerDetailServiceException;
import dev.shuktika.authorization.model.PensionerDetails;
import dev.shuktika.authorization.repository.PensionerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PensionerService implements UserDetailsService {
    private final PensionerRepository pensionerRepository;
    private final PasswordEncoder passwordEncoder;
    private final PensionerClient pensionerClient;

    public Pensioner addPensioner(Pensioner pensioner) {
        pensioner.setPassword(passwordEncoder.encode(pensioner.getPassword()));
        return pensionerRepository.save(pensioner);
    }

    public PensionerDetails getPensioner() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        var pensioner = pensionerRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> {
                    var errorMsg = String.format("PensionerService.getPensioner () :" +
                            "Pensioner not found with aadhar number %s", username);
                    log.error(errorMsg);
                    return new UsernameNotFoundException(errorMsg);
                });

        Long aadharNumber = pensioner.getAadharNumber();
        ResponseEntity<PensionerDetails> pensionerResponseEntity = pensionerClient.getPensioner(aadharNumber);
        PensionerDetails pensionerDetails = null;

        if (pensionerResponseEntity.getStatusCode().is2xxSuccessful() && pensionerResponseEntity.hasBody()) {
            pensionerDetails = pensionerResponseEntity.getBody();
        } else if (pensionerResponseEntity.getStatusCode().is4xxClientError()) {
            throw new AadharMismatchException(String.format("Aadhar number %s not found", aadharNumber));
        } else if (pensionerResponseEntity.getStatusCode().is5xxServerError()) {
            throw new PensionerDetailServiceException("Pensioner Detail Service internal server error");
        }

        return pensionerDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pensionerRepository.findById(Long.parseLong(username)).orElseThrow(() -> {
            var errMsg = String.format("Pensioner with Aadhar Number %s not found", username);
            log.error(errMsg);
            return new UsernameNotFoundException(errMsg);
        });
    }
}
