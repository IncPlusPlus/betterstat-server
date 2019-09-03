package io.github.incplusplus.thermostat.validation;

import io.github.incplusplus.thermostat.web.dto.ClientDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final ClientDto user = (ClientDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
