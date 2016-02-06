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
@Target(value={ElementType.FIELD,ElementType.TYPE})
@Retention( value = RetentionPolicy.RUNTIME )
public @interface UIDescription {
	String name();
	String description() ;
}
