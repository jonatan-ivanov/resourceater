package resourceater.model.resource.heap;

import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class HeapResource implements Resource<HeapResource> {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private final byte[] bytes;

    public HeapResource(CreateHeapResourceRequest request) {
        this(DataSize.parse(request.getSize()));
    }

    private HeapResource(DataSize dataSize) {
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

        this.bytes = new byte[(int) dataSize.toBytes()];
    }

    @Override
    public Model<HeapResource> toModel() {
        return HeapResourceModel.builder()
            .id(this.getId())
            .size(this.bytes.length)
            .build();
    }
}
