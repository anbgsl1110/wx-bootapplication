package com.modules.common.annotation;

import java.lang.annotation.*;

/**
 * @author chenlingl
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreSecurity {
}
