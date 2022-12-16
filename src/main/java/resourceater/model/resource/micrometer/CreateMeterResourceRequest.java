package resourceater.model.resource.micrometer;

import java.time.Duration;

import io.micrometer.core.instrument.Meter;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
public record CreateMeterResourceRequest(int size, Meter.Type type, Duration ttl) implements CreateRequest {
}
