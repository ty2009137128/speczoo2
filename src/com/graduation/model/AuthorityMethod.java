package com.graduation.model;

import java.lang.annotation.*;

/**
 * Created by Stephen on 5/22/2014.
 */

//Only use on method.
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityMethod {
    AuthorityType[] authorityTypes();

    ResultType resultType() default ResultType.PAGE;
}
