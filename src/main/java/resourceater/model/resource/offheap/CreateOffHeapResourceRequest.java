package resourceater.model.resource.offheap;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateOffHeapResourceRequest(String size, Duration ttl) implements CreateRequest {
}
