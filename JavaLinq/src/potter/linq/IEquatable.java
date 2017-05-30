package potter.linq;

/**
 * Defines a generalized method that a value type or class implements to create
 * a type-specific method for determining equality of instances.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to compare.
 */
public interface IEquatable<T>
{
    /**
     * Indicates whether the current object is equal to another object of the
     * same type.
     * 
     * @param other
     *            An object to compare with this object.
     * @return <code>true</code> if the current object is equal to the
     *         <code>other</code> parameter; otherwise, <code>false</code>.
     */
    boolean equalsOther(T other);
}
