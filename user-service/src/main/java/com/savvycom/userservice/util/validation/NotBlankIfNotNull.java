package com.savvycom.userservice.util.validation;

import com.savvycom.userservice.util.validation.validator.EmailValidator;
import com.savvycom.userservice.util.validation.validator.NotBlankIfNotNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankIfNotNullValidator.class)
public @interface NotBlankIfNotNull {
    String message() default "must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
