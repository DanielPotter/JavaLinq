package potter.linq;

import java.util.Iterator;

/**
 * Wraps an {@link Iterator} into an object that implements the
 * {@link IEnumerator} interface.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
class EnumeratorAdapter<T> extends SimpleIterator<T>
{
    /**
     * Creates a new instance with an {@link Iterator}.
     * 
     * @param source
     *            The iterator to adapt.
     */
    public EnumeratorAdapter(Iterator<T> source)
    {
        this.source = source;
    }

    private Iterator<T> source;

    @Override
    public boolean moveNext()
    {
        if (source.hasNext())
        {
            setCurrent(source.next());
        }

        return false;
    }
}
