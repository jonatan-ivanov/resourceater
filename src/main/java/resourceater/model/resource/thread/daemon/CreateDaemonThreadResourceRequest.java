package resourceater.model.resource.thread.daemon;

import java.time.Duration;

import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateDaemonThreadResourceRequest(int size, Duration ttl) implements CreateRequest {
}
