package resourceater.model.resource.network.socket;

import java.util.List;
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
public class SocketResourceModel extends Model<SocketResource> {
    private final String id;
    private final int size;
    private final List<Integer> ports;
}
