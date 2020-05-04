package resourceater.repository;

import java.time.Duration;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class TestResource extends Resource<TestResource> {
    public TestResource() {
        this(Duration.ZERO);
    }

    public TestResource(Duration ttl) {
        super(ttl);
    }

    @Override
    public Model<TestResource> toModel() {
        return null;
    }
}
