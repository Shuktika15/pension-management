package dev.shuktika.processpension.client;

import dev.shuktika.processpension.model.Pensioner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "PENSIONER-DETAIL")
public interface PensionerClient {
    @GetMapping("/pensionerDetail/pensionDetailsByAadhar")
    ResponseEntity<Pensioner> getPensioner(@RequestParam("aadharNumber") Long aadharNumber);
}
