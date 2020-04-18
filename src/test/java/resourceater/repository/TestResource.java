package resourceater.repository;

import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

public class TestResource implements Resource<TestResource> {
    @Override
    public Model<TestResource> toModel() {
        return null;
    }
}
