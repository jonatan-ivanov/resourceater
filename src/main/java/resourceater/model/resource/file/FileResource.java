package resourceater.model.resource.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.unit.DataSize;
import resourceater.model.resource.Resource;
import resourceater.model.resource.Response;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * @author Jonatan Ivanov
 */
@Slf4j
public class FileResource implements Resource {
    private static final int FILE_BUFFER_SIZE = (int) DataSize.ofMegabytes(1).toBytes();
    private final File file;

    public FileResource(FileResourceRequest request) {
        this(DataSize.parse(request.getSize()));
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
    public Response toResponse() {
        return FileResourceResponse.builder()
            .resourceId(this.getId())
            .size(this.file.length())
            .path(this.file.getAbsolutePath())
            .build();
    }
}
