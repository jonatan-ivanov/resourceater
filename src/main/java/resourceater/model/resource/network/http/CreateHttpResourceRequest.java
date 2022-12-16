package resourceater.model.resource.network.http;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateHttpResourceRequest(Duration ttl) implements CreateRequest {
}
