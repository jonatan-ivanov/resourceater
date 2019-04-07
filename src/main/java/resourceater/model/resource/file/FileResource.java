package resourceater.model.resource.file;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class FileResource implements Resource {
    private static final int FILE_BUFFER_SIZE = (int) DataSize.ofMegabytes(1).toBytes();
    @JsonIgnore private final File file;

    @JsonCreator public FileResource(@JsonProperty("size") String size) {
        this(DataSize.parse(size));
    }

    private FileResource(DataSize dataSize) {
        this.file = new File(getId());
        populate(file, dataSize);
    }

    private static void populate(File file, DataSize dataSize) {
        try (OutputStream outputStream = createOutputStream(file)) {
            for (int i = 0; i < dataSize.toBytes(); i++) {
                outputStream.write(0);
            }
        }
        catch (IOException e) {
            log.warn(String.format("Unable to populate file: %s", file.toPath()), e);
        }
    }

    private static OutputStream createOutputStream(File file) throws IOException {
        return new BufferedOutputStream(
            Files.newOutputStream(file.toPath(), CREATE_NEW, WRITE),
            FILE_BUFFER_SIZE
        );
    }

    @SuppressWarnings("unused")
    public Path getPath() {
        return file.toPath();
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public void destroy() {
        try {
            Files.deleteIfExists(file.toPath());
        }
        catch (IOException e) {
            log.warn(String.format("Unable to delete file: %s", file.toPath()), e);
        }
    }
}
