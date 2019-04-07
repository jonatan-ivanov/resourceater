package resourceater.model.resource.heap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class HeapResource implements Resource {
    private static final long MAX_SIZE = DataSize.ofGigabytes(2).toBytes();
    @JsonIgnore private final byte[] bytes;

    @JsonCreator public HeapResource(@JsonProperty("size") String size) {
        this(DataSize.parse(size));
    }

    private HeapResource(DataSize dataSize) {
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 2G"); // the size must fit into an int
        }

        this.bytes = new byte[(int) dataSize.toBytes()];
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public void destroy() {
        // GC will do this
    }
}
