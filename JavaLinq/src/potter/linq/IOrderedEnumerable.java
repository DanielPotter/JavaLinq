package potter.linq;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Represents a sorted sequence.
 *
 * @author Daniel Potter
 *
 * @param <TElement>
 *            The type of the elements of the sequence.
 */
public interface IOrderedEnumerable<TElement> extends IEnumerable<TElement>
{
    /**
     * Performs a subsequent ordering on the elements of an
     * {@link IOrderedEnumerable} according to a key.
     *
     * @param <TKey>
     *            The type of the key produced by <code>keySelector</code>.
     * @param keySelector
     *            The {@link Function} used to extract the key for each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            The {@link Comparator} used to compare keys for placement in
     *            the returned sequence.
     * @param descending
     *            <code>true</code> to sort the elements in descending order;
     *            <code>false</code> to sort the elements in ascending order.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    <TKey> IOrderedEnumerable<TElement> createOrderedEnumerable(Function<TElement, TKey> keySelector,
        Class<TKey> keyType, Comparator<TKey> comparer, boolean descending);

    // region: Then By

    /**
     * Performs a subsequent ordering of the elements in a sequence in ascending
     * order according to a key.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    default <TKey> IOrderedEnumerable<TElement> thenBy(Function<TElement, TKey> keySelector, Class<TKey> keyType)
    {
        return Linq.thenBy(this, keySelector, keyType);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in ascending
     * order by using a specified comparer.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted according
     *         to a key.
     */
    default <TKey> IOrderedEnumerable<TElement> thenBy(Function<TElement, TKey> keySelector, Class<TKey> keyType,
        Comparator<TKey> comparer)
    {
        return Linq.thenBy(this, keySelector, keyType, comparer);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in
     * descending order, according to a key.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    default <TKey> IOrderedEnumerable<TElement> thenByDescending(Function<TElement, TKey> keySelector,
        Class<TKey> keyType)
    {
        return Linq.thenByDescending(this, keySelector, keyType);
    }

    /**
     * Performs a subsequent ordering of the elements in a sequence in
     * descending order by using a specified comparer.
     *
     * @param <TKey>
     *            The type of the key returned by <code>keySelector</code>.
     * @param keySelector
     *            A function to extract a key from each element.
     * @param keyType
     *            The type of the key that will be compared while ordering.
     * @param comparer
     *            A {@link Comparator} to compare keys.
     * @return An {@link IOrderedEnumerable} whose elements are sorted in
     *         descending order according to a key.
     */
    default <TKey> IOrderedEnumerable<TElement> thenByDescending(Function<TElement, TKey> keySelector,
        Class<TKey> keyType, Comparator<TKey> comparer)
    {
        return Linq.thenByDescending(this, keySelector, keyType, comparer);
    }

    // endregion
}
