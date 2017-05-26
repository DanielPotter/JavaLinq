package potter.linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

    // region: Distinct

    /**
     * Returns distinct elements from a sequence by using the default equality
     * comparer to compare values.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The sequence from which to remove duplicate elements.
     * @return An {@link IEnumerable} that contains distinct elements from the
     *         source sequence.
     */
    public static <TSource> IEnumerable<TSource> distinct(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return new EnumerableAdapter<>(() -> new DistinctIterator<>(source));
    }

    private static class DistinctIterator<TSource> extends SimpleIterator<TSource>
    {
        public DistinctIterator(Iterable<TSource> source)
        {
            this.source = source;

            reset();
        }

        private final Iterable<TSource> source;

        private Iterator<TSource> sourceIterator;
        private HashSet<TSource> set;

        public void reset()
        {
            sourceIterator = source.iterator();

            if (set == null)
            {
                set = new HashSet<>();
            }
            else
            {
                set.clear();
            }
        }

        @Override
        public boolean moveNext()
        {
            while (sourceIterator.hasNext())
            {
                TSource element = sourceIterator.next();
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

        return new EnumerableAdapter<>(() -> new SelectEnumerator<TSource, TResult>(source)
        {
            @Override
            public TResult select(TSource item, int index)
            {
                return selector.apply(item);
            }
        });
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

        return new EnumerableAdapter<>(() -> new SelectEnumerator<TSource, TResult>(source)
        {
            @Override
            public TResult select(TSource item, int index)
            {
                return selector.apply(item, index);
            }
        });
    }

    private static abstract class SelectEnumerator<TSource, TResult> extends SimpleIterator<TResult>
    {
        public SelectEnumerator(Iterable<TSource> source)
        {
            sourceIterator = source.iterator();
        }

        private Iterator<TSource> sourceIterator;
        private int index;

        @Override
        public boolean moveNext()
        {
            if (sourceIterator.hasNext())
            {
                TSource input = sourceIterator.next();
                TResult currentValue = select(input, index++);
                setCurrent(currentValue);
                return true;
            }

            return false;
        }

        public abstract TResult select(TSource item, int index);
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

    // region: Sorting

    // region: Order By

    /**
     * Sorts the elements of a sequence in ascending order according to a key.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            A sequence of values to order.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> orderBy(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType)
    {
        return new KeyedOrderedEnumerable<TSource, TKey>(source, keySelector, keyType, null, false);
    }

    /**
     * Sorts the elements of a sequence in ascending order by using a specified
     * comparer.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            A sequence of values to order.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> orderBy(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType, Comparator<TKey> comparer)
    {
        return new KeyedOrderedEnumerable<TSource, TKey>(source, keySelector, keyType, comparer, false);
    }

    /**
     * Sorts the elements of a sequence in descending order according to a key.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            A sequence of values to order.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> orderByDescending(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType)
    {
        return new KeyedOrderedEnumerable<TSource, TKey>(source, keySelector, keyType, null, true);
    }

    /**
     * Sorts the elements of a sequence in descending order by using a specified
     * comparer.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            A sequence of values to order.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> orderByDescending(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType, Comparator<TKey> comparer)
    {
        return new KeyedOrderedEnumerable<TSource, TKey>(source, keySelector, keyType, comparer, true);
    }

    // endregion

    // region: Then By

    /**
     * Performs a subsequent ordering of the elements in a sequence in ascending
     * order according to a key.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            An {@link IOrderedEnumerable} that contains elements to sort.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> thenBy(IOrderedEnumerable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return source.createOrderedEnumerable(keySelector, keyType, null, false);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in ascending
     * order by using a specified comparer.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            An {@link IOrderedEnumerable} that contains elements to sort.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> thenBy(IOrderedEnumerable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType, Comparator<TKey> comparer)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return source.createOrderedEnumerable(keySelector, keyType, comparer, false);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in
     * descending order, according to a key.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            An {@link IOrderedEnumerable} that contains elements to sort.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> thenByDescending(IOrderedEnumerable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        return source.createOrderedEnumerable(keySelector, keyType, null, true);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in
     * descending order by using a specified comparer.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param source
     *            An {@link IOrderedEnumerable} that contains elements to sort.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    public static <TSource, TKey> IOrderedEnumerable<TSource> thenByDescending(IOrderedEnumerable<TSource> source,
        Function<TSource, TKey> keySelector, Class<TKey> keyType, Comparator<TKey> comparer)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null");
        }

        return source.createOrderedEnumerable(keySelector, keyType, comparer, true);
    }

    // endregion

    // region: Ordered Enumerable Class

    // Reference:
    // http://referencesource.microsoft.com/#System.Core/System/Linq/Enumerable.cs,ffb8de6aefac77cc,references
    // (5/26/2017)

    private static abstract class OrderedEnumerable<TElement> implements IOrderedEnumerable<TElement>
    {
        public OrderedEnumerable(Iterable<TElement> source)
        {
            if (source == null)
            {
                throw new IllegalArgumentException("source is null.");
            }

            this.source = source;
        }

        private final Iterable<TElement> source;

        @Override
        public Iterator<TElement> iterator()
        {
            return getEnumerator();
        }

        @Override
        public IEnumerator<TElement> getEnumerator()
        {
            ArrayList<TElement> items = Linq.toArrayList(source);
            int itemCount = items.size();
            if (itemCount > 0)
            {
                EnumerableSorter<TElement> sorter = getEnumerableSorter(null);
                int[] map = sorter.sort(items, itemCount);
                sorter = null;

                return new SimpleIterator<TElement>()
                {
                    private int index;

                    @Override
                    public boolean moveNext()
                    {
                        if (index < itemCount)
                        {
                            TElement element = items.get(map[index++]);

                            setCurrent(element);
                            return true;
                        }

                        return false;
                    }
                };
            }

            return new EmptyEnumerator<TElement>();
        }

        public abstract EnumerableSorter<TElement> getEnumerableSorter(EnumerableSorter<TElement> next);

        @Override
        public <TKey> IOrderedEnumerable<TElement> createOrderedEnumerable(Function<TElement, TKey> keySelector,
            Class<TKey> keyType, Comparator<TKey> comparer, boolean descending)
        {
            KeyedOrderedEnumerable<TElement, TKey> result
                = new KeyedOrderedEnumerable<>(source, keySelector, keyType, comparer, descending);

            result.parent = this;
            return result;
        }
    }

    private static class KeyedOrderedEnumerable<TElement, TKey> extends OrderedEnumerable<TElement>
    {
        public KeyedOrderedEnumerable(Iterable<TElement> source, Function<TElement, TKey> keySelector,
            Class<TKey> keyType, Comparator<TKey> comparer, boolean descending)
        {
            super(source);

            if (keySelector == null)
            {
                throw new IllegalArgumentException("keySelector is null.");
            }

            this.parent = null;
            this.keySelector = keySelector;
            this.descending = descending;

            if (comparer == null)
            {
                if (keyType == null)
                {
                    throw new IllegalArgumentException("keyType is null.");
                }

                comparer = DefaultComparator.getDefault(keyType);
            }

            this.comparer = comparer;
        }

        private OrderedEnumerable<TElement> parent;
        private final Function<TElement, TKey> keySelector;
        private final Comparator<TKey> comparer;
        private final boolean descending;

        @Override
        public EnumerableSorter<TElement> getEnumerableSorter(EnumerableSorter<TElement> next)
        {
            EnumerableSorter<TElement> sorter
                = new KeyedEnumerableSorter<TElement, TKey>(keySelector, comparer, descending, next);

            if (parent != null)
            {
                sorter = parent.getEnumerableSorter(sorter);
            }

            return sorter;
        }
    }

    private static abstract class EnumerableSorter<TElement>
    {
        public abstract void computeKeys(ArrayList<TElement> elements, int count);

        public abstract int compareKeys(int index1, int index2);

        public int[] sort(ArrayList<TElement> elements, int count)
        {
            computeKeys(elements, count);
            int[] map = new int[count];
            for (int index = 0; index < count; index++)
            {
                map[index] = index;
            }

            quickSort(map, 0, count - 1);
            return map;
        }

        private void quickSort(int[] map, int left, int right)
        {
            do
            {
                int leftIndex = left;
                int rightIndex = right;
                int pivotIndex = map[leftIndex + ((rightIndex - leftIndex) >> 1)];
                do
                {
                    while (leftIndex < map.length && compareKeys(pivotIndex, map[leftIndex]) > 0)
                    {
                        leftIndex++;
                    }
                    while (rightIndex >= 0 && compareKeys(pivotIndex, map[rightIndex]) < 0)
                    {
                        rightIndex--;
                    }
                    if (leftIndex > rightIndex)
                    {
                        break;
                    }
                    if (leftIndex < rightIndex)
                    {
                        int temp = map[leftIndex];
                        map[leftIndex] = map[rightIndex];
                        map[rightIndex] = temp;
                    }
                    leftIndex++;
                    rightIndex--;
                }
                while (leftIndex <= rightIndex);
                if (rightIndex - left <= right - leftIndex)
                {
                    if (left < rightIndex)
                    {
                        quickSort(map, left, rightIndex);
                    }
                    left = leftIndex;
                }
                else
                {
                    if (leftIndex < right)
                    {
                        quickSort(map, leftIndex, right);
                    }
                    right = rightIndex;
                }
            }
            while (left < right);
        }
    }

    private static class KeyedEnumerableSorter<TElement, TKey> extends EnumerableSorter<TElement>
    {
        public KeyedEnumerableSorter(Function<TElement, TKey> keySelector, Comparator<TKey> comparer,
            boolean descending, EnumerableSorter<TElement> next)
        {
            this.keySelector = keySelector;
            this.comparer = comparer;
            this.descending = descending;
            this.next = next;
        }

        private final Function<TElement, TKey> keySelector;
        private final Comparator<TKey> comparer;
        private final boolean descending;
        private final EnumerableSorter<TElement> next;
        private ArrayList<TKey> keys;

        @Override
        public void computeKeys(ArrayList<TElement> elements, int count)
        {
            keys = new ArrayList<TKey>(count);
            for (int index = 0; index < count; index++)
            {
                keys.add(keySelector.apply(elements.get(index)));
            }

            if (next != null)
            {
                next.computeKeys(elements, count);
            }
        }

        @Override
        public int compareKeys(int index1, int index2)
        {
            int order = comparer.compare(keys.get(index1), keys.get(index2));
            if (order == 0)
            {
                if (next == null)
                {
                    return index1 - index2;
                }

                return next.compareKeys(index1, index2);
            }

            return descending ? -order : order;
        }
    }

    // endregion

    // endregion

    // region: Aggregation

    // region: Aggregate

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} over which to aggregate.
     * @param function
     *            An accumulator function to be invoked on each element.
     * @return The final accumulator value.
     */
    public static <TSource> TSource aggregate(Iterable<TSource> source, BiFunction<TSource, TSource, TSource> function)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        TSource current = iterator.next();
        while (iterator.hasNext())
        {
            TSource nextItem = iterator.next();
            current = function.apply(current, nextItem);
        }

        return current;
    }

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TAccumulate>
     *            The type of the accumulator value.
     * @param source
     *            An {@link Iterable} over which to aggregate.
     * @param seed
     *            The initial accumulator value.
     * @param function
     *            An accumulator function to be invoked on each element.
     * @return The final accumulator value.
     */
    public static <TSource, TAccumulate> TAccumulate aggregate(Iterable<TSource> source, TAccumulate seed,
        BiFunction<TAccumulate, TSource, TAccumulate> function)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        TAccumulate current = seed;
        do
        {
            TSource nextItem = iterator.next();
            current = function.apply(current, nextItem);
        }
        while (iterator.hasNext());

        return current;
    }

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param <TAccumulate>
     *            The type of the accumulator value.
     * @param <TResult>
     *            The type of the resulting value.
     * @param source
     *            An {@link Iterable} over which to aggregate.
     * @param seed
     *            The initial accumulator value.
     * @param function
     *            An accumulator function to be invoked on each element.
     * @param resultSelector
     *            A function to transform the final accumulator value into the
     *            result value.
     * @return The transformed final accumulator value.
     */
    public static <TSource, TAccumulate, TResult> TResult aggregate(Iterable<TSource> source, TAccumulate seed,
        BiFunction<TAccumulate, TSource, TAccumulate> function, Function<TAccumulate, TResult> resultSelector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (function == null)
        {
            throw new IllegalArgumentException("function is null.");
        }
        if (resultSelector == null)
        {
            throw new IllegalArgumentException("resultSelector is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        TAccumulate current = seed;
        do
        {
            TSource nextItem = iterator.next();
            current = function.apply(current, nextItem);
        }
        while (iterator.hasNext());

        return resultSelector.apply(current);
    }

    // endregion

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

    // region: Element At

    /**
     * Returns the element at a specified index in a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return an element.
     * @param index
     *            The zero-based index of the element to retrieve.
     * @return The element at the specified position in the source sequence.
     */
    public static <TSource> TSource elementAt(Iterable<TSource> source, int index)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (index < 0)
        {
            throw new IndexOutOfBoundsException("index is out of range.");
        }

        if (source instanceof List<?>)
        {
            return ((List<TSource>) source).get(index);
        }

        Iterator<TSource> iterator = source.iterator();
        while (true)
        {
            if (iterator.hasNext() == false)
            {
                throw new IndexOutOfBoundsException("index is out of range.");
            }

            TSource value = iterator.next();

            if (index == 0)
            {
                return value;
            }

            index--;
        }
    }

    /**
     * Returns the element at a specified index in a sequence or a default value
     * if the index is out of range.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return an element.
     * @param index
     *            The zero-based index of the element to retrieve.
     * @return <code>null</code> if the index is outside the bounds of the
     *         source sequence; otherwise, the element at the specified position
     *         in the source sequence.
     */
    public static <TSource> TSource elementAtOrDefault(Iterable<TSource> source, int index)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (index >= 0)
        {
            if (source instanceof List<?>)
            {
                List<TSource> list = (List<TSource>) source;
                if (list != null && index < list.size())
                {
                    return list.get(index);
                }
            }
            else
            {
                Iterator<TSource> iterator = source.iterator();
                while (true)
                {
                    if (iterator.hasNext() == false)
                    {
                        break;
                    }

                    TSource value = iterator.next();

                    if (index == 0)
                    {
                        return value;
                    }

                    index--;
                }
            }
        }

        return null;
    }

    // endregion

    // region: First

    /**
     * Returns the first element of a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the first element.
     * @return The first element in the specified sequence.
     */
    public static <TSource> TSource first(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("The source sequence is empty.");
        }

        return source.iterator().next();
    }

    /**
     * Returns the first element of a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the first element.
     * @param predicate
     *            A function to test each element for a condition.
     * @return The first element in the sequence that passes the test in the
     *         specified predicate function.
     */
    public static <TSource> TSource first(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }
        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("The source sequence is empty.");
        }

        for (TSource item : source)
        {
            if (predicate.apply(item))
            {
                return item;
            }
        }

        throw new IllegalStateException("No element satisfies the condition in predicate.");
    }

    /**
     * Returns the first element of a sequence, or a default value if the
     * sequence contains no elements.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the first element.
     * @return <code>null</code> if <code>source</code> is empty; otherwise, the
     *         first element in <code>source</code>.
     */
    public static <TSource> TSource firstOrDefault(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            return null;
        }

        return source.iterator().next();
    }

    /**
     * Returns the first element of the sequence that satisfies a condition or a
     * default value if no such element is found.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the first element.
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>null</code> if <code>source</code> is empty or if no
     *         element passes the test specified by <code>predicate</code>;
     *         otherwise, the first element in <code>source</code> that passes
     *         the test specified by <code>predicate</code>.
     */
    public static <TSource> TSource firstOrDefault(Iterable<TSource> source, Function<TSource, Boolean> predicate)
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
            if (predicate.apply(item))
            {
                return item;
            }
        }

        return null;
    }

    // endregion

    // region: Last

    /**
     * Returns the last element of a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the last element.
     * @return The last element in the specified sequence.
     */
    public static <TSource> TSource last(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        Iterator<TSource> iterator = source.iterator();
        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("The source sequence is empty.");
        }

        if (source instanceof List<?>)
        {
            List<TSource> sourceList = (List<TSource>) source;
            return sourceList.get(sourceList.size() - 1);
        }

        TSource item = null;
        do
        {
            item = iterator.next();
        }
        while (iterator.hasNext());

        return item;
    }

    /**
     * Returns the last element of a sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the last element.
     * @param predicate
     *            A function to test each element for a condition.
     * @return The last element in the sequence that passes the test in the
     *         specified predicate function.
     */
    public static <TSource> TSource last(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        Iterator<TSource> iterator = source.iterator();
        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("The source sequence is empty.");
        }

        TSource item = null;
        boolean hasMatched = false;
        do
        {
            TSource current = iterator.next();
            if (predicate.apply(current))
            {
                hasMatched = true;
                item = current;
            }
        }
        while (iterator.hasNext());

        if (hasMatched)
        {
            return item;
        }

        throw new IllegalStateException("No element satisfies the condition in predicate.");
    }

    /**
     * Returns the last element of a sequence, or a default value if the
     * sequence contains no elements.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the last element.
     * @return <code>null</code> if <code>source</code> is empty; otherwise, the
     *         last element in the {@link Iterable}.
     */
    public static <TSource> TSource lastOrDefault(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        Iterator<TSource> iterator = source.iterator();
        if (iterator.hasNext() == false)
        {
            return null;
        }

        if (source instanceof List<?>)
        {
            List<TSource> sourceList = (List<TSource>) source;
            return sourceList.get(sourceList.size() - 1);
        }

        TSource item = null;
        do
        {
            item = iterator.next();
        }
        while (iterator.hasNext());

        return item;
    }

    /**
     * Returns the last element of the sequence that satisfies a condition or a
     * default value if no such element is found.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            The {@link Iterable} of which to return the last element.
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>null</code> if <code>source</code> is empty or if no
     *         element passes the test specified by <code>predicate</code>;
     *         otherwise, the last element in <code>source</code> that passes
     *         the test specified by <code>predicate</code>.
     */
    public static <TSource> TSource lastOrDefault(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        Iterator<TSource> iterator = source.iterator();
        if (iterator.hasNext() == false)
        {
            return null;
        }

        TSource item = null;
        do
        {
            TSource current = iterator.next();
            if (predicate.apply(current))
            {
                item = current;
            }
        }
        while (iterator.hasNext());

        return item;
    }

    // endregion

    // region: Sequence Equal

    /**
     * Determines whether two sequences are equal by comparing the elements by
     * using the default equality comparer for their type.
     *
     * @param <TSource>
     *            The type of the elements of the input sequences.
     * @param first
     *            An {@link Iterable} to compare to second.
     * @param second
     *            An {@link Iterable} to compare to the first sequence.
     * @return <code>true</code> if the two source sequences are of equal length
     *         and their corresponding elements are equal according to the
     *         default equality comparer for their type; otherwise,
     *         <code>false</code>.
     */
    public static <TSource> boolean sequenceEqual(Iterable<TSource> first, Iterable<TSource> second)
    {
        if (first == null)
        {
            throw new IllegalArgumentException("first is null.");
        }
        if (second == null)
        {
            throw new IllegalArgumentException("second is null.");
        }

        Iterator<TSource> firstIterator = first.iterator();
        Iterator<TSource> secondIterator = second.iterator();

        while (firstIterator.hasNext())
        {
            if (secondIterator.hasNext() == false)
            {
                return false;
            }

            TSource firstItem = firstIterator.next();
            TSource secondItem = secondIterator.next();

            if (firstItem == null)
            {
                if (secondItem == null)
                {
                    continue;
                }

                return false;
            }

            if (firstItem.equals(secondItem) == false)
            {
                return false;
            }
        }

        return true;
    }

    // endregion

    // region: Single

    /**
     * Returns the only element of a sequence, and throws an exception if there
     * is not exactly one element in the sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} of which to return the single element.
     * @return The single element of the input sequence.
     */
    public static <TSource> TSource single(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("The input sequence is empty.");
        }

        TSource item = iterator.next();

        if (iterator.hasNext())
        {
            throw new IllegalStateException("The input sequence contains more than one element.");
        }

        return item;
    }

    /**
     * Returns the only element of a sequence that satisfies a specified
     * condition, and throws an exception if more than one such element exists.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return a single element.
     * @param predicate
     *            A function to test an element for a condition.
     * @return The single element of the input sequence that satisfies a
     *         condition.
     */
    public static <TSource> TSource single(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            throw new IllegalStateException("The source sequence is empty.");
        }

        TSource current, item = null;
        boolean hasBeenFound = false;

        do
        {
            current = iterator.next();
            if (predicate.apply(current))
            {
                if (hasBeenFound)
                {
                    throw new IllegalStateException(
                        "More than one element satisfies the condition in predicate.");
                }

                item = current;
                hasBeenFound = true;
            }
        }
        while (iterator.hasNext());

        if (hasBeenFound == false)
        {
            throw new IllegalStateException("No element satisfies the condition in predicate.");
        }

        return item;
    }

    /**
     * Returns the only element of a sequence, or a default value if the
     * sequence is empty; this method throws an exception if there is more than
     * one element in the sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} of which to return the single element.
     * @return The single element of the input sequence, or <code>null</code> if
     *         the sequence contains no elements.
     */
    public static <TSource> TSource singleOrDefault(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            return null;
        }

        TSource item = iterator.next();

        if (iterator.hasNext())
        {
            throw new IllegalStateException("The input sequence contains more than one element.");
        }

        return item;
    }

    /**
     * Returns the only element of a sequence that satisfies a specified
     * condition or a default value if no such element exists; this method
     * throws an exception if more than one element satisfies the condition.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to return a single element.
     * @param predicate
     *            A function to test an element for a condition.
     * @return The single element of the input sequence that satisfies the
     *         condition, or <code>null</code> if no such element is found.
     */
    public static <TSource> TSource singleOrDefault(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        Iterator<TSource> iterator = source.iterator();

        if (iterator.hasNext() == false)
        {
            return null;
        }

        TSource current, item = null;
        boolean hasBeenFound = false;

        do
        {
            current = iterator.next();
            if (predicate.apply(current))
            {
                if (hasBeenFound)
                {
                    throw new IllegalStateException(
                        "More than one element satisfies the condition in predicate.");
                }

                item = current;
                hasBeenFound = true;
            }
        }
        while (iterator.hasNext());

        if (hasBeenFound == false)
        {
            return null;
        }

        return item;
    }

    // endregion

    // region: To Array

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param source
     *            An {@link Iterable} from which to create an array.
     * @return An array that contains the elements from the input sequence.
     */
    public static Object[] toArray(Iterable<?> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        ArrayList<?> list = toArrayList(source);

        return list.toArray();
    }

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to create an array.
     * @param array
     *            The array to fill.
     * @return An array that contains the elements from the input sequence.
     */
    public static <TSource> TSource[] toArray(Iterable<TSource> source, TSource[] array)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (array == null)
        {
            throw new IllegalArgumentException("array is null.");
        }

        int index = 0;
        for (TSource element : source)
        {
            if (index < array.length)
            {
                array[index++] = element;
            }
            else
            {
                break;
            }
        }

        return array;
    }

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            An {@link Iterable} from which to create an array.
     * @param selector
     *            Creates an array given the number of elements.
     * @return An array that contains the elements from the input sequence.
     */
    public static <TSource> TSource[] toArray(Iterable<TSource> source, Function<Integer, TSource[]> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        ArrayList<TSource> list = toArrayList(source);

        TSource[] newArray = selector.apply(list.size());
        return list.toArray(newArray);
    }

    // endregion

    // region: To Array List

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

    // endregion

    // region: To Hash Map

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
    public static <TSource, TKey> HashMap<TKey, TSource> toHashMap(Iterable<TSource> source,
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

        HashMap<TKey, TSource> hashMap = new HashMap<TKey, TSource>();
        for (TSource value : source)
        {
            TKey key = keySelector.apply(value);
            hashMap.put(key, value);
        }

        return hashMap;
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
    public static <TSource, TKey, TElement> HashMap<TKey, TElement> toHashMap(Iterable<TSource> source,
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

        HashMap<TKey, TElement> hashMap = new HashMap<TKey, TElement>();
        for (TSource item : source)
        {
            TKey key = keySelector.apply(item);
            TElement value = elementSelector.apply(item);
            hashMap.put(key, value);
        }

        return hashMap;
    }

    // endregion

    // endregion

    // region: Metrics

    // region: Average

    /**
     * Computes the average of a sequence of {@link Double} values.
     *
     * @param source
     *            A sequence of {@link Double} values of which to calculate the
     *            average.
     * @return The average of the sequence of values.
     */
    public static double averageDouble(Iterable<Double> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Double sum = 0d;
        int count = 0;

        for (Double value : source)
        {
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of the sequence of {@link Double} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the average.
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    public static <TSource> double averageDouble(Iterable<TSource> source, Function<TSource, Double> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Double sum = 0d;
        int count = 0;

        for (TSource item : source)
        {
            Double value = selector.apply(item);
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of a sequence of {@link Float} values.
     *
     * @param source
     *            A sequence of {@link Float} values of which to calculate the
     *            average.
     * @return The average of the sequence of values.
     */
    public static float averageFloat(Iterable<Float> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Float sum = 0f;
        int count = 0;

        for (Float value : source)
        {
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of the sequence of {@link Float} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the average.
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    public static <TSource> float averageFloat(Iterable<TSource> source, Function<TSource, Float> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Float sum = 0f;
        int count = 0;

        for (TSource item : source)
        {
            Float value = selector.apply(item);
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of a sequence of {@link Integer} values.
     *
     * @param source
     *            A sequence of {@link Integer} values of which to calculate the
     *            average.
     * @return The average of the sequence of values.
     */
    public static int averageInteger(Iterable<Integer> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Integer sum = 0;
        int count = 0;

        for (Integer value : source)
        {
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of the sequence of {@link Integer} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the average.
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    public static <TSource> int averageInteger(Iterable<TSource> source, Function<TSource, Integer> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Integer sum = 0;
        int count = 0;

        for (TSource item : source)
        {
            Integer value = selector.apply(item);
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of a sequence of {@link Long} values.
     *
     * @param source
     *            A sequence of {@link Long} values of which to calculate the
     *            average.
     * @return The average of the sequence of values.
     */
    public static long averageLong(Iterable<Long> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Long sum = 0l;
        int count = 0;

        for (Long value : source)
        {
            count++;
            sum += value;
        }

        return sum / count;
    }

    /**
     * Computes the average of the sequence of {@link Long} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the average.
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    public static <TSource> long averageLong(Iterable<TSource> source, Function<TSource, Long> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Long sum = 0l;
        int count = 0;

        for (TSource item : source)
        {
            Long value = selector.apply(item);
            count++;
            sum += value;
        }

        return sum / count;
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

    // region: Max

    /**
     * Returns the maximum value in a sequence of {@link Double} values.
     *
     * @param source
     *            A sequence of {@link Double} values of which to determine the
     *            maximum value.
     * @return The maximum value in the sequence.
     */
    public static double maxDouble(Iterable<Double> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        double maxValue = Double.MIN_VALUE;
        for (Double value : source)
        {
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Double} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the maximum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    public static <TSource> double maxDouble(Iterable<TSource> source, Function<TSource, Double> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        double maxValue = Double.MIN_VALUE;
        for (TSource item : source)
        {
            Double value = selector.apply(item);
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Returns the maximum value in a sequence of {@link Float} values.
     *
     * @param source
     *            A sequence of {@link Float} values of which to determine the
     *            maximum value.
     * @return The maximum value in the sequence.
     */
    public static float maxFloat(Iterable<Float> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        float maxValue = Float.MIN_VALUE;
        for (Float value : source)
        {
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Float} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the maximum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    public static <TSource> float maxFloat(Iterable<TSource> source, Function<TSource, Float> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        float maxValue = Float.MIN_VALUE;
        for (TSource item : source)
        {
            Float value = selector.apply(item);
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Returns the maximum value in a sequence of {@link Integer} values.
     *
     * @param source
     *            A sequence of {@link Integer} values of which to determine the
     *            maximum value.
     * @return The maximum value in the sequence.
     */
    public static int maxInteger(Iterable<Integer> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        int maxValue = Integer.MIN_VALUE;
        for (Integer value : source)
        {
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Integer} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the maximum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    public static <TSource> int maxInteger(Iterable<TSource> source, Function<TSource, Integer> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        int maxValue = Integer.MIN_VALUE;
        for (TSource item : source)
        {
            Integer value = selector.apply(item);
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Returns the maximum value in a sequence of {@link Long} values.
     *
     * @param source
     *            A sequence of {@link Long} values of which to determine the
     *            maximum value.
     * @return The maximum value in the sequence.
     */
    public static long maxLong(Iterable<Long> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        long maxValue = Long.MIN_VALUE;
        for (Long value : source)
        {
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Long} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the maximum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    public static <TSource> long maxLong(Iterable<TSource> source, Function<TSource, Long> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        long maxValue = Long.MIN_VALUE;
        for (TSource item : source)
        {
            Long value = selector.apply(item);
            if (value > maxValue)
            {
                maxValue = value;
            }
        }

        return maxValue;
    }

    // endregion

    // region: Min

    /**
     * Returns the minimum value in a sequence of {@link Double} values.
     *
     * @param source
     *            A sequence of {@link Double} values of which to determine the
     *            minimum value.
     * @return The minimum value in the sequence.
     */
    public static double minDouble(Iterable<Double> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        double minValue = Double.MAX_VALUE;
        for (Double value : source)
        {
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Double} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the minimum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    public static <TSource> double minDouble(Iterable<TSource> source, Function<TSource, Double> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        double minValue = Double.MAX_VALUE;
        for (TSource item : source)
        {
            Double value = selector.apply(item);
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Returns the minimum value in a sequence of {@link Float} values.
     *
     * @param source
     *            A sequence of {@link Float} values of which to determine the
     *            minimum value.
     * @return The minimum value in the sequence.
     */
    public static float minFloat(Iterable<Float> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        float minValue = Float.MAX_VALUE;
        for (Float value : source)
        {
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Float} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the minimum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    public static <TSource> float minFloat(Iterable<TSource> source, Function<TSource, Float> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        float minValue = Float.MAX_VALUE;
        for (TSource item : source)
        {
            Float value = selector.apply(item);
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Returns the minimum value in a sequence of {@link Integer} values.
     *
     * @param source
     *            A sequence of {@link Integer} values of which to determine the
     *            minimum value.
     * @return The minimum value in the sequence.
     */
    public static int minInteger(Iterable<Integer> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        int minValue = Integer.MAX_VALUE;
        for (Integer value : source)
        {
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Integer} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the minimum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    public static <TSource> int minInteger(Iterable<TSource> source, Function<TSource, Integer> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        int minValue = Integer.MAX_VALUE;
        for (TSource item : source)
        {
            Integer value = selector.apply(item);
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Returns the minimum value in a sequence of {@link Long} values.
     *
     * @param source
     *            A sequence of {@link Long} values of which to determine the
     *            minimum value.
     * @return The minimum value in the sequence.
     */
    public static long minLong(Iterable<Long> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        long minValue = Long.MAX_VALUE;
        for (Long value : source)
        {
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Long} value.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to determine the minimum value.
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    public static <TSource> long minLong(Iterable<TSource> source, Function<TSource, Long> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        long minValue = Long.MAX_VALUE;
        for (TSource item : source)
        {
            Long value = selector.apply(item);
            if (value < minValue)
            {
                minValue = value;
            }
        }

        return minValue;
    }

    // endregion

    // region: Sum

    /**
     * Computes the sum of a sequence of {@link Double} values.
     *
     * @param source
     *            A sequence of {@link Double} values of which to calculate the
     *            sum.
     * @return The sum of the values in the sequence.
     */
    public static double sumDouble(Iterable<Double> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Double sum = 0d;

        for (Double value : source)
        {
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of the sequence of {@link Double} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the sum.
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    public static <TSource> double sumDouble(Iterable<TSource> source, Function<TSource, Double> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Double sum = 0d;

        for (TSource item : source)
        {
            Double value = selector.apply(item);
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of a sequence of {@link Float} values.
     *
     * @param source
     *            A sequence of {@link Float} values of which to calculate the
     *            sum.
     * @return The sum of the values in the sequence.
     */
    public static float sumFloat(Iterable<Float> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Float sum = 0f;

        for (Float value : source)
        {
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of the sequence of {@link Float} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the sum.
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    public static <TSource> float sumFloat(Iterable<TSource> source, Function<TSource, Float> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Float sum = 0f;

        for (TSource item : source)
        {
            Float value = selector.apply(item);
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of a sequence of {@link Integer} values.
     *
     * @param source
     *            A sequence of {@link Integer} values of which to calculate the
     *            sum.
     * @return The sum of the values in the sequence.
     */
    public static int sumInteger(Iterable<Integer> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Integer sum = 0;

        for (Integer value : source)
        {
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of the sequence of {@link Integer} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the sum.
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    public static <TSource> int sumInteger(Iterable<TSource> source, Function<TSource, Integer> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Integer sum = 0;

        for (TSource item : source)
        {
            Integer value = selector.apply(item);
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of a sequence of {@link Long} values.
     *
     * @param source
     *            A sequence of {@link Long} values of which to calculate the
     *            sum.
     * @return The sum of the values in the sequence.
     */
    public static long sumLong(Iterable<Long> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Long sum = 0l;

        for (Long value : source)
        {
            sum += value;
        }

        return sum;
    }

    /**
     * Computes the sum of the sequence of {@link Long} values that are obtained
     * by invoking a transform function on each element of the input sequence.
     *
     * @param <TSource>
     *            The type of the elements of <code>source</code>.
     * @param source
     *            A sequence of values of which to calculate the sum.
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    public static <TSource> long sumLong(Iterable<TSource> source, Function<TSource, Long> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        if (source.iterator().hasNext() == false)
        {
            throw new IllegalStateException("source contains no elements.");
        }

        Long sum = 0l;

        for (TSource item : source)
        {
            Long value = selector.apply(item);
            sum += value;
        }

        return sum;
    }

    // endregion

    // endregion
}
