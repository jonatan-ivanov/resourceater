package resourceater.model.resource.clazz;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateClassResourceRequest implements CreateRequest {
    private final int size;
    private final Duration ttl;
}
