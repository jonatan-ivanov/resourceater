package resourceater.model.resource;

import org.springframework.hateoas.ResourceSupport;

public abstract class Response extends ResourceSupport {
    public abstract String getResourceId();
}
