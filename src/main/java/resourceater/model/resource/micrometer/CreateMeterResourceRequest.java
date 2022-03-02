package resourceater.model.resource.micrometer;

import java.time.Duration;

import io.micrometer.core.instrument.Meter;
import lombok.Value;
import resourceater.model.resource.CreateRequest;

/**
 * @author Jonatan Ivanov
 */
@Value
public class CreateMeterResourceRequest implements CreateRequest {
    private final int size;
    private final Meter.Type type;
    private final Duration ttl;
}
