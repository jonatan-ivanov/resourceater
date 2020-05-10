package resourceater.repository;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static resourceater.utils.StreamUtils.toStream;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
class ResourceRepositoryTest {
    private static final Duration DEFAULT_TTL = Duration.ofSeconds(5);
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
    void shouldNotSaveResourceWithZeroTtl() {
        saveThreeResources(Duration.ZERO);
        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    void shouldSaveResourceWithoutTtl() {
        saveThreeResources(null);
        assertThat(repository.count()).isEqualTo(3);
    }

    @Test
    void shouldDeleteAndDestroyEntityByReference() {
        Iterable<TestResource> resources = saveThreeResources();
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
        List<TestResource> savedResources = toStream(saveThreeResources()).collect(toUnmodifiableList());
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

        List<TestResource> foundResources = Stream.concat(toStream(firstPage), toStream(secondPage))
            .collect(toUnmodifiableList());
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
            .collect(toUnmodifiableList());

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
            .collect(toUnmodifiableList());

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
        return saveThreeResources(DEFAULT_TTL);
    }

    private Iterable<TestResource> saveThreeResources(Duration ttl) {
        return repository.saveAll(List.of(createResource(ttl), createResource(ttl), createResource(ttl)));
    }

    private TestResource createResource() {
        return createResource(DEFAULT_TTL);
    }

    private TestResource createResource(Duration ttl) {
        return spy(new TestResource(ttl));
    }
}
