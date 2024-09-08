package com.yangjl.bigevent.annotation;

import com.yangjl.bigevent.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    String message() default "状态只能是'草稿'或者'已发布'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
