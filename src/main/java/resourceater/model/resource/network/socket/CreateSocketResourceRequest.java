package resourceater.model.resource.network.socket;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateSocketResourceRequest implements CreateRequest {
    private final int size;
    private final Duration ttl;
}
