package resourceater.model.resource.network.socket;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class SocketResourceModel extends Model<SocketResource> {
    private final int size;
    private final List<Integer> ports;

    public SocketResourceModel(SocketResource resource, int size, List<Integer> ports) {
        super(resource);
        this.size = size;
        this.ports = ports;
    }
}
