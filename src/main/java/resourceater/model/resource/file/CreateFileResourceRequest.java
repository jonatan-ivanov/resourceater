package resourceater.model.resource.file;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateFileResourceRequest(String size, Duration ttl) implements CreateRequest {
}
