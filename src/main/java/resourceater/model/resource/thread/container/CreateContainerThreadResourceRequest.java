package resourceater.model.resource.thread.container;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateContainerThreadResourceRequest(int size, Duration ttl) implements CreateRequest {
}
