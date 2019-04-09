package resourceater.model.resource.clazz;

import org.objectweb.asm.ClassWriter;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.V12;

/**
 * @author Jonatan Ivanov
 */
public class ClassResource implements Resource {
    private static final DynamicClassLoader CLASS_LOADER = new DynamicClassLoader();
    private final ClassWriter classWriter = new ClassWriter(0);
    private final List<Class<?>> classes = new ArrayList<>();

    public ClassResource(ClassResourceRequest request) {
        for (int i = 0; i < request.getSize(); i++) {
            classes.add(generateClass("gen", format("%s$%d", getId(), i)));
        }
    }

    private Class<?> generateClass(String pckg, String name) {
        classWriter.visit(V12, ACC_PUBLIC, format("%s/%s", pckg, name), null, "java/lang/Object", null);
        classWriter.visitEnd();
        return CLASS_LOADER.defineClass(format("%s.%s", pckg, name), classWriter.toByteArray());
    }

    @Override
    public void destroy() {
        // GC will do this
    }

    @Override
    public Response toResponse() {
        return ClassResourceResponse.builder()
            .resourceId(this.getId())
            .size(this.classes.size())
            .build();
    }

    private static class DynamicClassLoader extends ClassLoader { // because defineClass is protected
         Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}
