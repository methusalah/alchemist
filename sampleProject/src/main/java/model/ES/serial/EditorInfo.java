package model.ES.serial;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EditorInfo {
	public String info() default "";
	public String UIname() default "";
}
