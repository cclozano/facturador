package com.aurora.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentificacionValidator.class)
@Documented
public @interface Identificacion {
    String message() default "{com.aurora.vaadin.util.reports.util.Identificacion}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
