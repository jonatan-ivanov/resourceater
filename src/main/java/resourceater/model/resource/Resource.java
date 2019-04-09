package resourceater.model.resource;

/**
 * @author Jonatan Ivanov
 */
public interface Resource {
    default String getId() {
        return String.valueOf(System.identityHashCode(this));
    }

    void destroy();

    Response toResponse();
}
