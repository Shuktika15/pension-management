package dev.shuktika.pensionerdetail.entity;

import dev.shuktika.pensionerdetail.constraints.BirthDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pensioner {
    @Id
    @Min(value = 1000_0000_0000L, message = "Please put 12 digits valid aadhar number")
    @Max(value = 9999_9999_9999L, message = "Please put 12 digits valid aadhar number")
    private Long aadharNumber;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Date of birth is required")
    @BirthDate(message = "The person should be greater than or equal to 60 years of age")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Pan is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-z]{1}", flags = Pattern.Flag.UNICODE_CASE, message = "Please put valid pan")
    private String pan;

    @NotNull(message = "Last earned monthly salary is required")
    private Integer salaryEarned;

    @NotNull(message = "allowances is required")
    private Integer allowances;

    @Pattern(regexp = "^(family|self)$", message = "Pension type can only be family or self")
    @NotBlank(message = "Pension type(self/family) is required")
    private String pensionType;

    @Embedded
    private BankDetails bankDetails;
}

