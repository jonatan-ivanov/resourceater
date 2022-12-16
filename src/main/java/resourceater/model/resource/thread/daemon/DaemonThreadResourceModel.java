package resourceater.model.resource.thread.daemon;

import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class DaemonThreadResourceModel extends Model<DaemonThreadResource> {
    int size;

    public DaemonThreadResourceModel(DaemonThreadResource resource, int size) {
        super(resource);
        this.size = size;
    }
}
