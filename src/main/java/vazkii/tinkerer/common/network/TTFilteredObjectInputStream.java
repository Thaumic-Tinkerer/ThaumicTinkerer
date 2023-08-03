package vazkii.tinkerer.common.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TTFilteredObjectInputStream extends ObjectInputStream {
    public TTFilteredObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    protected TTFilteredObjectInputStream() throws IOException, SecurityException {
    }
    static List<String> allowedClasses=new ArrayList<String>(Arrays.asList("boolean",
            "byte",
            "char",
            "short",
            "int",
            "long",
            "float",
            "double",
            "void"));

    static List<String> allowedPackages=new ArrayList<String>(Arrays.asList(
            "java.util.",
            "java.lang.",
            "vazkii.tinkerer."
    ));

    private boolean canDo(String className) {
        boolean endsInSemicolon = className.endsWith(";");
        if (className.startsWith("[L") && endsInSemicolon) {
            className = className.substring(2, className.length() - 1);
        } else if (className.startsWith("L") && endsInSemicolon) {
            className = className.substring(1, className.length() - 1);
        }

        if(allowedClasses.contains(className))
            return true;

        for (String allowedPackage : allowedPackages) {
            if (className.startsWith(allowedPackage)) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String className=desc.getName();
        if(canDo(className))
            return super.resolveClass(desc);
        else
            throw new ClassNotFoundException("Class " + desc.getName() + " is not allowed to be deserialized");

    }
}
