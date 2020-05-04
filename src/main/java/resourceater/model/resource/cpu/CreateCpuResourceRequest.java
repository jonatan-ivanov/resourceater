package resourceater.model.resource.cpu;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateCpuResourceRequest implements CreateRequest {
    private final int size;
    private final Duration ttl;
}
