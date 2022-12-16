package resourceater.model.resource.network.http;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class HttpResourceModel extends Model<HttpResource> {
    String url;

    public HttpResourceModel(HttpResource resource, String url) {
        super(resource);
        this.url = url;
    }
}
