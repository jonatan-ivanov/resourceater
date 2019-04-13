package resourceater.model.resource.thread;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
class ThreadResourceResponse implements Response {
    private final String resourceId;
    private final int size;
}
