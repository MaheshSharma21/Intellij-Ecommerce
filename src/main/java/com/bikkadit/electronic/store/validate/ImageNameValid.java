package com.bikkadit.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    //default error message
    String message() default "Invalid Image name";

    //represent group of constraints
    Class<?>[] groups() default {};

    //additional Information about annotations
    Class<? extends Payload>[] payload() default {};


}
