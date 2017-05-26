package potter.linq;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Provides default implementations of the {@link Comparator} generic interface.
 * 
 * @author Daniel Potter
 */
public class DefaultComparator implements Comparator<Object>
{
    /**
     * Represents the default comparer for objects.
     */
    public final static Comparator<Object> Default = new DefaultComparator();

    // Reference:
    // http://referencesource.microsoft.com/#mscorlib/system/collections/comparer.cs,eeb25dd2d5980323
    // (5/26/2017)

    @SuppressWarnings("unchecked")
    @Override
    public int compare(Object o1, Object o2)
    {
        if (o1 == o2)
        {
            return 0;
        }
        if (o1 == null)
        {
            return -1;
        }
        if (o2 == null)
        {
            return 1;
        }

        if (o1 instanceof Comparable<?>)
        {
            Comparable<Object> comparable = (Comparable<Object>) o1;
            return comparable.compareTo(o2);
        }

        if (o2 instanceof Comparable<?>)
        {
            Comparable<Object> comparable = (Comparable<Object>) o2;
            return -comparable.compareTo(o2);
        }

        throw new IllegalArgumentException("Cannot compare objects that do not implement Comparable");
    }

    // region: Generic Comparisons

    // Reference:
    // http://referencesource.microsoft.com/#mscorlib/system/collections/generic/comparer.cs,23592232e4d01ea4
    // (5/26/2017)

    @SuppressWarnings("rawtypes")
    private static HashMap<Class, Comparator> cachedComparators = new HashMap<Class, Comparator>();

    /**
     * Returns a default sort order comparer for the specified type.
     * 
     * @param <T>
     *            The type of objects to compare.
     * @param type
     *            The type of objects to compare.
     * @return An object that implements {@link Comparator} and serves as a sort
     *         order comparer for type <code>T</code>.
     */
    public static <T> Comparator<T> getDefault(Class<T> type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type is null.");
        }

        @SuppressWarnings("unchecked")
        Comparator<T> comparator = cachedComparators.get(type);

        if (comparator == null)
        {
            comparator = createComparer(type);
            cachedComparators.put(type, comparator);
        }

        return comparator;
    }

    @SuppressWarnings(
    {
        "unchecked", "rawtypes"
    })
    private static <T> Comparator<T> createComparer(Class<T> type)
    {
        // If T implements Comparable<T>, return a GenericComparer<T>.
        if (Comparable.class.isAssignableFrom(type))
        {
            return new GenericComparer(type);
        }

        // Otherwise, return an ObjectComparer<T>.
        return new ObjectComparer<T>(type);
    }

    private static class GenericComparer<T extends Comparable<T>> implements Comparator<T>
    {
        public GenericComparer(Class<T> type)
        {
            this.type = type;
        }

        private final Class<T> type;

        @Override
        public int compare(T x, T y)
        {
            if (x != null)
            {
                if (y != null)
                {
                    return x.compareTo(y);
                }

                return 1;
            }

            if (y != null)
            {
                return -1;
            }

            return 0;
        }

        // Equals method for the comparer itself.
        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof GenericComparer)
            {
                GenericComparer<?> comparer = (GenericComparer<?>) obj;
                return type.equals(comparer.type);
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            return type.hashCode();
        }
    }

    private static class ObjectComparer<T> implements Comparator<T>
    {
        public ObjectComparer(Class<T> type)
        {
            this.type = type;
        }

        private final Class<T> type;

        @Override
        public int compare(T o1, T o2)
        {
            return Default.compare(o1, o2);
        }

        // Equals method for the comparer itself.
        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof ObjectComparer<?>)
            {
                ObjectComparer<?> comparer = (ObjectComparer<?>) obj;
                return type.equals(comparer.type);
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            return type.hashCode();
        }
    }

    // endregion
}
