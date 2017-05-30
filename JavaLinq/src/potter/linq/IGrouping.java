package potter.linq;

/**
 * Represents a collection of objects that have a common key.
 * 
 * @author Daniel Potter
 *
 * @param <TKey>
 *            The type of the key of the {@link IGrouping}.
 * @param <TElement>
 *            The type of the values in the {@link IGrouping}.
 */
public interface IGrouping<TKey, TElement> extends IEnumerable<TElement>
{
    /**
     * Gets the key of the {@link IGrouping}.
     * 
     * @return The key of an {@link IGrouping} represents the attribute that is
     *         common to each value in the {@link IGrouping}.
     */
    TKey getKey();
}
