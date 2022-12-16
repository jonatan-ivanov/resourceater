package resourceater.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resourceater.model.resource.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static resourceater.utils.StreamUtils.toStream;

/**
 * @author Jonatan Ivanov
 */
class ResourceRepositoryTest {
    private final ResourceRepository<TestResource> repository = new ResourceRepository<>();

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
        Iterable<TestResource> resources = saveThreeResources();
        // We intentionally call delete in a loop instead of deleteAll since we want to test the delete method
        // noinspection UseBulkOperation
        resources.forEach(repository::delete);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldNotDeleteNonExistingEntityByReference() {
        Iterable<TestResource> resources = saveThreeResources();
        repository.delete(createResource());

        assertThat(repository.count()).isEqualTo(3);
        resources.forEach(resource -> verify(resource, times(0)).destroy());
    }

    @Test
    void shouldDeleteAndDestroyAllEntities() {
        Iterable<TestResource> resources = saveThreeResources();
        repository.deleteAll();

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldDeleteAndDestroyEntitiesById() {
        Iterable<TestResource> resources = saveThreeResources();
        repository.deleteAll(resources);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldDeleteAndDestroyEntityById() {
        Iterable<TestResource> resources = saveThreeResources();
        toStream(resources)
            .map(Resource::getId)
            .forEach(repository::deleteById);

        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    @Test
    void shouldNotDeleteNonExistingEntityById() {
        Iterable<TestResource> resources = saveThreeResources();
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
    void shouldFindAndPageAllSavedEntities() {
        List<TestResource> savedResources = toStream(saveThreeResources()).toList();
        Page<TestResource> firstPage = repository.findAll(PageRequest.of(0, 2));
        Page<TestResource> secondPage = repository.findAll(firstPage.nextPageable());

        assertThat(firstPage.getTotalElements()).isEqualTo(3);
        assertThat(firstPage.getTotalPages()).isEqualTo(2);
        assertThat(firstPage.hasNext()).isTrue();
        assertThat(firstPage.hasPrevious()).isFalse();

        assertThat(secondPage.getTotalElements()).isEqualTo(3);
        assertThat(secondPage.getTotalPages()).isEqualTo(2);
        assertThat(secondPage.hasNext()).isFalse();
        assertThat(secondPage.hasPrevious()).isTrue();

        List<TestResource> foundResources = Stream.concat(toStream(firstPage), toStream(secondPage)).toList();
        assertThat(foundResources).hasSameElementsAs(savedResources);
    }

    @Test
    void sortedFindAndPageAllShouldNotBeSupported() {
        assertThatThrownBy(() -> repository.findAll(PageRequest.of(0, 2, Sort.by("testField"))))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Sorting is not supported on this data set")
            .hasNoCause();
    }

    @Test
    void sortedFindAllShouldNotBeSupported() {
        assertThatThrownBy(() -> repository.findAll(Sort.by("testField")))
            .isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Sorting is not supported on this data set")
            .hasNoCause();
    }

    @Test
    void shouldFindAllSavedEntitiesById() {
        Iterable<TestResource> resources = saveThreeResources();
        Iterable<String> ids = toStream(resources)
            .map(Resource::getId)
            .toList();

        assertThat(repository.findAllById(ids)).hasSameElementsAs(resources);
    }

    @Test
    void shouldNotFindAnyUnsavedEntitiesById() {
        saveThreeResources();
        assertThat(repository.findAllById(List.of("", "0", "1"))).isEmpty();
    }

    @Test
    void shouldFindSavedEntityById() {
        Iterable<TestResource> resources = saveThreeResources();
        List<TestResource> foundResources = toStream(resources)
            .map(Resource::getId)
            .map(repository::findById)
            .flatMap(Optional::stream)
            .toList();

        assertThat(foundResources).hasSameElementsAs(resources);
    }

    @Test
    void shouldNotFindUnsavedEntityById() {
        saveThreeResources();
        assertThat(repository.findById("")).isEmpty();
    }

    @Test
    void shouldSaveEntity() {
        TestResource resource = createResource();
        assertThat(repository.save(resource)).isSameAs(resource);
        assertThat(repository.findAll()).hasSameElementsAs(List.of(resource));
    }

    @Test
    void shouldSaveAllEntities() {
        List<TestResource> resources = List.of(createResource(), createResource(), createResource());
        assertThat(repository.saveAll(resources)).hasSameElementsAs(resources);
        assertThat(repository.findAll()).hasSameElementsAs(resources);
    }

    @Test
    void shouldRemoveAndDestroyAllEntities() {
        Iterable<TestResource> resources = saveThreeResources();
        repository.destroy();
        assertThat(repository.count()).isZero();
        resources.forEach(resource -> verify(resource).destroy());
    }

    private Iterable<TestResource> saveThreeResources() {
        return repository.saveAll(List.of(createResource(), createResource(), createResource()));
    }

    private TestResource createResource() {
        return spy(TestResource.class);
    }
}
