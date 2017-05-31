package potter.linq;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Wraps an {@link Iterable} into an object that implements the
 * {@link IEnumerable} interface.
 *
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
public class EnumerableAdapter<T> implements IEnumerable<T>
{
    /**
     * Creates a new instance with an {@link Iterable}.
     *
     * @param source
     *            The iterable to adapt.
     */
    public EnumerableAdapter(Iterable<T> source)
    {
        this.source = source;
    }

    /**
     * Creates a new instance with an {@link IEnumerable}.
     *
     * @param source
     *            The enumerable to adapt.
     */
    public EnumerableAdapter(IEnumerable<T> source)
    {
        this.source = source;
        enumerableSource = source;
    }

    /**
     * Creates a new instance with an {@link IEnumerator} factory.
     *
     * @param factory
     *            The factory that creates the {@link IEnumerator} instances.
     */
    public EnumerableAdapter(Supplier<IEnumerator<T>> factory)
    {
        this.factory = factory;
    }

    private Iterable<T> source;
    private IEnumerable<T> enumerableSource;
    private Supplier<IEnumerator<T>> factory;

    @Override
    public Iterator<T> iterator()
    {
        if (factory != null)
        {
            return factory.get();
        }

        return source.iterator();
    }

    @Override
    public IEnumerator<T> getEnumerator()
    {
        if (factory != null)
        {
            return factory.get();
        }

        if (enumerableSource != null)
        {
            return enumerableSource.getEnumerator();
        }

        return IEnumerable.super.getEnumerator();
    }
}
