package com.banglalink.toffee.annotation;

import com.banglalink.toffee.validator.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.banglalink.toffee.models.schema.ConstantUtil.INVALID_ENUM_MESSAGE;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default INVALID_ENUM_MESSAGE;
    String paramName() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
