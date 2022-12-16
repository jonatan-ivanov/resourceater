package resourceater.model.resource.cpu;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateCpuResourceRequest(int size, Duration ttl) implements CreateRequest {
}
