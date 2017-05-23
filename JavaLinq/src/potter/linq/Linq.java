package potter.linq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

/**
 * Provides a set of static methods for querying objects that implement
 * {@link Iterable}.
 *
 * @author Daniel Potter
 */
public class Linq
{
    // region: Aggregation

    // region: Count

    /**
     * Returns the number of elements in a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence that contains elements to be counted.
     * @return The number of elements in the input sequence.
     */
    public static <TSource> int count(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        int count = 0;
        Iterator<TSource> sequenceIterator = source.iterator();
        while (sequenceIterator.hasNext())
        {
            sequenceIterator.next();
            count++;
        }

        return count;
    }

    // endregion

    // region: To Collection

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} to create an array from.
     * @return An array that contains the elements from the input sequence.
     */
    public static <TSource> ArrayList<TSource> toArrayList(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        ArrayList<TSource> list = new ArrayList<>();
        for (TSource element : source)
        {
            list.add(element);
        }

        return list;
    }

    /**
     * Creates a {@link HashMap} from an {@link Iterable} according to a
     * specified key selector function.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            An {@link Iterable} from which to create a {@link HashMap}.
     * @param keySelector
     *            A function to extract a key from each element.
     * @return A {@link HashMap} that contains keys and values.
     */
    public static <TSource, TKey> HashMap<TKey, TSource> toDictionary(Iterable<TSource> source,
        Function<TSource, TKey> keySelector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (keySelector == null)
        {
            throw new IllegalArgumentException("keySelector is null.");
        }

        HashMap<TKey, TSource> dictionary = new HashMap<TKey, TSource>();
        for (TSource value : source)
        {
            TKey key = keySelector.apply(value);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    /**
     * Creates a {@link HashMap} from an {@link Iterable} according to specified
     * key selector and element selector functions.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the value returned by
     *            <code>elementSelector</code>.
     * @param source
     *            An {@link Iterable} to create a {@link HashMap} from.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param elementSelector
     *            A transform function to produce a result element value from
     *            each element.
     * @return A {@link HashMap} that contains values of type
     *         <code>TElement</code> selected from the input sequence.
     */
    public static <TSource, TKey, TElement> HashMap<TKey, TElement> toDictionary(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (keySelector == null)
        {
            throw new IllegalArgumentException("keySelector is null.");
        }
        if (elementSelector == null)
        {
            throw new IllegalArgumentException("elementSelector is null.");
        }

        HashMap<TKey, TElement> dictionary = new HashMap<TKey, TElement>();
        for (TSource item : source)
        {
            TKey key = keySelector.apply(item);
            TElement value = elementSelector.apply(item);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    // endregion

    // endregion

    // region: Mutating

    // region: Select

    /**
     * Projects each element of a sequence into a new form.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TResult>
     *            The type of the value returned by <code>selector</code>.
     * @param source
     *            A sequence of values on which to invoke a transform function.
     * @param selector
     *            A transform function to apply to each element.
     * @return An {@link Iterable} whose elements are the result of invoking the
     *         transform function on each element of <code>source</code>.
     */
    public static <TSource, TResult> IEnumerable<TResult> select(Iterable<TSource> source,
        Function<TSource, TResult> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        return new EnumerableAdapter<>(() ->
        {
            return new SimpleIterator<TResult>()
            {
                private Iterator<TSource> sourceIterator = source.iterator();

                @Override
                public boolean moveNext()
                {
                    if (sourceIterator.hasNext())
                    {
                        TSource input = sourceIterator.next();
                        TResult currentValue = selector.apply(input);
                        setCurrent(currentValue);
                        return true;
                    }

                    return false;
                }
            };
        });
    }

    // endregion

    // region: Where

    /**
     * Filters a sequence of values based on a predicate.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} to filter.
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link Iterable} that contains elements from the input
     *         sequence that satisfy the condition.
     */
    public static <TSource> IEnumerable<TSource> where(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() ->
        {
            return new SimpleIterator<TSource>()
            {
                private Iterator<TSource> sourceIterator = source.iterator();

                @Override
                public boolean moveNext()
                {
                    while (sourceIterator.hasNext())
                    {
                        setCurrent(sourceIterator.next());
                        if (predicate.apply(getCurrent()))
                        {
                            return true;
                        }
                    }

                    return false;
                }
            };
        });
    }

    // endregion

    // endregion
}
