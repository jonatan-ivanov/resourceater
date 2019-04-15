package resourceater.model.resource.heap;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
@EqualsAndHashCode(callSuper=true)
class HeapResourceResponse extends Response {
    private final String resourceId;
    private final long size;
}
