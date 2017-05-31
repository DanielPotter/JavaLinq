package potter.linq;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Exposes the enumerator, which supports a simple iteration over a collection
 * of a specified type.
 *
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to enumerate.
 */
public interface IEnumerable<T> extends Iterable<T>
{
    /**
     * Returns an enumerator that iterates through the collection.
     *
     * @return An enumerator that can be used to iterate through the collection.
     */
    default IEnumerator<T> getEnumerator()
    {
        return IEnumerator.wrap(iterator());
    }

    // region: Mutation

    // region: Concat

    /**
     * Concatenates two sequences.
     *
     * @param second
     *            The sequence to concatenate to the first sequence.
     * @return An {@link Iterable} that contains the concatenated elements of
     *         the two input sequences.
     */
    default IEnumerable<T> concat(Iterable<T> second)
    {
        return Linq.concat(this, second);
    }

    // endregion

    // region: Distinct

    /**
     * Returns distinct elements from a sequence by using the default equality
     * comparer to compare values.
     *
     * @return An {@link IEnumerable} that contains distinct elements from the
     *         source sequence.
     */
    default IEnumerable<T> distinct(Iterable<T> source)
    {
        return Linq.distinct(this);
    }

    // endregion

    // region: Except

    /**
     * Produces the set difference of two sequences by using the default
     * equality comparer to compare values.
     *
     * @param second
     *            An {@link Iterable} whose elements that also occur in the
     *            first sequence will cause those elements to be removed from
     *            the returned sequence.
     * @return A sequence that contains the set difference of the elements of
     *         two sequences.
     */
    default IEnumerable<T> except(Iterable<T> second)
    {
        return Linq.except(this, second);
    }

    // endregion

    // region: Intersect

    /**
     * Produces the set intersection of two sequences by using the default
     * equality comparer to compare values.
     *
     * @param second
     *            An {@link Iterable} whose distinct elements that also appear
     *            in the first sequence will be returned.
     * @return A sequence that contains the elements that form the set
     *         intersection of two sequences.
     */
    default IEnumerable<T> intersect(Iterable<T> second)
    {
        return Linq.intersect(this, second);
    }

    // endregion

    // region: Of Type

    /**
     * Filters the elements of an {@link Iterable} based on a specified type.
     *
     * @param <TResult>
     *            The type on which to filter the elements of the sequence.
     * @param type
     *            The type of elements to filter
     * @return An {@link Iterable} that contains elements from the input
     *         sequence of type <code>type</code>.
     */
    default <TResult> IEnumerable<TResult> ofType(Class<TResult> type)
    {
        return Linq.ofType(this, type);
    }

    // endregion

    // region: Reverse

    /**
     * Inverts the order of the elements in a sequence.
     *
     * @return A sequence whose elements correspond to those of the input
     *         sequence in reverse order.
     */
    default IEnumerable<T> reverse()
    {
        return Linq.reverse(this);
    }

    // endregion

    // region: Select

    /**
     * Projects each element of a sequence into a new form.
     *
     * @param <TResult>
     *            The type of the value returned by <code>selector</code>.
     * @param selector
     *            A transform function to apply to each element.
     * @return An {@link Iterable} whose elements are the result of invoking the
     *         transform function on each element of <code>source</code>.
     */
    default <TResult> IEnumerable<TResult> select(Function<T, TResult> selector)
    {
        return Linq.select(this, selector);
    }

    /**
     * Projects each element of a sequence into a new form by incorporating the
     * element's index.
     *
     * @param <TResult>
     *            The type of the value returned by <code>selector</code>.
     * @param selector
     *            A transform function to apply to each source element; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link Iterable} whose elements are the result of invoking the
     *         transform function on each element of <code>source</code>.
     */
    default <TResult> IEnumerable<TResult> select(BiFunction<T, Integer, TResult> selector)
    {
        return Linq.select(this, selector);
    }

    // endregion

    // region: Select Many

    /**
     * Projects each element of a sequence to an {@link IEnumerable} and
     * flattens the resulting sequences into one sequence.
     *
     * @param <TResult>
     *            The type of the elements of the sequence returned by
     *            <code>selector</code>.
     * @param selector
     *            A transform function to apply to each element.
     * @return An {@link IEnumerable} whose elements are the result of invoking
     *         the one-to-many transform function on each element of the input
     *         sequence.
     */
    default <TResult> IEnumerable<TResult> selectMany(Function<T, Iterable<TResult>> selector)
    {
        return Linq.selectMany(this, selector);
    }

    /**
     * Projects each element of a sequence to an {@link IEnumerable}, and
     * flattens the resulting sequences into one sequence. The index of each
     * source element is used in the projected form of that element.
     *
     * @param <TResult>
     *            The type of the elements of the sequence returned by
     *            <code>selector</code>.
     * @param selector
     *            A transform function to apply to each source element; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} whose elements are the result of invoking
     *         the one-to-many transform function on each element of an input
     *         sequence.
     */
    default <TResult> IEnumerable<TResult> selectMany(BiFunction<T, Integer, Iterable<TResult>> selector)
    {
        return Linq.selectMany(this, selector);
    }

    // endregion

    // region: Skip

    /**
     * Bypasses a specified number of elements in a sequence and then returns
     * the remaining elements.
     *
     * @param count
     *            The number of elements to skip before returning the remaining
     *            elements.
     * @return An {@link IEnumerable} that contains the elements that occur
     *         after the specified index in the input sequence.
     */
    default IEnumerable<T> skip(int count)
    {
        return Linq.skip(this, count);
    }

    /**
     * Bypasses elements in a sequence as long as a specified condition is true
     * and then returns the remaining elements.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence starting at the first element in the linear series that
     *         does not pass the test specified by <code>predicate</code>.
     */
    default IEnumerable<T> skipWhile(Function<T, Boolean> predicate)
    {
        return Linq.skipWhile(this, predicate);
    }

    /**
     * Bypasses elements in a sequence as long as a specified condition is true
     * and then returns the remaining elements. The element's index is used in
     * the logic of the predicate function.
     *
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence starting at the first element in the linear series that
     *         does not pass the test specified by <code>predicate</code>.
     */
    default IEnumerable<T> skipWhile(BiFunction<T, Integer, Boolean> predicate)
    {
        return Linq.skipWhile(this, predicate);
    }

    // endregion

    // region: Take

    /**
     * Returns a specified number of contiguous elements from the start of a
     * sequence.
     *
     * @param count
     *            The number of elements to return.
     * @return An {@link IEnumerable} that contains the specified number of
     *         elements from the start of the input sequence.
     */
    default IEnumerable<T> take(int count)
    {
        return Linq.take(this, count);
    }

    /**
     * Returns elements from a sequence as long as a specified condition is
     * true.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence that occur before the element at which the test no
     *         longer passes.
     */
    default IEnumerable<T> takeWhile(Function<T, Boolean> predicate)
    {
        return Linq.takeWhile(this, predicate);
    }

    /**
     * Returns elements from a sequence as long as a specified condition is
     * true. The element's index is used in the logic of the predicate function.
     *
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link IEnumerable} that contains the elements from the input
     *         sequence that occur before the element at which the test no
     *         longer passes.
     */
    default IEnumerable<T> takeWhile(BiFunction<T, Integer, Boolean> predicate)
    {
        return Linq.takeWhile(this, predicate);
    }

    // endregion

    // region: Union

    /**
     * Produces the set union of two sequences by using the default equality
     * comparer.
     *
     * @param second
     *            An {@link Iterable} whose distinct elements form the second
     *            set for the union.
     * @return An {@link IEnumerable} that contains the elements from both input
     *         sequences, excluding duplicates.
     */
    default IEnumerable<T> union(Iterable<T> second)
    {
        return Linq.union(this, second);
    }

    // endregion

    // region: Where

    /**
     * Filters a sequence of values based on a predicate.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return An {@link Iterable} that contains elements from the input
     *         sequence that satisfy the condition.
     */
    default IEnumerable<T> where(Function<T, Boolean> predicate)
    {
        return Linq.where(this, predicate);
    }

    /**
     * Filters a sequence of values based on a predicate. Each element's index
     * is used in the logic of the predicate function.
     *
     * @param predicate
     *            A function to test each source element for a condition; the
     *            second parameter of the function represents the index of the
     *            source element.
     * @return An {@link Iterable} that contains elements from the input
     *         sequence that satisfy the condition.
     */
    default IEnumerable<T> where(BiFunction<T, Integer, Boolean> predicate)
    {
        return Linq.where(this, predicate);
    }

    // endregion

    // region: Zip

    /**
     * Applies a specified function to the corresponding elements of two
     * sequences, producing a sequence of the results.
     *
     * @param <TSecond>
     *            The type of the elements of the second input sequence.
     * @param <TResult>
     *            The type of the elements of the result sequence.
     * @param second
     *            The second sequence to merge.
     * @param resultSelector
     *            A function that specifies how to merge the elements from the
     *            two sequences.
     * @return An {@link IEnumerable} that contains merged elements of two input
     *         sequences.
     */
    default <TSecond, TResult> IEnumerable<TResult> zip(Iterable<TSecond> second,
        BiFunction<T, TSecond, TResult> resultSelector)
    {
        return Linq.zip(this, second, resultSelector);
    }

    // endregion

    // endregion

    // region: Sorting

    // region: Order By

    /**
     * Sorts the elements of a sequence in ascending order according to a key.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    default <TKey> IOrderedEnumerable<T> orderBy(Function<T, TKey> keySelector, Class<TKey> keyType)
    {
        return Linq.orderBy(this, keySelector, keyType);
    }

    /**
     * Sorts the elements of a sequence in ascending order by using a specified
     * comparer.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    default <TKey> IOrderedEnumerable<T> orderBy(Function<T, TKey> keySelector, Class<TKey> keyType,
        Comparator<TKey> comparer)
    {
        return Linq.orderBy(this, keySelector, keyType, comparer);
    }

    /**
     * Sorts the elements of a sequence in descending order according to a key.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    default <TKey> IOrderedEnumerable<T> orderByDescending(Function<T, TKey> keySelector, Class<TKey> keyType)
    {
        return Linq.orderByDescending(this, keySelector, keyType);
    }

    /**
     * Sorts the elements of a sequence in descending order by using a specified
     * comparer.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from an element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    default <TKey> IOrderedEnumerable<T> orderByDescending(Function<T, TKey> keySelector, Class<TKey> keyType,
        Comparator<TKey> comparer)
    {
        return Linq.orderByDescending(this, keySelector, keyType, comparer);
    }

    // endregion

    // endregion

    // region: Grouping

    // region: Group By

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return An {@link IEnumerable} where each {@link IGrouping} object
     *         contains a sequence of objects and a key.
     */
    default <TKey> IEnumerable<IGrouping<TKey, T>> groupBy(
        Function<T, TKey> keySelector,
        Class<TKey> keyType,
        Class<T> elementType)
    {
        return Linq.groupBy(this, keySelector, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and compares the keys by using a specified comparer.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param comparer
     *            An {@link IEqualityComparer} with which to compare keys.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return An {@link IEnumerable} where each {@link IGrouping} object
     *         contains a sequence of objects and a key.
     */
    default <TKey> IEnumerable<IGrouping<TKey, T>> groupBy(
        Function<T, TKey> keySelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<T> elementType)
    {
        return Linq.groupBy(this, keySelector, comparer, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and projects the elements for each group by using a specified
     * function.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the elements in the {@link IGrouping}.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param elementSelector
     *            A function to map each source element to an element in the
     *            {@link IGrouping}.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements in the {@link IGrouping}.
     * @return An {@link IEnumerable} where each {@link IGrouping} object
     *         contains a collection of objects of type <code>TElement</code>
     *         and a key.
     */
    default <TKey, TElement> IEnumerable<IGrouping<TKey, TElement>> groupBy(
        Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        return Linq.groupBy(this, keySelector, elementSelector, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a key selector function.
     * The keys are compared by using a comparer and each group's elements are
     * projected by using a specified function.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the elements in the {@link IGrouping}.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param elementSelector
     *            A function to map each source element to an element in the
     *            {@link IGrouping}.
     * @param comparer
     *            An {@link IEqualityComparer} with which to compare keys.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements in the {@link IGrouping}.
     * @return An {@link IEnumerable} where each {@link IGrouping} object
     *         contains a collection of objects of type <code>TElement</code>
     *         and a key.
     */
    default <TKey, TElement> IEnumerable<IGrouping<TKey, TElement>> groupBy(
        Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        return Linq.groupBy(this, keySelector, elementSelector, comparer, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and creates a result value from each group and its key.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TResult>
     *            The type of the result value returned by
     *            <code>resultSelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param resultSelector
     *            A function to create a result value from each group.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return A collection of elements of type <code>TResult</code> where each
     *         element represents a projection over a group and its key.
     */
    default <TKey, TResult> IEnumerable<TResult> groupBy(
        Function<T, TKey> keySelector,
        BiFunction<TKey, IEnumerable<T>, TResult> resultSelector,
        Class<TKey> keyType,
        Class<T> elementType)
    {
        return Linq.groupBy(this, keySelector, resultSelector, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and creates a result value from each group and its key. The keys
     * are compared by using a specified comparer.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TResult>
     *            The type of the result value returned by
     *            <code>resultSelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param resultSelector
     *            A function to create a result value from each group.
     * @param comparer
     *            An {@link IEqualityComparer} with which to compare keys.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return A collection of elements of type <code>TResult</code> where each
     *         element represents a projection over a group and its key.
     */
    default <TKey, TResult> IEnumerable<TResult> groupBy(
        Function<T, TKey> keySelector,
        BiFunction<TKey, IEnumerable<T>, TResult> resultSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<T> elementType)
    {
        return Linq.groupBy(this, keySelector, resultSelector, comparer, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and creates a result value from each group and its key. The
     * elements of each group are projected by using a specified function.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the elements in the {@link IGrouping}.
     * @param <TResult>
     *            The type of the result value returned by
     *            <code>resultSelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param elementSelector
     *            A function to map each source element to an element in the
     *            {@link IGrouping}.
     * @param resultSelector
     *            A function to create a result value from each group.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return A collection of elements of type <code>TResult</code> where each
     *         element represents a projection over a group and its key.
     */
    default <TKey, TElement, TResult> IEnumerable<TResult> groupBy(
        Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector,
        BiFunction<TKey, IEnumerable<TElement>, TResult> resultSelector,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        return Linq.groupBy(this, keySelector, elementSelector, resultSelector, keyType, elementType);
    }

    /**
     * Groups the elements of a sequence according to a specified key selector
     * function and creates a result value from each group and its key. Key
     * values are compared by using a specified comparer, and the elements of
     * each group are projected by using a specified function.
     * 
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the elements in the {@link IGrouping}.
     * @param <TResult>
     *            The type of the result value returned by
     *            <code>resultSelector</code>.
     * @param keySelector
     *            A function to extract the key for each element.
     * @param elementSelector
     *            A function to map each source element to an element in the
     *            {@link IGrouping}.
     * @param resultSelector
     *            A function to create a result value from each group.
     * @param comparer
     *            An {@link IEqualityComparer} with which to compare keys.
     * @param keyType
     *            The type of the key returned by <code>keySelector</code>.
     * @param elementType
     *            The type of the elements of <code>source</code>.
     * @return A collection of elements of type <code>TResult</code> where each
     *         element represents a projection over a group and its key.
     */
    default <TKey, TElement, TResult> IEnumerable<TResult> groupBy(
        Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector,
        BiFunction<TKey, IEnumerable<TElement>, TResult> resultSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        return Linq.groupBy(this, keySelector, elementSelector, resultSelector, comparer, keyType, elementType);
    }

    // endregion

    // region: Group Join

    /**
     * Correlates the elements of two sequences based on equality of keys and
     * groups the results. The default equality comparer is used to compare
     * keys.
     * 
     * @param <TInner>
     *            The type of the elements of the second sequence.
     * @param <TKey>
     *            The type of the keys returned by the key selector functions.
     * @param <TResult>
     *            The type of the result elements.
     * @param inner
     *            The sequence to join to the first sequence.
     * @param outerKeySelector
     *            A function to extract the join key from each element of the
     *            first sequence.
     * @param innerKeySelector
     *            A function to extract the join key from each element of the
     *            second sequence.
     * @param resultSelector
     *            A function to create a result element from an element from the
     *            first sequence and a collection of matching elements from the
     *            second sequence.
     * @param keyType
     *            The type of the keys returned by the key selector functions.
     * @param elementType
     *            The type of the elements of the second sequence.
     * @return An IEnumerable<T> that contains elements of type TResult that are
     *         obtained by performing a grouped join on two sequences.
     */
    default <TInner, TKey, TResult> IEnumerable<TResult> groupJoin(
        Iterable<TInner> inner,
        Function<T, TKey> outerKeySelector,
        Function<TInner, TKey> innerKeySelector,
        BiFunction<T, IEnumerable<TInner>, TResult> resultSelector,
        Class<TKey> keyType,
        Class<TInner> elementType)
    {
        return Linq.groupJoin(this, inner,
            outerKeySelector, innerKeySelector, resultSelector,
            keyType, elementType);
    }

    /**
     * Correlates the elements of two sequences based on equality of keys and
     * groups the results. A specified {@link IEqualityComparer} is used to
     * compare keys.
     * 
     * @param <TInner>
     *            The type of the elements of the second sequence.
     * @param <TKey>
     *            The type of the keys returned by the key selector functions.
     * @param <TResult>
     *            The type of the result elements.
     * @param inner
     *            The sequence to join to the first sequence.
     * @param outerKeySelector
     *            A function to extract the join key from each element of the
     *            first sequence.
     * @param innerKeySelector
     *            A function to extract the join key from each element of the
     *            second sequence.
     * @param resultSelector
     *            A function to create a result element from an element from the
     *            first sequence and a collection of matching elements from the
     *            second sequence.
     * @param comparer
     *            An {@link IEqualityComparer} to hash and compare keys.
     * @param keyType
     *            The type of the keys returned by the key selector functions.
     * @param elementType
     *            The type of the elements of the second sequence.
     * @return An IEnumerable<T> that contains elements of type TResult that are
     *         obtained by performing a grouped join on two sequences.
     */
    default <TInner, TKey, TResult> IEnumerable<TResult> groupJoin(
        Iterable<TInner> inner,
        Function<T, TKey> outerKeySelector,
        Function<TInner, TKey> innerKeySelector,
        BiFunction<T, IEnumerable<TInner>, TResult> resultSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TInner> elementType)
    {
        return Linq.groupJoin(this, inner,
            outerKeySelector, innerKeySelector, resultSelector,
            comparer, keyType, elementType);
    }

    // endregion

    // region: Join

    /**
     * Correlates the elements of two sequences based on matching keys. The
     * default equality comparer is used to compare keys.
     *
     * @param <TInner>
     *            The type of the elements of the second sequence.
     * @param <TKey>
     *            The type of the keys returned by the key selector functions.
     * @param <TResult>
     *            The type of the result elements.
     * @param inner
     *            The sequence to join to the first sequence.
     * @param outerKeySelector
     *            A function to extract the join key from each element of the
     *            first sequence.
     * @param innerKeySelector
     *            A function to extract the join key from each element of the
     *            second sequence.
     * @param resultSelector
     *            A function to create a result element from two matching
     *            elements.
     * @param keyType
     *            The type of the keys returned by the key selector functions.
     * @param elementType
     *            The type of the elements of the second sequence.
     * @return An {@link IEnumerable} that has elements of type
     *         <code>TResult</code> that are obtained by performing an inner
     *         join on two sequences.
     */
    default <TInner, TKey, TResult> IEnumerable<TResult> join(
        Iterable<TInner> inner,
        Function<T, TKey> outerKeySelector,
        Function<TInner, TKey> innerKeySelector,
        BiFunction<T, TInner, TResult> resultSelector,
        Class<TKey> keyType,
        Class<TInner> elementType)
    {
        return Linq.join(this, inner, outerKeySelector, innerKeySelector, resultSelector, keyType, elementType);
    }

    /**
     * Correlates the elements of two sequences based on matching keys. The
     * default equality comparer is used to compare keys.
     *
     * @param <TInner>
     *            The type of the elements of the second sequence.
     * @param <TKey>
     *            The type of the keys returned by the key selector functions.
     * @param <TResult>
     *            The type of the result elements.
     * @param inner
     *            The sequence to join to the first sequence.
     * @param outerKeySelector
     *            A function to extract the join key from each element of the
     *            first sequence.
     * @param innerKeySelector
     *            A function to extract the join key from each element of the
     *            second sequence.
     * @param resultSelector
     *            A function to create a result element from two matching
     *            elements.
     * @param comparer
     *            An {@link IEqualityComparer} to hash and compare keys.
     * @param keyType
     *            The type of the keys returned by the key selector functions.
     * @param elementType
     *            The type of the elements of the second sequence.
     * @return An {@link IEnumerable} that has elements of type
     *         <code>TResult</code> that are obtained by performing an inner
     *         join on two sequences.
     */
    default <TInner, TKey, TResult> IEnumerable<TResult> join(
        Iterable<TInner> inner,
        Function<T, TKey> outerKeySelector,
        Function<TInner, TKey> innerKeySelector,
        BiFunction<T, TInner, TResult> resultSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TInner> elementType)
    {
        return Linq.join(this, inner, outerKeySelector, innerKeySelector, resultSelector, comparer, keyType,
            elementType);
    }

    // endregion

    // endregion

    // region: Aggregation

    // region: Aggregate

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param function
     *            An accumulator function to be invoked on each element.
     * @return The final accumulator value.
     */
    default T aggregate(BiFunction<T, T, T> function)
    {
        return Linq.aggregate(this, function);
    }

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param <TAccumulate>
     *            The type of the accumulator value.
     * @param seed
     *            The initial accumulator value.
     * @param function
     *            An accumulator function to be invoked on each element.
     * @return The final accumulator value.
     */
    default <TAccumulate> TAccumulate aggregate(TAccumulate seed,
        BiFunction<TAccumulate, T, TAccumulate> function)
    {
        return Linq.aggregate(this, seed, function);
    }

    /**
     * Applies an accumulator function over a sequence.
     *
     * @param <TAccumulate>
     *            The type of the accumulator value.
     * @param <TResult>
     *            The type of the resulting value.
     * @param seed
     *            The initial accumulator value.
     * @param function
     *            An accumulator function to be invoked on each element.
     * @param resultSelector
     *            A function to transform the final accumulator value into the
     *            result value.
     * @return The transformed final accumulator value.
     */
    default <TAccumulate, TResult> TResult aggregate(TAccumulate seed,
        BiFunction<TAccumulate, T, TAccumulate> function, Function<TAccumulate, TResult> resultSelector)
    {
        return Linq.aggregate(this, seed, function, resultSelector);
    }

    // endregion

    // region: All

    /**
     * Determines whether all elements of a sequence satisfy a condition.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>true</code> if every element of the source sequence passes
     *         the test in the specified predicate, or if the sequence is empty;
     *         otherwise, <code>false</code>.
     */
    default boolean all(Function<T, Boolean> predicate)
    {
        return Linq.all(this, predicate);
    }

    // endregion

    // region: Any

    /**
     * Determines whether a sequence contains any elements.
     *
     * @return <code>true</code> if the source sequence contains any elements;
     *         otherwise, <code>false</code>.
     */
    default boolean any()
    {
        return Linq.any(this);
    }

    /**
     * Determines whether any element of a sequence satisfies a condition.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>true</code> if any elements in the source sequence pass the
     *         test in the specified predicate; otherwise, <code>false</code>.
     */
    default boolean any(Function<T, Boolean> predicate)
    {
        return Linq.any(this, predicate);
    }

    // endregion

    // region: Contains

    /**
     * Determines whether a sequence contains a specified element by using the
     * default equality comparer.
     *
     * @param value
     *            The value to locate in the sequence.
     * @return <code>true</code> if the source sequence contains an element that
     *         has the specified value; otherwise, <code>false</code>.
     */
    default boolean contains(T value)
    {
        return Linq.contains(this, value);
    }

    // endregion

    // region: Element At

    /**
     * Returns the element at a specified index in a sequence.
     *
     * @param index
     *            The zero-based index of the element to retrieve.
     * @return The element at the specified position in the source sequence.
     */
    default T elementAt(int index)
    {
        return Linq.elementAt(this, index);
    }

    /**
     * Returns the element at a specified index in a sequence or a default value
     * if the index is out of range.
     *
     * @param index
     *            The zero-based index of the element to retrieve.
     * @return <code>null</code> if the index is outside the bounds of the
     *         source sequence; otherwise, the element at the specified position
     *         in the source sequence.
     */
    default T elementAtOrDefault(int index)
    {
        return Linq.elementAtOrDefault(this, index);
    }

    // endregion

    // region: First

    /**
     * Returns the first element of a sequence.
     *
     * @return The first element in the specified sequence.
     */
    default T first()
    {
        return Linq.first(this);
    }

    /**
     * Returns the first element of a sequence.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return The first element in the sequence that passes the test in the
     *         specified predicate function.
     */
    default T first(Function<T, Boolean> predicate)
    {
        return Linq.first(this, predicate);
    }

    /**
     * Returns the first element of a sequence, or a default value if the
     * sequence contains no elements.
     *
     * @return <code>null</code> if <code>source</code> is empty; otherwise, the
     *         first element in <code>source</code>.
     */
    default T firstOrDefault()
    {
        return Linq.firstOrDefault(this);
    }

    /**
     * Returns the first element of the sequence that satisfies a condition or a
     * default value if no such element is found.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>null</code> if <code>source</code> is empty or if no
     *         element passes the test specified by <code>predicate</code>;
     *         otherwise, the first element in <code>source</code> that passes
     *         the test specified by <code>predicate</code>.
     */
    default T firstOrDefault(Function<T, Boolean> predicate)
    {
        return Linq.firstOrDefault(this, predicate);
    }

    // endregion

    // region: Last

    /**
     * Returns the last element of a sequence.
     *
     * @param source
     *            The {@link Iterable} of which to return the last element.
     * @return The last element in the specified sequence.
     */
    default T last()
    {
        return Linq.last(this);
    }

    /**
     * Returns the last element of a sequence.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return The last element in the sequence that passes the test in the
     *         specified predicate function.
     */
    default T last(Function<T, Boolean> predicate)
    {
        return Linq.last(this, predicate);
    }

    /**
     * Returns the last element of a sequence, or a default value if the
     * sequence contains no elements.
     *
     * @return <code>null</code> if <code>source</code> is empty; otherwise, the
     *         last element in the {@link Iterable}.
     */
    default T lastOrDefault()
    {
        return Linq.lastOrDefault(this);
    }

    /**
     * Returns the last element of the sequence that satisfies a condition or a
     * default value if no such element is found.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return <code>null</code> if <code>source</code> is empty or if no
     *         element passes the test specified by <code>predicate</code>;
     *         otherwise, the last element in <code>source</code> that passes
     *         the test specified by <code>predicate</code>.
     */
    default T lastOrDefault(Function<T, Boolean> predicate)
    {
        return Linq.lastOrDefault(this, predicate);
    }

    // endregion

    // region: Sequence Equal

    /**
     * Determines whether two sequences are equal by comparing the elements by
     * using the default equality comparer for their type.
     *
     * @param second
     *            An {@link Iterable} to compare to the first sequence.
     * @return <code>true</code> if the two source sequences are of equal length
     *         and their corresponding elements are equal according to the
     *         default equality comparer for their type; otherwise,
     *         <code>false</code>.
     */
    default boolean sequenceEqual(Iterable<T> second)
    {
        return Linq.sequenceEqual(this, second);
    }

    // endregion

    // region: Single

    /**
     * Returns the only element of a sequence, and throws an exception if there
     * is not exactly one element in the sequence.
     *
     * @return The single element of the input sequence.
     */
    default T single()
    {
        return Linq.single(this);
    }

    /**
     * Returns the only element of a sequence that satisfies a specified
     * condition, and throws an exception if more than one such element exists.
     *
     * @param predicate
     *            A function to test an element for a condition.
     * @return The single element of the input sequence that satisfies a
     *         condition.
     */
    default T single(Function<T, Boolean> predicate)
    {
        return Linq.single(this, predicate);
    }

    /**
     * Returns the only element of a sequence, or a default value if the
     * sequence is empty; this method throws an exception if there is more than
     * one element in the sequence.
     *
     * @return The single element of the input sequence, or <code>null</code> if
     *         the sequence contains no elements.
     */
    default T singleOrDefault()
    {
        return Linq.singleOrDefault(this);
    }

    /**
     * Returns the only element of a sequence that satisfies a specified
     * condition or a default value if no such element exists; this method
     * throws an exception if more than one element satisfies the condition.
     *
     * @param predicate
     *            A function to test an element for a condition.
     * @return The single element of the input sequence that satisfies the
     *         condition, or <code>null</code> if no such element is found.
     */
    default T singleOrDefault(Function<T, Boolean> predicate)
    {
        return Linq.singleOrDefault(this, predicate);
    }

    // endregion

    // region: To Array

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @return An array that contains the elements from the input sequence.
     */
    default Object[] toArray()
    {
        return Linq.toArray(this);
    }

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param arrayFactory
     *            Creates an array given the number of elements.
     * @return An array that contains the elements from the input sequence.
     */
    default T[] toArray(Function<Integer, T[]> arrayFactory)
    {
        return Linq.toArray(this, arrayFactory);
    }

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @param array
     *            The array to fill.
     * @return An array that contains the elements from the input sequence.
     */
    default T[] toArray(T[] array)
    {
        return Linq.toArray(this, array);
    }

    // endregion

    // region: To Array List

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @return An array that contains the elements from the input sequence.
     */
    default ArrayList<T> toArrayList()
    {
        return Linq.toArrayList(this);
    }

    // endregion

    // region: To Hash Map

    /**
     * Creates a {@link HashMap} from an {@link Iterable} according to a
     * specified key selector function.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @return A {@link HashMap} that contains keys and values.
     */
    default <TKey> HashMap<TKey, T> toHashMap(Function<T, TKey> keySelector)
    {
        return Linq.toHashMap(this, keySelector);
    }

    /**
     * Creates a {@link HashMap} from an {@link Iterable} according to specified
     * key selector and element selector functions.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param <TElement>
     *            The type of the value returned by
     *            <code>elementSelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param elementSelector
     *            A transform function to produce a result element value from
     *            each element.
     * @return A {@link HashMap} that contains values of type
     *         <code>TElement</code> selected from the input sequence.
     */
    default <TKey, TElement> HashMap<TKey, TElement> toHashMap(Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector)
    {
        return Linq.toHashMap(this, keySelector, elementSelector);
    }

    // endregion

    // endregion

    // region: Metrics

    // region: Average

    /**
     * Computes the average of the sequence of {@link Double} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    default double averageDouble(Function<T, Double> selector)
    {
        return Linq.averageDouble(this, selector);
    }

    /**
     * Computes the average of the sequence of {@link Float} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    default float averageFloat(Function<T, Float> selector)
    {
        return Linq.averageFloat(this, selector);
    }

    /**
     * Computes the average of the sequence of {@link Integer} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    default int averageInteger(Function<T, Integer> selector)
    {
        return Linq.averageInteger(this, selector);
    }

    /**
     * Computes the average of the sequence of {@link Long} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The average of the sequence of values.
     */
    default long averageLong(Function<T, Long> selector)
    {
        return Linq.averageLong(this, selector);
    }

    // endregion

    // region: Count

    /**
     * Returns the number of elements in a sequence.
     *
     * @return The number of elements in the input sequence.
     */
    default int count()
    {
        return Linq.count(this);
    }

    /**
     * Returns a number that represents how many elements in the specified
     * sequence satisfy a condition.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return A number that represents how many elements in the sequence
     *         satisfy the condition in the predicate function.
     */
    default int count(Function<T, Boolean> predicate)
    {
        return Linq.count(this, predicate);
    }

    /**
     * Returns a {@link Long} that represents the total number of elements in a
     * sequence.
     *
     * @return The number of elements in the input sequence.
     */
    default long longCount()
    {
        return Linq.longCount(this);
    }

    /**
     * Returns a {@link Long} that represents how many elements in a sequence
     * satisfy a condition.
     *
     * @param predicate
     *            A function to test each element for a condition.
     * @return A number that represents how many elements in the sequence
     *         satisfy the condition in the predicate function.
     */
    default long longCount(Function<T, Boolean> predicate)
    {
        return Linq.longCount(this, predicate);
    }

    // endregion

    // region: Max

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Double} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    default double maxDouble(Function<T, Double> selector)
    {
        return Linq.maxDouble(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Float} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    default float maxFloat(Function<T, Float> selector)
    {
        return Linq.maxFloat(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Integer} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    default int maxInteger(Function<T, Integer> selector)
    {
        return Linq.maxInteger(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the maximum {@link Long} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The maximum value in the sequence.
     */
    default long maxLong(Function<T, Long> selector)
    {
        return Linq.maxLong(this, selector);
    }

    // endregion

    // region: Min

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Double} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    default double minDouble(Function<T, Double> selector)
    {
        return Linq.minDouble(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Float} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    default float minFloat(Function<T, Float> selector)
    {
        return Linq.minFloat(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Integer} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    default int minInteger(Function<T, Integer> selector)
    {
        return Linq.minInteger(this, selector);
    }

    /**
     * Invokes a transform function on each element of a sequence and returns
     * the minimum {@link Long} value.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The minimum value in the sequence.
     */
    default long minLong(Function<T, Long> selector)
    {
        return Linq.minLong(this, selector);
    }

    // endregion

    // region: Sum

    /**
     * Computes the sum of the sequence of {@link Double} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    default double sumDouble(Function<T, Double> selector)
    {
        return Linq.sumDouble(this, selector);
    }

    /**
     * Computes the sum of the sequence of {@link Float} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    default float sumFloat(Function<T, Float> selector)
    {
        return Linq.sumFloat(this, selector);
    }

    /**
     * Computes the sum of the sequence of {@link Integer} values that are
     * obtained by invoking a transform function on each element of the input
     * sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    default int sumInteger(Function<T, Integer> selector)
    {
        return Linq.sumInteger(this, selector);
    }

    /**
     * Computes the sum of the sequence of {@link Long} values that are obtained
     * by invoking a transform function on each element of the input sequence.
     *
     * @param selector
     *            A transform function to apply to each element.
     * @return The sum of the projected values.
     */
    default long sumLong(Function<T, Long> selector)
    {
        return Linq.sumLong(this, selector);
    }

    // endregion

    // endregion
}
