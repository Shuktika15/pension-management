package dev.shuktika.pensionerdetail.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        return LocalDate.now().getYear() - valueToValidate.getYear() >= 60;
    }
}
