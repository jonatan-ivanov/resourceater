package resourceater.model.resource;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jonatan Ivanov
 */
@Getter
@ToString
@EqualsAndHashCode
public abstract class Resource<R extends Resource<R>> {
    private final Duration ttl;
    private final Instant createdAt;
    private final Instant diesAt;

    public Resource(Duration ttl) {
        this.ttl = ttl;
        this.createdAt = Instant.now();
        this.diesAt = getTtl()
            .map(createdAt::plus)
            .orElse(createdAt);
    }

    public final Optional<Duration> getTtl() {
        return Optional.ofNullable(ttl);
    }

    public final Duration getAge() {
        return Duration.between(createdAt, Instant.now());
    }

    public final String getId() {
        return String.valueOf(System.identityHashCode(this));
    }

    public void saved() {
        // noop
    }

    public void destroy() {
        // noop
    }

    public abstract Model<R> toModel();
}
