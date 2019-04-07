package resourceater.repository;

import org.springframework.beans.factory.DisposableBean;
import resourceater.model.resource.Resource;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static resourceater.utils.StreamUtils.toStream;

/**
 * Implements org.springframework.data.repository.CrudRepository
 * @author Jonatan Ivanov
 */
@SuppressWarnings("WeakerAccess")
public class ResourceRepository<T extends Resource> implements DisposableBean {
    private final Map<String, T> resources = new ConcurrentHashMap<>();

    public long count() {
        return resources.size();
    }

    public void delete(T entity) {
        deleteById(entity.getId());
    }

    public void deleteAll() {
        resources.values().forEach(this::delete);
    }

    public void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(this::delete);
    }

    public void deleteById(String id) {
        Optional.ofNullable(resources.remove(id)).ifPresent(Resource::destroy);
    }

    public boolean existsById(String id) {
        return resources.containsKey(id);
    }

    public Iterable<T> findAll() {
        return resources.values();
    }

    public Iterable<T> findAllById(Iterable<String> ids) {
        return toStream(ids)
            .filter(resources::containsKey)
            .map(resources::get)
            .collect(Collectors.toList());
    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(resources.get(id));
    }

    public T save(T entity) {
        resources.put(entity.getId(), entity);
        return entity;
    }

    public Iterable<T> saveAll(Iterable<T> entities) {
        return toStream(entities)
            .map(this::save)
            .collect(Collectors.toList());
    }

    @Override
    public void destroy() {
        deleteAll();
    }
}
