package resourceater.model.resource.clazz;

import static java.lang.String.format;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.V12;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassWriter;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class ClassResource extends Resource<ClassResource> {
    private static final String PACKAGE_NAME = "gen";
    private static final DynamicClassLoader CLASS_LOADER = new DynamicClassLoader();
    private final ClassWriter classWriter = new ClassWriter(0);
    private final List<Class<?>> classes = new ArrayList<>();

    public ClassResource(CreateClassResourceRequest request) {
        super(request.getTtl());
        for (int i = 0; i < request.getSize(); i++) {
            classes.add(generateClass(format("%s$%d", getId(), i)));
        }
    }

    private Class<?> generateClass(String name) {
        classWriter.visit(V12, ACC_PUBLIC, format("%s/%s", PACKAGE_NAME, name), null, "java/lang/Object", null);
        classWriter.visitEnd();
        return CLASS_LOADER.defineClass(format("%s.%s", PACKAGE_NAME, name), classWriter.toByteArray());
    }

    @Override
    public Model<ClassResource> toModel() {
        return new ClassResourceModel(this, this.classes.size());
    }

    private static class DynamicClassLoader extends ClassLoader { // because defineClass is protected
         Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}
