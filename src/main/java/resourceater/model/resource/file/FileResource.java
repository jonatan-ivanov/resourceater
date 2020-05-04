package resourceater.model.resource.file;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Model;
import resourceater.model.resource.Resource;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class FileResource extends Resource<FileResource> {
    private static final long MAX_SIZE = DataSize.ofGigabytes(1).toBytes();
    private static final int FILE_BUFFER_SIZE = (int) DataSize.ofMegabytes(1).toBytes();
    private final File file;

    public FileResource(CreateFileResourceRequest request) {
        this(DataSize.parse(request.getSize()), request.getTtl());
    }

    private FileResource(DataSize dataSize, Duration ttl) {
        super(ttl);
        if (MAX_SIZE < dataSize.toBytes()) {
            throw new IllegalArgumentException("The maximum allowed size is 1GB"); // the size must fit into an int
        }

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

    @Override
    public void destroy() {
        try {
            Files.deleteIfExists(file.toPath());
        }
        catch (IOException e) {
            log.warn(String.format("Unable to delete file: %s", file.toPath()), e);
        }
    }

    @Override
    public Model<FileResource> toModel() {
        return new FileResourceModel(this, this.file.length(), this.file.getAbsolutePath());
    }
}
