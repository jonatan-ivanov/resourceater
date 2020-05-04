package resourceater.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Jonatan Ivanov
 */
public class StreamUtils {
    public static <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
