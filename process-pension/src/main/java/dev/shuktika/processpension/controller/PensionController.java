package dev.shuktika.processpension.controller;

import dev.shuktika.processpension.model.PensionDetail;
import dev.shuktika.processpension.model.ProcessPensionInput;
import dev.shuktika.processpension.service.PensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process-pension")
public class PensionController {
    private final PensionService pensionService;

    @PostMapping
    public ResponseEntity<PensionDetail> processPension(@RequestBody ProcessPensionInput processPensionInput) {
        return ResponseEntity.ok().body(pensionService.processPension(processPensionInput));
    }
}
