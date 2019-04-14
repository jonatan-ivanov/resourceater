package resourceater.model.resource;

/**
 * @author Jonatan Ivanov
 */
public interface Resource {
    default String getId() {
        return String.valueOf(System.identityHashCode(this));
    }

    default Resource init() {
        return this;
    }

    default void destroy() {
        // noop
    }

    Response toResponse();
}
