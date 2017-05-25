package potter.linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Provides a set of static methods for querying objects that implement
 * {@link Iterable}.
 *
 * @author Daniel Potter
 */
public class Linq
{
    // region: Creation

    // region: As Enumerable

    /**
     * Gets an {@link IEnumerable} that represents a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} to wrap.
     * @return An {@link IEnumerable} containing the elements from
     *         <code>source</code>.
     */
    public static <TSource> IEnumerable<TSource> asEnumerable(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source instanceof IEnumerable<?>)
        {
            return (IEnumerable<TSource>) source;
        }

        return new EnumerableAdapter<>(source);
    }

    /**
     * Gets an {@link IEnumerable} that represents an array.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An array to wrap.
     * @return An {@link IEnumerable} containing the elements from
     *         <code>source</code>.
     */
    public static <TSource> IEnumerable<TSource> asEnumerable(TSource[] source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return new EnumerableArray<>(source);
    }

    // endregion

    // region: Empty

    /**
     * Returns an empty {@link IEnumerable} that has the specified type
     * argument.
     *
     * @param <TResult>
     *            The type to assign to the type parameter of the returned
     *            generic {@link IEnumerable}.
     * @param type
     *            The type to assign to the type parameter of the returned
     *            generic {@link IEnumerable}.
     * @return An empty {@link IEnumerable} whose type argument is
     *         <code>TResult</code>.
     */
    public static <TResult> IEnumerable<TResult> empty(Class<TResult> type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type is null.");
        }

        return EmptyEnumerable.getInstance(type);
    }

    private static class EmptyEnumerable<TElement> implements IEnumerable<TElement>
    {
        @SuppressWarnings("rawtypes")
        private static HashMap<Class, IEnumerable> cachedEnumerables = new HashMap<Class, IEnumerable>();

        public static <TElement> IEnumerable<TElement> getInstance(Class<TElement> type)
        {
            @SuppressWarnings("unchecked")
            IEnumerable<TElement> enumerable = cachedEnumerables.get(type);

            if (enumerable == null)
            {
                enumerable = new EmptyEnumerable<TElement>();
                cachedEnumerables.put(type, enumerable);
            }

            return enumerable;
        }

        @Override
        public Iterator<TElement> iterator()
        {
            return new EmptyEnumerator<>();
        }

        private static class EmptyEnumerator<TElement> implements IEnumerator<TElement>
        {
            @Override
            public boolean hasNext()
            {
                return false;
            }

            @Override
            public TElement next()
            {
                return null;
            }

            @Override
            public TElement getCurrent()
            {
                return null;
            }

            @Override
            public boolean moveNext()
            {
                return false;
            }
        }
    }

    // endregion

    // region: Range

    /**
     * Generates a sequence of integral numbers within a specified range.
     *
     * @param start
     *            The value of the first integer in the sequence.
     * @param count
     *            The number of sequential integers to generate.
     * @return An {@link IEnumerable} that contains a range of sequential
     *         integral numbers.
     */
    public static IEnumerable<Integer> range(int start, int count)
    {
        return new EnumerableAdapter<>(() -> new DynamicIterator<Integer>()
        {
            private int index = -1;

            @Override
            public Integer getCurrent()
            {
                return start + index;
            }

            @Override
            public boolean moveNext()
            {
                if (index < count - 1)
                {
                    index++;
                    return true;
                }

                return false;
            }
        });
    }

    // endregion

    // region: Repeat

    /**
     * Generates a sequence that contains one repeated value.
     *
     * @param <TResult>
     *            The type of the value to be repeated in the result sequence.
     * @param element
     *            The value to be repeated.
     * @param count
     *            The number of times to repeat the value in the generated
     *            sequence.
     * @return An {@link IEnumerable} that contains a repeated value.
     */
    public static <TResult> IEnumerable<TResult> repeat(TResult element, int count)
    {
        return new EnumerableAdapter<>(() -> new DynamicIterator<TResult>()
        {
            private int index;

            @Override
            public TResult getCurrent()
            {
                return element;
            }

            @Override
            public boolean moveNext()
            {
                if (index < count)
                {
                    index++;
                    return true;
                }

                return false;
            }
        });
    }

    // endregion

    // endregion

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

    // region: Contains

    /**
     * Determines whether a sequence contains a specified element by using the
     * default equality comparer.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence in which to locate a value.
     * @param value
     *            The value to locate in the sequence.
     * @return <code>true</code> if the source sequence contains an element that
     *         has the specified value; otherwise, <code>false</code>.
     */
    public static <TSource> boolean contains(Iterable<TSource> source, TSource value)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source instanceof Collection<?>)
        {
            return ((Collection<?>) source).contains(value);
        }

        for (TSource item : source)
        {
            if (value.equals(item))
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

    // region: Concat

    /**
     * Concatenates two sequences.
     *
     * @param <TSource>
     *            The type of the elements of the input sequences.
     * @param first
     *            The first sequence to concatenate.
     * @param second
     *            The sequence to concatenate to the first sequence.
     * @return An {@link Iterable} that contains the concatenated elements of
     *         the two input sequences.
     */
    public static <TSource> IEnumerable<TSource> concat(Iterable<TSource> first, Iterable<TSource> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first is null.");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second is null.");
        }

        return new EnumerableAdapter<>(() -> new ConcatIterator<>(first, second));
    }

    private static class ConcatIterator<TSource> extends SimpleIterator<TSource>
    {
        public ConcatIterator(Iterable<TSource> first, Iterable<TSource> second)
        {
            firstIterator = first.iterator();
            secondIterator = second.iterator();
        }

        private Iterator<TSource> firstIterator;
        private Iterator<TSource> secondIterator;

        @Override
        public boolean moveNext()
        {
            if (firstIterator.hasNext())
            {
                setCurrent(firstIterator.next());
                return true;
            }

            if (secondIterator.hasNext())
            {
                setCurrent(secondIterator.next());
                return true;
            }

            return false;
        }
    }

    // endregion

    // region: Except

    /**
     * Produces the set difference of two sequences by using the default
     * equality comparer to compare values.
     *
     * @param <TSource>
     *            The type of the elements of the input sequences.
     * @param first
     *            An {@link Iterable} whose elements that are not also in second
     *            will be returned.
     * @param second
     *            An {@link Iterable} whose elements that also occur in the
     *            first sequence will cause those elements to be removed from
     *            the returned sequence.
     * @return A sequence that contains the set difference of the elements of
     *         two sequences.
     */
    public static <TSource> IEnumerable<TSource> except(Iterable<TSource> first, Iterable<TSource> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first is null.");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second is null.");
        }

        return new EnumerableAdapter<>(() -> new ExceptIterator<>(first, second));
    }

    private static class ExceptIterator<TSource> extends SimpleIterator<TSource>
    {
        public ExceptIterator(Iterable<TSource> first, Iterable<TSource> second)
        {
            firstIterator = first.iterator();
            this.second = second;
        }

        private Iterator<TSource> firstIterator;
        private Iterable<TSource> second;

        private HashSet<TSource> set;

        @Override
        public boolean moveNext()
        {
            if (set == null)
            {
                set = new HashSet<TSource>();
                for (TSource element : second)
                {
                    set.add(element);
                }
            }

            while (firstIterator.hasNext())
            {
                TSource element = firstIterator.next();
                if (set.add(element))
                {
                    setCurrent(element);
                    return true;
                }
            }

            return false;
        }
    }

    // endregion

    // region: Intersect

    /**
     * Produces the set intersection of two sequences by using the default
     * equality comparer to compare values.
     *
     * @param <TSource>
     *            The type of the elements of the input sequences.
     * @param first
     *            An {@link Iterable} whose distinct elements that also appear
     *            in <code>second</code> will be returned.
     * @param second
     *            An {@link Iterable} whose distinct elements that also appear
     *            in the first sequence will be returned.
     * @return A sequence that contains the elements that form the set
     *         intersection of two sequences.
     */
    public static <TSource> IEnumerable<TSource> intersect(Iterable<TSource> first, Iterable<TSource> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first is null.");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second is null.");
        }

        return new EnumerableAdapter<>(() -> new IntersectIterator<>(first, second));
    }

    private static class IntersectIterator<TSource> extends SimpleIterator<TSource>
    {
        public IntersectIterator(Iterable<TSource> first, Iterable<TSource> second)
        {
            firstIterator = first.iterator();
            this.second = second;
        }

        private Iterator<TSource> firstIterator;
        private Iterable<TSource> second;

        private HashSet<TSource> set;

        @Override
        public boolean moveNext()
        {
            if (set == null)
            {
                set = new HashSet<TSource>();
                for (TSource element : second)
                {
                    set.add(element);
                }
            }

            while (firstIterator.hasNext())
            {
                TSource element = firstIterator.next();
                if (set.remove(element))
                {
                    setCurrent(element);
                    return true;
                }
            }

            return false;
        }
    }

    // endregion

    // region: Of Type

    /**
     * Filters the elements of an {@link Iterable} based on a specified type.
     *
     * @param <TResult>
     *            The type on which to filter the elements of the sequence.
     * @param source
     *            The {@link Iterable} whose elements to filter.
     * @param type
     *            The type of elements to filter
     * @return An {@link Iterable} that contains elements from the input
     *         sequence of type <code>type</code>.
     */
    public static <TResult> IEnumerable<TResult> ofType(Iterable<?> source, Class<TResult> type)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (type == null)
        {
            throw new IllegalArgumentException("type is null.");
        }

        return new EnumerableAdapter<>(() -> new OfTypeIterator<>(source, type));
    }

    private static class OfTypeIterator<TResult> extends SimpleIterator<TResult>
    {
        public OfTypeIterator(Iterable<?> source, Class<TResult> type)
        {
            sourceIterator = source.iterator();
            this.type = type;
        }

        private Iterator<?> sourceIterator;
        private Class<TResult> type;

        @Override
        public boolean moveNext()
        {
            while (sourceIterator.hasNext())
            {
                Object input = sourceIterator.next();
                if (type.isInstance(input))
                {
                    TResult currentValue = type.cast(input);
                    setCurrent(currentValue);
                    return true;
                }
            }

            return false;
        }
    }

    // endregion

    // region: Reverse

    /**
     * Inverts the order of the elements in a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values to reverse.
     * @return A sequence whose elements correspond to those of the input
     *         sequence in reverse order.
     */
    public static <TSource> IEnumerable<TSource> reverse(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return new EnumerableAdapter<>(() -> new ReverseIterator<>(source));
    }

    private static class ReverseIterator<TSource> extends SimpleIterator<TSource>
    {
        public ReverseIterator(Iterable<TSource> source)
        {
            this.source = source;
        }

        private Iterable<TSource> source;
        private ArrayList<TSource> items;
        private int index;

        @Override
        public boolean moveNext()
        {
            if (items == null)
            {
                items = toArrayList(source);
                index = items.size() - 1;
            }

            if (index >= 0)
            {
                setCurrent(items.get(index--));
                return true;
            }

            return false;
        }
    }

    // endregion

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

        return new EnumerableAdapter<>(
            () -> new SelectEnumerator<TSource, TResult>(source, (item, index) -> selector.apply(item)));
    }

    /**
     * Projects each element of a sequence into a new form by incorporating the
     * element's index.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TResult>
     *            The type of the value returned by <code>selector</code>.
     * @param source
     *            A sequence of values on which to invoke a transform function.
     * @param selector
     *            A transform function to apply to each source element; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link Iterable} whose elements are the result of invoking the
     *         transform function on each element of <code>source</code>.
     */
    public static <TSource, TResult> IEnumerable<TResult> select(Iterable<TSource> source,
        BiFunction<TSource, Integer, TResult> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        return new EnumerableAdapter<>(() -> new SelectEnumerator<TSource, TResult>(source, selector));
    }

    private static class SelectEnumerator<TSource, TResult> extends SimpleIterator<TResult>
    {
        public SelectEnumerator(Iterable<TSource> source, BiFunction<TSource, Integer, TResult> selector)
        {
            this.selector = selector;
            sourceIterator = source.iterator();
        }

        private final BiFunction<TSource, Integer, TResult> selector;

        private Iterator<TSource> sourceIterator;
        private int index;

        @Override
        public boolean moveNext()
        {
            if (sourceIterator.hasNext())
            {
                TSource input = sourceIterator.next();
                TResult currentValue = selector.apply(input, index++);
                setCurrent(currentValue);
                return true;
            }

            return false;
        }
    }

    // endregion

    // region: Select Many

    /**
     * Projects each element of a sequence to an {@link IEnumerable} and
     * flattens the resulting sequences into one sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TResult>
     *            The type of the elements of the sequence returned by
     *            <code>selector</code>.
     * @param source
     *            A sequence of values to project.
     * @param selector
     *            A transform function to apply to each element.
     * @return An {@link IEnumerable} whose elements are the result of invoking
     *         the one-to-many transform function on each element of the input
     *         sequence.
     */
    public static <TSource, TResult> IEnumerable<TResult> selectMany(Iterable<TSource> source,
        Function<TSource, Iterable<TResult>> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        return new EnumerableAdapter<>(() -> new SelectManyEnumerator<TSource, TResult>(source)
        {
            @Override
            public Iterable<TResult> convert(TSource item, int index)
            {
                return selector.apply(item);
            }
        });
    }

    /**
     * Projects each element of a sequence to an {@link IEnumerable}, and
     * flattens the resulting sequences into one sequence. The index of each
     * source element is used in the projected form of that element.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TResult>
     *            The type of the elements of the sequence returned by
     *            <code>selector</code>.
     * @param source
     *            A sequence of values to project.
     * @param selector
     *            A transform function to apply to each source element; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} whose elements are the result of invoking
     *         the one-to-many transform function on each element of an input
     *         sequence.
     */
    public static <TSource, TResult> IEnumerable<TResult> selectMany(Iterable<TSource> source,
        BiFunction<TSource, Integer, Iterable<TResult>> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        return new EnumerableAdapter<>(() -> new SelectManyEnumerator<TSource, TResult>(source)
        {
            @Override
            public Iterable<TResult> convert(TSource item, int index)
            {
                return selector.apply(item, index);
            }
        });
    }

    private static abstract class SelectManyEnumerator<TSource, TResult> extends SimpleIterator<TResult>
    {
        public SelectManyEnumerator(Iterable<TSource> source)
        {
            sourceIterator = source.iterator();
        }

        private Iterator<TSource> sourceIterator;
        private Iterator<TResult> currentIterator;
        private int sourceIndex;

        @Override
        public boolean moveNext()
        {
            while (currentIterator == null || currentIterator.hasNext() == false)
            {
                if (sourceIterator.hasNext())
                {
                    TSource input = sourceIterator.next();
                    currentIterator = convert(input, sourceIndex++).iterator();
                }
                else
                {
                    return false;
                }
            }

            setCurrent(currentIterator.next());
            return true;
        }

        public abstract Iterable<TResult> convert(TSource item, int index);
    }

    // endregion

    // region: Skip

    /**
     * Bypasses a specified number of elements in a sequence and then returns
     * the remaining elements.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return elements.
     * @param count
     *            The number of elements to skip before returning the remaining
     *            elements.
     * @return An {@link IEnumerable} that contains the elements that occur
     *         after the specified index in the input sequence.
     */
    public static <TSource> IEnumerable<TSource> skip(Iterable<TSource> source, int count)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return new EnumerableAdapter<>(() -> new SkipWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean skip(TSource item, int index)
            {
                return index < count;
            }
        });
    }

    /**
     * Bypasses elements in a sequence as long as a specified condition is true
     * and then returns the remaining elements.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return elements.
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence starting at the first element in the linear series that
     *         does not pass the test specified by <code>predicate</code>.
     */
    public static <TSource> IEnumerable<TSource> skipWhile(Iterable<TSource> source,
            Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() -> new SkipWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean skip(TSource item, int index)
            {
                return predicate.apply(item);
            }
        });
    }

    /**
     * Bypasses elements in a sequence as long as a specified condition is true
     * and then returns the remaining elements. The element's index is used in
     * the logic of the predicate function.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return elements.
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence starting at the first element in the linear series that
     *         does not pass the test specified by <code>predicate</code>.
     */
    public static <TSource> IEnumerable<TSource> skipWhile(Iterable<TSource> source,
            BiFunction<TSource, Integer, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() -> new SkipWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean skip(TSource item, int index)
            {
                return predicate.apply(item, index);
            }
        });
    }

    private static abstract class SkipWhileEnumerator<TSource> extends SimpleIterator<TSource>
    {
        public SkipWhileEnumerator(Iterable<TSource> source)
        {
            sourceIterator = source.iterator();
        }

        private Iterator<TSource> sourceIterator;
        private int index;
        private boolean hasSkipped;

        @Override
        public boolean moveNext()
        {
            if (hasSkipped == false)
            {
                hasSkipped = true;

                while (sourceIterator.hasNext())
                {
                    TSource current = sourceIterator.next();
                    if (skip(current, index++) == false)
                    {
                        setCurrent(current);
                        return true;
                    }
                }
            }

            if (sourceIterator.hasNext())
            {
                setCurrent(sourceIterator.next());
                return true;
            }

            return false;
        }

        public abstract boolean skip(TSource item, int index);
    }

    // endregion

    // region: Take

    /**
     * Returns a specified number of contiguous elements from the start of a
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The sequence from which to return elements.
     * @param count
     *            The number of elements to return.
     * @return An {@link IEnumerable} that contains the specified number of
     *         elements from the start of the input sequence.
     */
    public static <TSource> IEnumerable<TSource> take(Iterable<TSource> source, int count)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return new EnumerableAdapter<>(() -> new TakeWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean take(TSource item, int index)
            {
                return index < count;
            }
        });
    }

    /**
     * Returns elements from a sequence as long as a specified condition is
     * true.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The sequence from which to return elements.
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence that occur before the element at which the test no
     *         longer passes.
     */
    public static <TSource> IEnumerable<TSource> takeWhile(Iterable<TSource> source,
            Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() -> new TakeWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean take(TSource item, int index)
            {
                return predicate.apply(item);
            }
        });
    }

    /**
     * Returns elements from a sequence as long as a specified condition is
     * true. The element's index is used in the logic of the predicate function.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The sequence from which to return elements.
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence that occur before the element at which the test no
     *         longer passes.
     */
    public static <TSource> IEnumerable<TSource> takeWhile(Iterable<TSource> source,
            BiFunction<TSource, Integer, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() -> new TakeWhileEnumerator<TSource>(source)
        {
            @Override
            public boolean take(TSource item, int index)
            {
                return predicate.apply(item, index);
            }
        });
    }

    private static abstract class TakeWhileEnumerator<TSource> extends SimpleIterator<TSource>
    {
        public TakeWhileEnumerator(Iterable<TSource> source)
        {
            sourceIterator = source.iterator();
        }

        private Iterator<TSource> sourceIterator;
        private int index;
        private boolean isTaking = true;

        @Override
        public boolean moveNext()
        {
            if (isTaking && sourceIterator.hasNext())
            {
                TSource item = sourceIterator.next();
                if (take(item, index++))
                {
                    setCurrent(item);
                    return true;
                }

                isTaking = false;
            }

            return false;
        }

        public abstract boolean take(TSource item, int index);
    }

    // endregion

    // region: Union

    /**
     * Produces the set union of two sequences by using the default equality
     * comparer.
     *
     * @param <TSource>
     *            The type of the elements of the input sequences.
     * @param first
     *            An {@link Iterable} whose distinct elements form the first set
     *            for the union.
     * @param second
     *            An {@link Iterable} whose distinct elements form the second
     *            set for the union.
     * @return An {@link IEnumerable} that contains the elements from both input
     *         sequences, excluding duplicates.
     */
    public static <TSource> IEnumerable<TSource> union(Iterable<TSource> first, Iterable<TSource> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first is null.");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second is null.");
        }

        return new EnumerableAdapter<>(() -> new UnionIterator<>(first, second));
    }

    private static class UnionIterator<TSource> extends SimpleIterator<TSource>
    {
        public UnionIterator(Iterable<TSource> first, Iterable<TSource> second)
        {
            firstIterator = first.iterator();
            secondIterator = second.iterator();
        }

        private Iterator<TSource> firstIterator;
        private Iterator<TSource> secondIterator;

        private HashSet<TSource> set = new HashSet<TSource>();

        @Override
        public boolean moveNext()
        {
            while (firstIterator.hasNext())
            {
                TSource element = firstIterator.next();
                if (set.add(element))
                {
                    setCurrent(element);
                    return true;
                }
            }

            while (secondIterator.hasNext())
            {
                TSource element = secondIterator.next();
                if (set.add(element))
                {
                    setCurrent(element);
                    return true;
                }
            }

            return false;
        }
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

        return new EnumerableAdapter<>(() -> new WhereEnumerator<TSource>(source.iterator())
        {
            @Override
            public boolean include(TSource item, int index)
            {
                return predicate.apply(item);
            }
        });
    }

    /**
     * Filters a sequence of values based on a predicate. Each element's index
     * is used in the logic of the predicate function.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} to filter.
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link Iterable} that contains elements from the input
     *         sequence that satisfy the condition.
     */
    public static <TSource> IEnumerable<TSource> where(Iterable<TSource> source,
        BiFunction<TSource, Integer, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new EnumerableAdapter<>(() -> new WhereEnumerator<TSource>(source.iterator())
        {
            @Override
            public boolean include(TSource item, int index)
            {
                return predicate.apply(item, index);
            }
        });
    }

    private static abstract class WhereEnumerator<T> extends SimpleIterator<T>
    {
        public WhereEnumerator(Iterator<T> source)
        {
            this.source = source;
        }

        private Iterator<T> source;
        private int index;

        @Override
        public boolean moveNext()
        {
            while (source.hasNext())
            {
                T item = source.next();
                if (include(item, index++))
                {
                    setCurrent(item);
                    return true;
                }
            }

            return false;
        }

        public abstract boolean include(T item, int index);
    }

    // endregion

    // endregion
}
