package resourceater.model.resource.file;

import java.time.Duration;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateFileResourceRequest implements CreateRequest {
    private final String size;
    private final Duration ttl;
}
