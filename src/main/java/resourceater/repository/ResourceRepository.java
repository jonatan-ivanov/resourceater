package resourceater.repository;

import static java.util.stream.Collectors.toUnmodifiableList;
import static resourceater.utils.StreamUtils.toStream;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import reactor.core.publisher.Mono;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
public class ResourceRepository<T extends Resource<T>> implements PagingAndSortingRepository<T, String>, DisposableBean {
    private final Map<String, T> resources = new ConcurrentHashMap<>();

    @Override
    public Iterable<T> findAll(Sort sort) {
        throw new UnsupportedOperationException("Sorting is not supported on this data set");
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            throw new UnsupportedOperationException("Sorting is not supported on this data set");
        }

        List<T> result = resources.values().stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(toUnmodifiableList());

        return new PageImpl<>(result, pageable, count());
    }

    @Override
    public <S extends T> S save(S entity) {
        resources.put(entity.getId(), entity);
        entity.saved();
        scheduleResourceDeletionIfNeeded(entity);

        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return toStream(entities)
            .map(this::save)
            .collect(toUnmodifiableList());
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(resources.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return resources.containsKey(id);
    }

    @Override
    public Iterable<T> findAll() {
        return resources.values();
    }

    @Override
    public Iterable<T> findAllById(Iterable<String> ids) {
        return toStream(ids)
            .filter(resources::containsKey)
            .map(resources::get)
            .collect(toUnmodifiableList());
    }

    @Override
    public long count() {
        return resources.size();
    }

    @Override
    public void deleteById(String id) {
        Optional.ofNullable(resources.remove(id)).ifPresent(Resource::destroy);
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        resources.values().forEach(this::delete);
    }

    @Override
    public void destroy() {
        deleteAll();
    }

    private void scheduleResourceDeletionIfNeeded(T entity) {
        entity.getTtl().ifPresent(ttl -> Mono.delay(ttl).subscribe(event -> delete(entity)));
    }
}
