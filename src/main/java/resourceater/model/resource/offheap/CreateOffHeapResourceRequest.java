package resourceater.model.resource.offheap;

import lombok.Value;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateOffHeapResourceRequest {
    private final String size;
}
