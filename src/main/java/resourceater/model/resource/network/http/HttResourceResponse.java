package resourceater.model.resource.network.http;

import lombok.Builder;
import lombok.Getter;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
@Getter
@Builder
class HttResourceResponse implements Response {
    private final String resourceId;
    private final String url;
}
