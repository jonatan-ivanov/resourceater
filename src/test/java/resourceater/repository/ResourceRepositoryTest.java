package resourceater.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resourceater.model.resource.Resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
class ResourceRepositoryTest {
    private final ResourceRepository<Resource> repository = new ResourceRepository<>();

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        assertThat(repository.count()).isZero();
    }

    @Test
    void shouldReturnCorrectCounts() {
        saveThreeResources();
        assertThat(repository.count()).isEqualTo(3);

        repository.deleteAll();
        assertThat(repository.count()).isZero();
    }

    @Test
    void shouldDeleteAndDestroyEntityByReference() {
        Iterable<Resource> resources = saveThreeResources();
        resources.forEach(repository::delete);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldNotDeleteNonExistingEntityByReference() {
        Iterable<Resource> resources = saveThreeResources();
        repository.delete(createResource());

        assertThat(repository.count()).isEqualTo(3);
        resources.forEach(resource -> verify(resource, times(0)).destroy());
    }

    @Test
    void shouldDeleteAndDestroyAllEntities() {
        Iterable<Resource> resources = saveThreeResources();
        repository.deleteAll();

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldDeleteAndDestroyEntitiesById() {
        Iterable<Resource> resources = saveThreeResources();
        repository.deleteAll(resources);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldDeleteAndDestroyEntityById() {
        Iterable<Resource> resources = saveThreeResources();
        toStream(resources)
            .map(Resource::getId)
            .forEach(repository::deleteById);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldNotDeleteNonExistingEntityById() {
        Iterable<Resource> resources = saveThreeResources();
        repository.deleteById("");

        assertThat(repository.count()).isEqualTo(3);
        resources.forEach(resource -> verify(resource, times(0)).destroy());
    }

    @Test
    void savedEntitiesShouldExist() {
        assertThat(
            toStream(saveThreeResources())
                .map(Resource::getId)
                .allMatch(repository::existsById)
        ).isTrue();
    }

    @Test
    void unsavedEntitiesShouldNotExist() {
        saveThreeResources();
        assertThat(repository.existsById("")).isFalse();
    }

    @Test
    void shouldFindAllSavedEntities() {
        assertThat(saveThreeResources()).hasSameElementsAs(repository.findAll());
    }

    @Test
    void shouldFindAllSavedEntitiesById() {
        Iterable<Resource> resources = saveThreeResources();
        Iterable<String> ids = toStream(resources)
            .map(Resource::getId)
            .collect(Collectors.toList());

        assertThat(repository.findAllById(ids)).hasSameElementsAs(resources);
    }

    @Test
    void shouldNotFindAnyUnsavedEntitiesById() {
        saveThreeResources();
        assertThat(repository.findAllById(List.of("", "0", "1"))).isEmpty();
    }

    @Test
    void shouldFindSavedEntityById() {
        Iterable<Resource> resources = saveThreeResources();
        List<Resource> foundResources = toStream(resources)
            .map(Resource::getId)
            .map(repository::findById)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());

        assertThat(foundResources).hasSameElementsAs(resources);
    }

    @Test
    void shouldNotFindUnsavedEntityById() {
        saveThreeResources();
        assertThat(repository.findById("")).isEmpty();
    }

    @Test
    void shouldSaveEntity() {
        Resource resource = createResource();
        assertThat(repository.save(resource)).isSameAs(resource);
        assertThat(repository.findAll()).hasSameElementsAs(List.of(resource));
    }

    @Test
    void shouldSaveAllEntities() {
        List<Resource> resources = List.of(createResource(), createResource(), createResource());
        assertThat(repository.saveAll(resources)).hasSameElementsAs(resources);
        assertThat(repository.findAll()).hasSameElementsAs(resources);
    }

    @Test
    void shouldRemoveAndDestroyAllEntities() {
        Iterable<Resource> resources = saveThreeResources();
        repository.destroy();
        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    private Iterable<Resource> saveThreeResources() {
        return repository.saveAll(List.of(createResource(), createResource(), createResource()));
    }

    private Resource createResource() {
        Resource resource = mock(Resource.class);
        when(resource.getId()).thenReturn(UUID.randomUUID().toString());

        return resource;
    }
}
