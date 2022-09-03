package dev.shuktika.authorization.client;

import dev.shuktika.authorization.model.PensionerDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "PENSIONER-DETAIL")
public interface PensionerClient {
    @GetMapping("/pensionerDetail/pensionDetailsByAadhar")
    ResponseEntity<PensionerDetails> getPensioner(@RequestParam("aadharNumber") Long aadharNumber);
}