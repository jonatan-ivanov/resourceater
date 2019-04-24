package resourceater.model.resource.offheap;

import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

import java.nio.ByteBuffer;

/**
 * @author Jonatan Ivanov
 */
public class OffHeapResource implements Resource {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private final ByteBuffer byteBuffer;

    public OffHeapResource(OffHeapResourceRequest request) {
        this(DataSize.parse(request.getSize()));
    }

    private OffHeapResource(DataSize dataSize) {
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

        this.byteBuffer = ByteBuffer.allocateDirect((int) dataSize.toBytes());
    }

    @Override
    public Response toResponse() {
        return OffHeapResourceResponse.builder()
            .resourceId(this.getId())
            .size(this.byteBuffer.capacity())
            .build();
    }
}
