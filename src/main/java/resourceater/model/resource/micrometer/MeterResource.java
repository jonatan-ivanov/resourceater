package resourceater.model.resource.micrometer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.Timer;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class MeterResource extends Resource<MeterResource> {
    private final MeterRegistry registry;
    private final List<Meter.Id> meters;

    public MeterResource(CreateMeterResourceRequest request, MeterRegistry registry) {
        this(request.size(), request.type(), request.ttl(), registry);
    }

    public MeterResource(int count, Meter.Type type, Duration ttl, MeterRegistry registry) {
        super(ttl);
        this.registry = registry;
        this.meters = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            this.meters.add(createMeter(type).getId());
        }
    }

    private Meter createMeter(Meter.Type type) {
        return switch (type) {
            case COUNTER -> createCounter();
            case GAUGE -> createGauge();
            case TIMER -> createTimer();
            case DISTRIBUTION_SUMMARY -> createDistributionSummary();
            case LONG_TASK_TIMER -> createLongTaskTimer();
            case OTHER -> createMeter();
        };
    }

    public List<Meter> getMeters() {
        return registry.getMeters();
    }

    @Override
    public Model<MeterResource> toModel() {
        return new MeterResourceModel(this);
    }

    @Override
    public void destroy() {
        for (Meter.Id id : meters) {
            registry.remove(id);
        }
    }

    private Counter createCounter() {
        Counter counter = Counter.builder("counter.resource")
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
        counter.increment();

        return counter;
    }

    private Gauge createGauge() {
        return Gauge.builder("gauge.resource", () -> 1)
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
    }

    private Timer createTimer() {
        Timer timer = Timer.builder("timer.resource")
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
        timer.record(42, TimeUnit.MILLISECONDS);

        return timer;
    }

    private DistributionSummary createDistributionSummary() {
        DistributionSummary distributionSummary = DistributionSummary.builder("distributionSummary.resource")
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
        distributionSummary.record(42);

        return distributionSummary;
    }

    private LongTaskTimer createLongTaskTimer() {
        LongTaskTimer longTaskTimer = LongTaskTimer.builder("longTaskTimer.resource")
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
        longTaskTimer.start();

        return longTaskTimer;
    }

    private Meter createMeter() {
        return Meter.builder("meter.resource", Meter.Type.OTHER, List.of(new Measurement(() -> 42.0, Statistic.VALUE)))
            .tag("high.cardinality", UUID.randomUUID().toString())
            .register(registry);
    }
}
