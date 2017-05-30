package potter.linq;

import java.util.NoSuchElementException;

/**
 * Implements base logic for an iterable where the existence of the next element
 * is not known until an attempt to move to the next element is made.
 *
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
public abstract class DynamicIterator<T> implements IEnumerator<T>
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
