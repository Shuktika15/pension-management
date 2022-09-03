package dev.shuktika.pensionerdetail.controller;

import dev.shuktika.pensionerdetail.entity.Pensioner;
import dev.shuktika.pensionerdetail.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pensionerDetail")
@RequiredArgsConstructor
public class PensionerController {
    private final PensionerService pensionerService;

    @GetMapping("/pensionDetailsByAadhar")
    public ResponseEntity<Pensioner> fetchPersonByAadharNumber(@RequestParam("aadharNumber") Long aadharNumber) {
        return new ResponseEntity<>(pensionerService.fetchPensionerByAadharNumber(aadharNumber), HttpStatus.OK);
    }
}
