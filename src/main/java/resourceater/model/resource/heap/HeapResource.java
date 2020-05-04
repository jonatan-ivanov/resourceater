package resourceater.model.resource.heap;

import java.time.Duration;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class HeapResource extends Resource<HeapResource> {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private final byte[] bytes;

    public HeapResource(CreateHeapResourceRequest request) {
        this(DataSize.parse(request.getSize()), request.getTtl());
    }

    private HeapResource(DataSize dataSize, Duration ttl) {
        super(ttl);
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

        this.bytes = new byte[(int) dataSize.toBytes()];
    }

    @Override
    public Model<HeapResource> toModel() {
        return new HeapResourceModel(this, this.bytes.length);
    }
}
