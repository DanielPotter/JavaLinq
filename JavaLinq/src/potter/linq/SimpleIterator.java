package potter.linq;

abstract class SimpleIterator<T> extends DynamicIterator<T>
{
    private T current;

    @Override
    public T getCurrent()
    {
        return current;
    }

    protected void setCurrent(T value)
    {
        current = value;
    }
}
