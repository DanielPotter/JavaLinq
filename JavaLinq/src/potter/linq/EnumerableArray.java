package potter.linq;

import java.util.Iterator;

/**
 * Provides the ability to iterate over an array.
 *
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of the elements to iterate.
 */
class EnumerableArray<T> implements IEnumerable<T>
{
    /**
     * Constructs an {@link EnumerableArray} instance.
     *
     * @param source
     *            The array to iterate.
     */
    public EnumerableArray(T[] source)
    {
        this.source = source;
    }

    private T[] source;

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator();
    }

    @Override
    public IEnumerator<T> getEnumerator()
    {
        return new ArrayIterator();
    }

    private class ArrayIterator extends SimpleIterator<T>
    {
        private int index;

        @Override
        public boolean moveNext()
        {
            if (index < source.length)
            {
                setCurrent(source[index++]);
                return true;
            }

            return false;
        }
    }
}
