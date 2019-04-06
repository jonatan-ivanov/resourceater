package resourceater.model.resource.heap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.util.unit.DataSize;

public class HeapResource {
    private static final long MAX_SIZE = DataSize.ofGigabytes(2).toBytes();

    @Getter private final int id;
    @JsonIgnore private final byte[] bytes;

    @JsonCreator
    public HeapResource(@JsonProperty("size") String size) {
        this(parseAndValidate(size));
    }

    private HeapResource(DataSize dataSize) {
        this.id = System.identityHashCode(this);
        this.bytes = new byte[(int) dataSize.toBytes()];
    }

    private static DataSize parseAndValidate(String size) {
        DataSize dataSize = DataSize.parse(size);
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 2G");
        }

        return dataSize;
    }

    public int getSize() {
        return bytes.length;
    }
}
