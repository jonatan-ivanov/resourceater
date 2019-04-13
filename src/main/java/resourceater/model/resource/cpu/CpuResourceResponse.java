package resourceater.model.resource.cpu;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
class CpuResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
