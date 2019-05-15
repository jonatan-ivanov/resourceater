package resourceater.model.resource.network.http;

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
class HttpResourceResponse extends Response {
    private final String resourceId;
    private final String url;
}
