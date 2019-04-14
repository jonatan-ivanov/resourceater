package resourceater.model.resource.network.http;

import lombok.Builder;
import lombok.Value;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
class HttResourceResponse implements Response {
    private final String resourceId;
    private final String url;
}
