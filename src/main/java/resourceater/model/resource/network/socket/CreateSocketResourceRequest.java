package resourceater.model.resource.network.socket;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateSocketResourceRequest(int size, Duration ttl) implements CreateRequest {
}
