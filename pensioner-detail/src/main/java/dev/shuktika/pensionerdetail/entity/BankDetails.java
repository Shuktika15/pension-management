package dev.shuktika.pensionerdetail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotNull(message = "accountNumber is required")
    private Long accountNumber;

    @Pattern(regexp = "^(private|public)$", message = "Bank type can only be private or public")
    @NotBlank(message = "Bank type(public/private) is required")
    private String bankType;
}