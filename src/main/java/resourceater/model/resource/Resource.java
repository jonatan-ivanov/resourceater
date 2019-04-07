package resourceater.model.resource;

/**
 * @author Jonatan Ivanov
 */
@SuppressWarnings("unused")
public interface Resource {
    default String getId() {
        return String.valueOf(System.identityHashCode(this));
    }

    long getSize();
    void destroy();
}
