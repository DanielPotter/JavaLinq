package potter.linq;

/**
 * Defines methods to support the comparison of objects for equality.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to compare.
 */
public interface IEqualityComparer<T>
{
    /**
     * Determines whether the specified objects are equal.
     * 
     * @param x
     *            The first object of type <code>T</code> to compare.
     * @param y
     *            The second object of type <code>T</code> to compare.
     * @return <code>true</code> if the specified objects are equal; otherwise,
     *         <code>false</code>.
     */
    boolean equals(T x, T y);

    /**
     * Returns a hash code for the specified object.
     * 
     * @param obj
     *            The {@link Object} for which a hash code is to be returned.
     * @return A hash code for the specified object.
     */
    int hashCode(T obj);
}
