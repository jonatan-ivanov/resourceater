package resourceater.model.resource.micrometer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.Tag;
import lombok.EqualsAndHashCode;
import lombok.Value;
import resourceater.model.resource.Model;

/**
 * @author Jonatan Ivanov
 */
@Value
@EqualsAndHashCode(callSuper=true)
public class MeterResourceModel extends Model<MeterResource> {
    private final int size;
//    private final List<String> meters;

    public MeterResourceModel(MeterResource resource) {
        super(resource);
//        this.meters = resource.getMeters().stream()
//            .sorted(Comparator.comparing(meter -> meter.getId().getName()))
//            .map(this::toString)
//            .collect(Collectors.toList());
        this.size = resource.getMeters().size();
    }

    private String toString(Meter meter) {
        Meter.Id id = meter.getId();
        String tags = id.getTags().stream()
            .map(this::toString)
            .collect(Collectors.joining(", "));
        String baseUnit = id.getBaseUnit();
        String meterUnitSuffix = baseUnit != null ? " " + baseUnit : "";
        String measurements = StreamSupport.stream(meter.measure().spliterator(), false)
            .map((measurement) -> toString(measurement, meterUnitSuffix))
            .collect(Collectors.joining(", "));

        return String.format("%s(%s)[%s]; %s", id.getName(), id.getType(), tags, measurements);
    }

    private String toString(Tag tag) {
        return String.format("%s='%s'", tag.getKey(), tag.getValue());
    }

    private String toString(Measurement measurement, String meterUnitSuffix) {
        Statistic statistic = measurement.getStatistic();
        return String.format("%s=%s%s",
            statistic.toString().toLowerCase(),
            measurement.getValue(),
            getUnitSuffix(statistic, meterUnitSuffix));
    }

    private String getUnitSuffix(Statistic statistic, String meterUnitSuffix) {
        return switch (statistic) {
            case DURATION, TOTAL_TIME, TOTAL, MAX, VALUE -> meterUnitSuffix;
            default -> "";
        };
    }
}
