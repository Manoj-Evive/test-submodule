package com.evive.services.annotations;

import com.evive.enums.TestCaseStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Manual {
    TestCaseStatus status() default TestCaseStatus.SKIPPED;
    String message() default "";
}