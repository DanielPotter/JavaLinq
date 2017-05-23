package potter.linq;

/**
 * Extends a {@link DynamicIterator} with cached current value property.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
abstract class SimpleIterator<T> extends DynamicIterator<T>
{
    private T current;

    @Override
    public T getCurrent()
    {
        return current;
    }

    /**
     * Sets the element in the collection at the current position of the
     * enumerator.
     * 
     * @param value
     *            The element in the collection at the current position of the
     *            enumerator.
     */
    protected void setCurrent(T value)
    {
        current = value;
    }
}
