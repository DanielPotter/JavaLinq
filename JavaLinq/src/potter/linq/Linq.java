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

    // region: All

    /**
     * Determines whether all elements of a sequence satisfy a condition.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} that contains the elements to apply the
     *            predicate to.
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>true</code> if every element of the source sequence passes
     *         the test in the specified predicate, or if the sequence is empty;
     *         otherwise, <code>false</code>.
     */
    public static <TSource> boolean all(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        for (TSource item : source)
        {
            if (predicate.apply(item) == false)
            {
                return false;
            }
        }

        return true;
    }

    // endregion

    // region: Any

    /**
     * Determines whether a sequence contains any elements.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} whose elements to which to apply the
     *            predicate.
     * @return <code>true</code> if the source sequence contains any elements;
     *         otherwise, <code>false</code>.
     */
    public static <TSource> boolean any(Iterable<TSource> source)
    {
        if (source == null)
        {
            return false;
        }

        return source.iterator().hasNext();
    }

    /**
     * Determines whether any element of a sequence satisfies a condition.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} whose elements to which to apply the
     *            predicate.
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>true</code> if any elements in the source sequence pass the
     *         test in the specified predicate; otherwise, <code>false</code>.
     */
    public static <TSource> boolean any(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            return false;
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        for (TSource item : source)
        {
            if (predicate.apply(item))
            {
                return true;
            }
        }

        return false;
    }

    // endregion

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

    /**
     * Returns a number that represents how many elements in the specified
     * sequence satisfy a condition.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence that contains elements to be tested and counted.
     * @param predicate
     *            A function to test each element for a condition.
     * @return A number that represents how many elements in the sequence
     *         satisfy the condition in the predicate function.
     */
    public static <TSource> int count(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        int count = 0;
        for (TSource item : source)
        {
            if (predicate.apply(item))
            {
                count++;
            }
        }

        return count;
    }

    /**
     * Returns a {@link Long} that represents the total number of elements in a
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} that contains elements to be counted.
     * @return The number of elements in the input sequence.
     */
    public static <TSource> long longCount(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        long count = 0;
        Iterator<TSource> sequenceIterator = source.iterator();
        while (sequenceIterator.hasNext())
        {
            sequenceIterator.next();
            count++;
        }

        return count;
    }

    /**
     * Returns a {@link Long} that represents how many elements in a sequence
     * satisfy a condition.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} that contains elements to be counted.
     * @param predicate
     *            A function to test each element for a condition.
     * @return A number that represents how many elements in the sequence
     *         satisfy the condition in the predicate function.
     */
    public static <TSource> long longCount(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        long count = 0;
        for (TSource item : source)
        {
            if (predicate.apply(item))
            {
                count++;
            }
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

    // region: Mutation

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
