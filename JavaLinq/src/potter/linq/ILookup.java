package potter.linq;

/**
 * Defines an indexer, size property, and Boolean search method for data
 * structures that map keys to IEnumerable<T> sequences of values.
 * 
 * @author Daniel Potter
 *
 * @param <TKey>
 *            The type of the keys in the {@link ILookup}.
 * @param <TElement>
 *            The type of the elements in the {@link IEnumerable} sequences that
 *            make up the values in the {@link ILookup}.
 */
public interface ILookup<TKey, TElement> extends IEnumerable<IGrouping<TKey, TElement>>
{
    /**
     * Gets the number of key/value collection pairs in the {@link ILookup}.
     * 
     * @return The number of key/value collection pairs in the {@link ILookup}.
     */
    int getCount();

    /**
     * Gets the {@link IEnumerable} sequence of values indexed by a specified
     * key.
     * 
     * @param key
     *            The key of the desired sequence of values.
     * @return The {@link IEnumerable} sequence of values indexed by the
     *         specified key.
     */
    IEnumerable<TElement> get(TKey key);

    /**
     * Determines whether a specified key exists in the {@link ILookup}.
     * 
     * @param key
     *            The key to search for in the {@link ILookup}.
     * @return <code>true</code> if key is in the {@link ILookup}; otherwise,
     *         <code>false</code>.
     */
    boolean containsKey(TKey key);
}
