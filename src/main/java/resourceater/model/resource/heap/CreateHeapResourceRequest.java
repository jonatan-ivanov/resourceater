package resourceater.model.resource.heap;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateHeapResourceRequest(String size, Duration ttl) implements CreateRequest {
}
