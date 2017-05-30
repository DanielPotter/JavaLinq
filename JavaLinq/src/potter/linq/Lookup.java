package potter.linq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a collection of keys each mapped to one or more values.
 * 
 * @author Daniel Potter
 *
 * @param <TKey>
 *            The type of the keys in the {@link Lookup}.
 * @param <TElement>
 *            The type of the elements of each {@link IEnumerable} value in the
 *            {@link Lookup}.
 */
public class Lookup<TKey, TElement> implements ILookup<TKey, TElement>
{
    static <TSource, TKey, TElement> Lookup<TKey, TElement> create(Iterable<TSource> source,
        Function<TSource, TKey> keySelector,
        Function<TSource, TElement> elementSelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        Lookup<TKey, TElement> lookup = new Lookup<>(comparer, keyType, elementType);
        for (TSource item : source)
        {
            lookup.getGrouping(keySelector.apply(item), true).addItem(elementSelector.apply(item));
        }

        return lookup;
    }

    static <TKey, TElement> Lookup<TKey, TElement> createForJoin(IEnumerable<TElement> source,
        Function<TElement, TKey> keySelector,
        IEqualityComparer<TKey> comparer,
        Class<TKey> keyType,
        Class<TElement> elementType)
    {
        Lookup<TKey, TElement> lookup = new Lookup<>(comparer, keyType, elementType);
        for (TElement item : source)
        {
            TKey key = keySelector.apply(item);
            if (key != null)
            {
                lookup.getGrouping(key, true).addItem(item);
            }
        }

        return lookup;
    }

    private Lookup(IEqualityComparer<TKey> comparer, Class<TKey> keyType, Class<TElement> elementType)
    {
        if (comparer == null)
        {
            comparer = EqualityComparer.getDefault(keyType);
        }

        this.comparer = comparer;
        groupings = new Object[7];

        this.elementType = elementType;
    }

    private final IEqualityComparer<TKey> comparer;
    private final Class<TElement> elementType;

    private Object[] groupings;
    private Grouping lastGrouping;
    private int count;

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public IEnumerable<TElement> get(TKey key)
    {
        Grouping grouping = getGrouping(key, false);
        if (grouping != null)
        {
            return grouping;
        }

        return Linq.EmptyEnumerable.getInstance(elementType);
    }

    @Override
    public boolean containsKey(TKey key)
    {
        return getGrouping(key, false) != null;
    }

    @Override
    public Iterator<IGrouping<TKey, TElement>> iterator()
    {
        return getEnumerator();
    }

    @Override
    public IEnumerator<IGrouping<TKey, TElement>> getEnumerator()
    {
        return new SimpleIterator<IGrouping<TKey, TElement>>()
        {
            private Grouping group = lastGrouping;
            private boolean hasFoundLast;

            @Override
            public boolean moveNext()
            {
                if (group != null && hasFoundLast == false)
                {
                    group = group.next;
                    hasFoundLast = group == lastGrouping;

                    setCurrent(group);
                    return true;
                }

                return false;
            }
        };
    }

    /**
     * Applies a transform function to each key and its associated values and
     * returns the results.
     * 
     * @param <TResult>
     *            The type of the result values produced by
     *            <code>resultSelector</code>.
     * @param resultSelector
     *            A function to project a result value from each key and its
     *            associated values.
     * @return A collection that contains one value for each key/value
     *         collection pair in the {@link Lookup}.
     */
    public <TResult> IEnumerable<TResult> applyResultSelector(
        BiFunction<TKey, IEnumerable<TElement>, TResult> resultSelector)
    {
        return new EnumerableAdapter<>(() -> new SimpleIterator<TResult>()
        {
            private Grouping group = lastGrouping;
            private boolean hasFoundLast;

            @Override
            public boolean moveNext()
            {
                if (group != null && hasFoundLast == false)
                {
                    group = group.next;
                    hasFoundLast = group == lastGrouping;

                    setCurrent(resultSelector.apply(group.key, group));
                    return true;
                }

                return false;
            }
        });
    }

    private int internalHashCode(TKey key)
    {
        return (key == null) ? 0 : comparer.hashCode(key) & 0x7FFFFFFF;
    }

    @SuppressWarnings("unchecked")
    private Grouping getGrouping(TKey key, boolean create)
    {
        int hashCode = internalHashCode(key);
        for (Grouping group = (Grouping) groupings[hashCode % groupings.length];
            group != null; group = group.hashNext)
        {
            if (group.hashCode == hashCode && comparer.equals(group.key, key))
            {
                return group;
            }
        }

        if (create)
        {
            if (count == groupings.length)
            {
                resize();
            }

            int index = hashCode % groupings.length;
            Grouping group = new Grouping(key, hashCode, new ArrayList<TElement>(1));
            group.hashNext = (Grouping) groupings[index];
            groupings[index] = group;

            if (lastGrouping == null)
            {
                group.next = group;
            }
            else
            {
                group.next = lastGrouping.next;
                lastGrouping.next = group;
            }

            lastGrouping = group;
            count++;
            return group;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize()
    {
        int newSize = count * 2 + 1;
        Grouping[] newGroupings = (Grouping[]) new Object[newSize];
        Grouping group = lastGrouping;
        do
        {
            group = group.next;
            int index = group.hashCode % newSize;
            group.hashNext = newGroupings[index];
            newGroupings[index] = group;
        }
        while (group != lastGrouping);
        groupings = newGroupings;
    }

    private class Grouping implements IGrouping<TKey, TElement>, List<TElement>
    {
        public Grouping(TKey key, int hashCode, ArrayList<TElement> elements)
        {
            this.key = key;
            this.hashCode = hashCode;
            this.elements = elements;

            elementEnumerable = new EnumerableAdapter<>(elements);
        }

        private final TKey key;
        private final int hashCode;
        private final ArrayList<TElement> elements;
        private final EnumerableAdapter<TElement> elementEnumerable;

        private Grouping hashNext;
        private Grouping next;

        private void addItem(TElement element)
        {
            elements.add(element);
        }

        @Override
        public IEnumerator<TElement> getEnumerator()
        {
            return elementEnumerable.getEnumerator();
        }

        @Override
        public Iterator<TElement> iterator()
        {
            return elementEnumerable.getEnumerator();
        }

        @Override
        public TKey getKey()
        {
            return key;
        }

        // region: List Implementation

        @Override
        public boolean add(TElement element)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, TElement element)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends TElement> collection)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int index, Collection<? extends TElement> collection)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(Object item)
        {
            return elements.contains(item);
        }

        @Override
        public boolean containsAll(Collection<?> collection)
        {
            return elements.containsAll(collection);
        }

        @Override
        public TElement get(int index)
        {
            return elements.get(index);
        }

        @Override
        public int indexOf(Object item)
        {
            return elements.indexOf(item);
        }

        @Override
        public boolean isEmpty()
        {
            return elements.isEmpty();
        }

        @Override
        public int lastIndexOf(Object item)
        {
            return elements.lastIndexOf(item);
        }

        @Override
        public ListIterator<TElement> listIterator()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public ListIterator<TElement> listIterator(int index)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object item)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public TElement remove(int index)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public TElement set(int index, TElement element)
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size()
        {
            return elements.size();
        }

        @Override
        public List<TElement> subList(int fromIndex, int toIndex)
        {
            return elements.subList(fromIndex, toIndex);
        }

        @Override
        public Object[] toArray()
        {
            return elements.toArray();
        }

        @SuppressWarnings("unchecked")
        @Override
        public TElement[] toArray(Object[] array)
        {
            return (TElement[]) elements.toArray(array);
        }

        // endregion
    }
}
