package resourceater.model.resource.thread.daemon;

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
public class DaemonThreadResourceModel extends Model<DaemonThreadResource> {
    private final String id;
    private final int size;
}
