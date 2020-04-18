package resourceater.model.resource.heap;

import lombok.Value;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateHeapResourceRequest {
    private final String size;
}
