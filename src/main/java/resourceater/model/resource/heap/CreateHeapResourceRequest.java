package resourceater.model.resource.heap;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateHeapResourceRequest implements CreateRequest {
    private final String size;
    private final Duration ttl;
}
