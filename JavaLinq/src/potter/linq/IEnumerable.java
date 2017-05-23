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
