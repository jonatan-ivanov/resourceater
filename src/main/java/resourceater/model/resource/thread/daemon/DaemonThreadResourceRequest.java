package resourceater.model.resource.thread.daemon;

import lombok.Value;
import resourceater.model.resource.Request;

/**
 * @author Jonatan Ivanov
 */
@Value
public class DaemonThreadResourceRequest implements Request {
    private final int size;
}
