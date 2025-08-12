    package com.example.demo.domain.validation;

    import jakarta.validation.ConstraintValidator;
    import jakarta.validation.ConstraintValidatorContext;

    public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

        private static final String SPECIALS = "!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?`~|\\\\";

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;

            boolean hasLetter  = value.matches(".*[A-Za-z].*");
            boolean hasDigit   = value.matches(".*\\d.*");
            boolean hasSpecial = value.matches(".*[" + SPECIALS + "].*");
            int len = value.length();

            // ✅ 문자+숫자+특수 모두 포함 AND 길이 10~16
            boolean ok = hasLetter && hasDigit && hasSpecial && len >= 10 && len <= 16;
            if (ok) return true;

            context.disableDefaultConstraintViolation();
            if (len < 10 || len > 16) context.buildConstraintViolationWithTemplate("{password.length}").addConstraintViolation();
            if (!hasLetter)  context.buildConstraintViolationWithTemplate("{password.letter}").addConstraintViolation();
            if (!hasDigit)   context.buildConstraintViolationWithTemplate("{password.digit}").addConstraintViolation();
            if (!hasSpecial) context.buildConstraintViolationWithTemplate("{password.special}").addConstraintViolation();
            return false;
        }
    }