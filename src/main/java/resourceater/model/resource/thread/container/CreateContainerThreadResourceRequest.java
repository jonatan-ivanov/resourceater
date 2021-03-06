package resourceater.model.resource.thread.container;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateContainerThreadResourceRequest implements CreateRequest {
    private final int size;
    private final Duration ttl;
}
