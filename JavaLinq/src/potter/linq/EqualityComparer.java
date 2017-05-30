package potter.linq;

import java.util.HashMap;

/**
 * Provides a base class for implementations of the {@link IEqualityComparer}
 * generic interface.
 * 
 * @author Daniel Potter
 *
 * @param <T>
 *            The type of objects to compare.
 */
public abstract class EqualityComparer<T> implements IEqualityComparer<T>
{
    // Reference:
    // http://referencesource.microsoft.com/#mscorlib/system/collections/generic/equalitycomparer.cs,d8e28972e89a3e86
    // (5/30/2017)

    // region: Default Comparer

    @SuppressWarnings("rawtypes")
    private static HashMap<Class, EqualityComparer> cachedEqualityComparers = new HashMap<Class, EqualityComparer>();

    /**
     * Returns a default equality comparer for the type specified by the generic
     * argument.
     * 
     * @param <T>
     *            The type of objects to compare.
     * @param type
     *            The type of objects to compare.
     * @return The default instance of the {@link EqualityComparer} class for
     *         type <code>T</code>.
     */
    public static <T> EqualityComparer<T> getDefault(Class<T> type)
    {
        if (type == null)
        {
            throw new IllegalArgumentException("type is null.");
        }

        @SuppressWarnings("unchecked")
        EqualityComparer<T> equalityComparer = cachedEqualityComparers.get(type);

        if (equalityComparer == null)
        {
            equalityComparer = createComparer(type);
            cachedEqualityComparers.put(type, equalityComparer);
        }

        return equalityComparer;
    }

    @SuppressWarnings(
    {
        "rawtypes", "unchecked"
    })
    private static <T> EqualityComparer<T> createComparer(Class<T> type)
    {
        // If T implements IEquatable<T>, return a GenericEqualityComparer<T>.
        if (IEquatable.class.isAssignableFrom(type))
        {
            return new GenericEqualityComparer(type);
        }

        // Otherwise, return an ObjectEqualityComparer<T>.
        return new ObjectEqualityComparer<T>(type);
    }

    // endregion

    public EqualityComparer(Class<T> type)
    {
        this.type = type;
    }

    protected Class<T> getType()
    {
        return type;
    }

    private Class<T> type;

    public abstract boolean typedEquals(T x, T y);

    public abstract int typedHashCode(T obj);

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object x, Object y)
    {
        if (x == y)
        {
            return true;
        }
        if (x == null || y == null)
        {
            return false;
        }

        if (type.isAssignableFrom(x.getClass()) && type.isAssignableFrom(y.getClass()))
        {
            return typedEquals((T) x, (T) y);
        }

        throw new IllegalArgumentException("The provided objects are not instances of " + type);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int hashCode(Object obj)
    {
        if (obj == null)
        {
            return 0;
        }

        if (type.isAssignableFrom(obj.getClass()))
        {
            return typedHashCode((T) obj);
        }

        throw new IllegalArgumentException("The provided object is not an instance of " + type);
    }

    protected int indexOf(T[] array, T value, int startIndex, int count)
    {
        int endIndex = startIndex + count;
        for (int index = startIndex; index < endIndex; index++)
        {
            if (equals(array[index], value))
            {
                return index;
            }
        }

        return -1;
    }

    protected int lastIndexOf(T[] array, T value, int startIndex, int count)
    {
        int endIndex = startIndex - count + 1;
        for (int index = startIndex; index >= endIndex; index--)
        {
            if (equals(array[index], value))
            {
                return index;
            }
        }

        return -1;
    }

    static class GenericEqualityComparer<T extends IEquatable<T>> extends EqualityComparer<T>
    {
        public GenericEqualityComparer(Class<T> type)
        {
            super(type);
        }

        @Override
        public boolean typedEquals(T x, T y)
        {
            if (x != null)
            {
                if (y != null)
                {
                    return x.equalsOther(y);
                }

                return false;
            }

            if (y != null)
            {
                return false;
            }

            return true;
        }

        @Override
        public int typedHashCode(T obj)
        {
            if (obj == null)
            {
                return 0;
            }

            return obj.hashCode();
        }

        @Override
        protected int indexOf(T[] array, T value, int startIndex, int count)
        {
            int endIndex = startIndex + count;
            if (value == null)
            {
                for (int index = startIndex; index < endIndex; index++)
                {
                    if (array[index] == null)
                    {
                        return index;
                    }
                }
            }
            else
            {
                for (int index = startIndex; index < endIndex; index++)
                {
                    if (array[index] != null && array[index].equalsOther(value))
                    {
                        return index;
                    }
                }
            }

            return -1;
        }

        @Override
        protected int lastIndexOf(T[] array, T value, int startIndex, int count)
        {
            int endIndex = startIndex - count + 1;
            if (value == null)
            {
                for (int index = startIndex; index >= endIndex; index--)
                {
                    if (array[index] == null)
                    {
                        return index;
                    }
                }
            }
            else
            {
                for (int index = startIndex; index >= endIndex; index--)
                {
                    if (array[index] != null && array[index].equalsOther(value))
                    {
                        return index;
                    }
                }
            }

            return -1;
        }

        // Equals method for the comparer itself.
        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object obj)
        {
            if (GenericEqualityComparer.class.isInstance(obj))
            {
                return getType().equals(((GenericEqualityComparer) obj).getType());
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            return getType().getName().hashCode();
        }
    }

    static class ObjectEqualityComparer<T> extends EqualityComparer<T>
    {
        public ObjectEqualityComparer(Class<T> type)
        {
            super(type);
        }

        @Override
        public boolean typedEquals(T x, T y)
        {
            if (x != null)
            {
                if (y != null)
                {
                    return x.equals(y);
                }

                return false;
            }

            if (y != null)
            {
                return false;
            }

            return true;
        }

        @Override
        public int typedHashCode(T obj)
        {
            if (obj == null)
            {
                return 0;
            }

            return obj.hashCode();
        }

        @Override
        protected int indexOf(T[] array, T value, int startIndex, int count)
        {
            int endIndex = startIndex + count;
            if (value == null)
            {
                for (int index = startIndex; index < endIndex; index++)
                {
                    if (array[index] == null)
                    {
                        return index;
                    }
                }
            }
            else
            {
                for (int index = startIndex; index < endIndex; index++)
                {
                    if (array[index] != null && array[index].equals(value))
                    {
                        return index;
                    }
                }
            }

            return -1;
        }

        @Override
        protected int lastIndexOf(T[] array, T value, int startIndex, int count)
        {
            int endIndex = startIndex - count + 1;
            if (value == null)
            {
                for (int index = startIndex; index >= endIndex; index--)
                {
                    if (array[index] == null)
                    {
                        return index;
                    }
                }
            }
            else
            {
                for (int index = startIndex; index >= endIndex; index--)
                {
                    if (array[index] != null && array[index].equals(value))
                    {
                        return index;
                    }
                }
            }

            return -1;
        }

        // Equals method for the comparer itself.
        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object obj)
        {
            if (ObjectEqualityComparer.class.isInstance(obj))
            {
                return getType().equals(((ObjectEqualityComparer) obj).getType());
            }

            return false;
        }

        @Override
        public int hashCode()
        {
            return getType().getName().hashCode();
        }
    }
}
