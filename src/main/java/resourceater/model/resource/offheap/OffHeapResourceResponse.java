package resourceater.model.resource.offheap;

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
class OffHeapResourceResponse extends Response {
    private final String resourceId;
    private final long size;
}
