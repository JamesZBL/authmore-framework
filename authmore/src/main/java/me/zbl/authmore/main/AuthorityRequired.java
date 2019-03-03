package me.zbl.authmore.main;

import java.lang.annotation.*;

/**
 * @author ZHENG BAO LE
 * @since 2019-02-27
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorityRequired {

    OAuthProperties.RequireTypes type() default OAuthProperties.RequireTypes.ANY;

    String[] value() default {};
}
