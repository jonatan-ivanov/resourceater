package resourceater.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;
import resourceater.model.resource.Resource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
public class ResourceRepository<T extends Resource<T>> implements PagingAndSortingRepository<T, String>, CrudRepository<T, String>, DisposableBean {
    private final Map<String, T> resources = new ConcurrentHashMap<>();

    @NotNull
    @Override
    public Iterable<T> findAll(@NotNull Sort sort) {
        throw new UnsupportedOperationException("Sorting is not supported on this data set");
    }

    @NotNull
    @Override
    public Page<T> findAll(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            throw new UnsupportedOperationException("Sorting is not supported on this data set");
        }

        List<T> result = resources.values().stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .toList();

        return new PageImpl<>(result, pageable, count());
    }

    @NotNull
    @Override
    public <S extends T> S save(@NotNull S entity) {
        resources.put(entity.getId(), entity);
        entity.saved();
        scheduleResourceDeletionIfNeeded(entity);

        return entity;
    }

    @NotNull
    @Override
    public <S extends T> Iterable<S> saveAll(@NotNull Iterable<S> entities) {
        return toStream(entities)
            .map(this::save)
            .toList();
    }

    @NotNull
    @Override
    public Optional<T> findById(@NotNull String id) {
        return Optional.ofNullable(resources.get(id));
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return resources.containsKey(id);
    }

    @NotNull
    @Override
    public Iterable<T> findAll() {
        return resources.values();
    }

    @NotNull
    @Override
    public Iterable<T> findAllById(@NotNull Iterable<String> ids) {
        return toStream(ids)
            .filter(resources::containsKey)
            .map(resources::get)
            .toList();
    }

    @Override
    public long count() {
        return resources.size();
    }

    @Override
    public void deleteById(@NotNull String id) {
        Optional.ofNullable(resources.remove(id)).ifPresent(Resource::destroy);
    }

    @Override
    public void delete(T entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        ids.forEach(this::deleteById);
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
