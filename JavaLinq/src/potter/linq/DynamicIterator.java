package potter.linq;

import java.util.NoSuchElementException;

abstract class DynamicIterator<T> implements IEnumerator<T>
{
    private boolean hasBeenRead = true;

    @Override
    public boolean hasNext()
    {
        if (hasBeenRead)
        {
            hasBeenRead = false;
            return moveNext();
        }

        return true;
    }

    @Override
    public T next()
    {
        if (hasBeenRead)
        {
            if (moveNext() == false)
            {
                throw new NoSuchElementException("Cannot iterate past the end of the collection.");
            }
        }

        hasBeenRead = true;
        return getCurrent();
    }
}
