package resourceater.model.resource.cpu;

import lombok.Value;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateCpuResourceRequest {
    private final int size;
}
