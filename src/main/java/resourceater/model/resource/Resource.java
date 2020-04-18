package resourceater.model.resource;

/**
 * @author Jonatan Ivanov
 */
public interface Resource<R extends Resource<R>> {
    default String getId() {
        return String.valueOf(System.identityHashCode(this));
    }

    default void saved() {
        // noop
    }

    default void destroy() {
        // noop
    }

    Model<R> toModel();
}
