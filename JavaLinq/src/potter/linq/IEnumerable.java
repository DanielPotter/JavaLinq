package potter.linq;

import java.util.ArrayList;
import java.util.HashMap;
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

    // region: Aggregation

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

    // region: To Collection

    /**
     * Creates an array from an {@link Iterable}.
     *
     * @return An array that contains the elements from the input sequence.
     */
    default ArrayList<T> toArrayList()
    {
        return Linq.toArrayList(this);
    }

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
    default <TKey> HashMap<TKey, T> toDictionary(Function<T, TKey> keySelector)
    {
        return Linq.toDictionary(this, keySelector);
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
    default <TKey, TElement> HashMap<TKey, TElement> toDictionary(Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector)
    {
        return Linq.toDictionary(this, keySelector, elementSelector);
    }

    // endregion

    // endregion

    // region: Mutating

    // region: Select

    /**
     * Projects each element of the sequence into a new form.
     *
     * @param <TResult>
     *            The type of the value returned by <code>selector</code>.
     * @param selector
     *            A transform function to apply to each element.
     * @return An {@link Iterable} whose elements are the result of invoking the
     *         transform function on each element of the source.
     */
    default <TResult> IEnumerable<TResult> select(Function<T, TResult> selector)
    {
        return Linq.select(this, selector);
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

    // endregion

    // endregion
}
