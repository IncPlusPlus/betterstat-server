package io.github.incplusplus.thermostat.validation;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static org.passay.IllegalSequenceRule.DEFAULT_SEQUENCE_LENGTH;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(final ValidPassword arg0) {

    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // @formatter:off
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
            new CharacterCharacteristicsRule(1,
                    new CharacterRule(EnglishCharacterData.UpperCase,1),
                    new CharacterRule(EnglishCharacterData.Digit,1),
                    new CharacterRule(EnglishCharacterData.Special,1)
            ),
            new IllegalSequenceRule(EnglishSequenceData.Numerical,DEFAULT_SEQUENCE_LENGTH,false),
            new IllegalSequenceRule(EnglishSequenceData.Alphabetical,DEFAULT_SEQUENCE_LENGTH,false),
            new IllegalSequenceRule(EnglishSequenceData.USQwerty,DEFAULT_SEQUENCE_LENGTH,false),
            new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }

}
