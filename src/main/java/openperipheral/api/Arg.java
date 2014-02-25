package openperipheral.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface Arg {
    public static final String DEFAULT_NAME = "[none set]";

    String name() default DEFAULT_NAME;

    String description() default "";

    LuaType type();

    boolean isNullable() default false;
}