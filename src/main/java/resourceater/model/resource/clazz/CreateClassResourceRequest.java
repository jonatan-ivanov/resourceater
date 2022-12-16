package resourceater.model.resource.clazz;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateClassResourceRequest(int size, Duration ttl) implements CreateRequest {
}
