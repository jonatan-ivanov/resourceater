package resourceater.model.resource.network.http;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateHttpResourceRequest implements CreateRequest {
    private final Duration ttl;
}
