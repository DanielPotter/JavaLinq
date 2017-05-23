package potter.linq;

import java.util.Iterator;

/**
 * Supports a simple iteration over a generic collection.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
public interface IEnumerator<T> extends Iterator<T>
{
    /**
     * Gets the element in the collection at the current position of the
     * enumerator.
     * 
     * @return The element in the collection at the current position of the
     *         enumerator.
     */
    T getCurrent();

    /**
     * Advances the enumerator to the next element of the collection.
     * 
     * @return <code>true</code> if the enumerator was successfully advanced to
     *         the next element; <code>false</code> if the enumerator has passed
     *         the end of the collection.
     */
    boolean moveNext();

    // region: Helper Methods

    /**
     * Wraps an {@link Iterator} instance as an {@link IEnumerator}.
     * 
     * @param source
     *            The {@link Iterator} to wrap.
     * @return An {@link IEnumerator} that enumerates over <code>source</code>.
     */
    static <T> IEnumerator<T> wrap(Iterator<T> source)
    {
        return new EnumeratorAdapter<>(source);
    }

    // endregion
}
