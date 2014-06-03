package vazkii.tinkerer.common.core.helper;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Katrina on 18/03/14.
 */
public class ReflectionHelper {
    public static <T> T call(Object instance, String methodName, Object... args) {
        return call(instance.getClass(), instance, ArrayUtils.toArray(methodName), args);
    }
    public static <T> T call(Object instance, String[] methodNames, Object... args) {
        return call(instance.getClass(), instance, methodNames, args);
    }
    @SuppressWarnings("unchecked")
    public static <T> T call(Class<?> klazz, Object instance, String[] methodNames, Object... args) {
        Method m = getMethod(klazz, methodNames, args);
        Preconditions.checkNotNull(m, "Method %s not found", Arrays.toString(methodNames));

        m.setAccessible(true);
        try {
            return (T)m.invoke(instance, args);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
    public static Method getMethod(Class<?> klazz, String[] methodNames, Object... args) {
        if (klazz == null) return null;
        Class<?> argTypes[] = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            final Object arg = args[i];
            argTypes[i] = arg.getClass();
        }

        for (String name : methodNames) {
            Method result = getDeclaredMethod(klazz, name, argTypes);
            if (result != null) return result;
        }
        return null;
    }
    public static Method getMethod(Class<?> klazz, String[] methodNames, Class<?>... types) {
        if (klazz == null) return null;
        for (String name : methodNames) {
            Method result = getDeclaredMethod(klazz, name, types);
            if (result != null) return result;
        }
        return null;
    }

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>[] argsTypes) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(name, argsTypes);
            } catch (NoSuchMethodException e) {} catch (Exception e) {
                throw Throwables.propagate(e);
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}
