package resourceater.model.resource.offheap;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateOffHeapResourceRequest implements CreateRequest {
    private final String size;
    private final Duration ttl;
}
