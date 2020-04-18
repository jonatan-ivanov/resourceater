package resourceater.model.resource.thread.daemon;

import lombok.Value;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateDaemonThreadResourceRequest {
    private final int size;
}
