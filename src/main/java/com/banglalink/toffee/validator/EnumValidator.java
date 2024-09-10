package com.banglalink.toffee.validator;

import com.banglalink.toffee.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private String paramName;
    private String message;
    private List<String> enumValues;

    @Override
    public void initialize(ValidEnum annotationObject) {
        this.enumValues = new ArrayList<>();
        this.paramName = annotationObject.paramName();
        this.message = annotationObject.message();

        Class<? extends Enum<?>> enumClass = annotationObject.enumClass();
        for (var e : enumClass.getEnumConstants()) {
            enumValues.add(e.name());
        }
    }

    @Override
    public boolean isValid(String target, ConstraintValidatorContext context) {
        boolean valid = enumValues.contains(target);

        if (!valid) {
            String validationError = String.format(message, paramName, String.join(", ", enumValues));
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(validationError).addConstraintViolation();
        }

        return valid;
    }
}
