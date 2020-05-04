package resourceater.model.resource;

import java.time.Duration;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Jonatan Ivanov
 */
@Getter
@RequiredArgsConstructor
public abstract class Model<R extends Resource<R>> extends RepresentationModel<Model<R>> {
    protected final String id;
    protected final Duration ttl;
    protected final Instant createdAt;
    protected final Instant diesAt;

    public Model(R resource){
        this.id = resource.getId();
        this.ttl = resource.getTtl().orElse(null);
        this.createdAt = resource.getCreatedAt();
        this.diesAt = resource.getDiesAt();
    }
}
