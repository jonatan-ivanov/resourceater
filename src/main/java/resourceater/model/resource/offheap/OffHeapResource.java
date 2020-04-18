package resourceater.model.resource.offheap;

import java.nio.ByteBuffer;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class OffHeapResource implements Resource<OffHeapResource> {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private final ByteBuffer byteBuffer;

    public OffHeapResource(CreateOffHeapResourceRequest request) {
        this(DataSize.parse(request.getSize()));
    }

    private OffHeapResource(DataSize dataSize) {
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

        this.byteBuffer = ByteBuffer.allocateDirect((int) dataSize.toBytes());
    }

    @Override
    public Model<OffHeapResource> toModel() {
        return OffHeapResourceModel.builder()
            .id(this.getId())
            .size(this.byteBuffer.capacity())
            .build();
    }
}
