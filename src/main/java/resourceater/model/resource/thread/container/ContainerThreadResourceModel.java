package resourceater.model.resource.thread.container;

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
public class ContainerThreadResourceModel extends Model<ContainerThreadResource> {
    private final String id;
    private final int size;
}
