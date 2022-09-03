package dev.shuktika.authorization.controller;

import dev.shuktika.authorization.model.PensionerDetails;
import dev.shuktika.authorization.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthorizationController {
    private final PensionerService pensionerService;
    @GetMapping("/authenticate")
    public ResponseEntity<PensionerDetails> authenticate() {
        return ResponseEntity.ok(pensionerService.getPensioner());
    }
}
