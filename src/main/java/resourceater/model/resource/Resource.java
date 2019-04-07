package resourceater.model.resource;

/**
 * @author Jonatan Ivanov
 */
public interface Resource {
    default int getId() {
        return System.identityHashCode(this);
    }

    int getSize();
}
