package potter.linq;

import java.util.Iterator;
import java.util.function.Supplier;

public class LinqIterable<T> implements IEnumerable<T>
{
    public LinqIterable(Iterable<T> source)
    {
        this.source = source;
    }

    public LinqIterable(IEnumerable<T> source)
    {
        this.source = source;
        enumerableSource = source;
    }

    LinqIterable(Supplier<IEnumerator<T>> factory)
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
