package resourceater.model.resource.thread.daemon;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateDaemonThreadResourceRequest implements CreateRequest {
    private final int size;
    private final Duration ttl;
}
