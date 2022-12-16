package resourceater.model.resource.offheap;

import java.nio.ByteBuffer;
import java.time.Duration;

import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

import org.springframework.util.unit.DataSize;

/**
 * @author Jonatan Ivanov
 */
public class OffHeapResource extends Resource<OffHeapResource> {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private final ByteBuffer byteBuffer;

    public OffHeapResource(CreateOffHeapResourceRequest request) {
        this(DataSize.parse(request.size()), request.ttl());
    }

    private OffHeapResource(DataSize dataSize, Duration ttl) {
        super(ttl);
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

        this.byteBuffer = ByteBuffer.allocateDirect((int) dataSize.toBytes());
    }

    @Override
    public Model<OffHeapResource> toModel() {
        return new OffHeapResourceModel(this, this.byteBuffer.capacity());
    }
}
