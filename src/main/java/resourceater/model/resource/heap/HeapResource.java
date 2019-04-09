package resourceater.model.resource.heap;

import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

/**
 * @author Jonatan Ivanov
 */
public class HeapResource implements Resource {
    private static final long MAX_SIZE = DataSize.ofGigabytes(2).toBytes();
    private final byte[] bytes;

    public HeapResource(HeapResourceRequest request) {
        this(DataSize.parse(request.getSize()));
    }

    private HeapResource(DataSize dataSize) {
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 2G"); // the size must fit into an int
        }

        this.bytes = new byte[(int) dataSize.toBytes()];
    }

    @Override
    public void destroy() {
        // GC will do this
    }

    @Override
    public Response toResponse() {
        return HeapResourceResponse.builder()
            .resourceId(this.getId())
            .size(this.bytes.length)
            .build();
    }
}
