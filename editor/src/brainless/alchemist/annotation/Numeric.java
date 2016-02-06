package brainless.alchemist.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 */

/**
 * @author meltzow
 * 
 *
 */
@Target(value={ElementType.FIELD})
@Retention( value = RetentionPolicy.RUNTIME )
public @interface Numeric {
	int max() default Integer.MIN_VALUE;
	int min() default 0;
}
