package resourceater.model.resource.network.http;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@Builder
@EqualsAndHashCode(callSuper=true)
public class HttpResourceModel extends Model<HttpResource> {
    private final String id;
    private final String url;
}
