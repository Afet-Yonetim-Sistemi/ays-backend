package org.ays.common.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * A custom validator implementation for the {@link EmailAddress} annotation.
 * Validates whether the provided email matches a specified set of rules
 * to ensure it's in a valid format.
 */
class EmailAddressValidator implements ConstraintValidator<EmailAddress, String> {

    @SuppressWarnings("java:S5998")
    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^(?!.*\\.{2})" +
                    "[\\p{Alnum}][\\p{Alnum}._%+\\-]*" +
                    "@" +
                    "(?!-)(?:[\\p{Alnum}]+(?<!-)\\.)+" +
                    "[\\p{Alpha}]{2,}$",
            Pattern.UNICODE_CHARACTER_CLASS
    );

    /**
     * Checks whether the given value is a valid email or not.
     * <p>Some valid emails are:</p>
     * <ul>
     *     <li>user@example.com</li>
     *     <li>john.doe123@example.co.uk</li>
     *     <li>admin_123@example.org</li>
     * </ul>
     *
     * <p>Some invalid emails are:</p>
     * <ul>
     *     <li>user@invalid</li>
     *     <li>user@invalid!.com</li>
     *     <li>u@ser@.com</li>
     *     <li>user@..com</li>
     *     <li>user</li>
     * </ul>
     *
     * @param email object to validate
     * @return true if the value is valid, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        if (!StringUtils.hasText(email)) {
            return true;
        }

        return EMAIL_REGEX.matcher(email).matches();
    }

}
