package guru.springframework.security.passwordValidator;

import com.google.common.base.Joiner;
import edu.vt.middleware.password.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 20),
                new DigitCharacterRule(1),
                new LowercaseCharacterRule(1),
                new UppercaseCharacterRule(1),
                new NonAlphanumericCharacterRule(1),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(new Password(password)));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}
