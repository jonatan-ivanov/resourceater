package resourceater.model.resource.thread.container;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class ContainerThreadResourceModel extends Model<ContainerThreadResource> {
    private final int size;

    public ContainerThreadResourceModel(ContainerThreadResource resource, int size) {
        super(resource);
        this.size = size;
    }
}
