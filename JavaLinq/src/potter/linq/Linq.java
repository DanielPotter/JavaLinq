package potter.linq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Linq
{
    // region: Aggregation

    // region: Count

    public static <TSource> int count(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        int count = 0;
        Iterator<TSource> sequenceIterator = source.iterator();
        while (sequenceIterator.hasNext())
        {
            sequenceIterator.next();
            count++;
        }

        return count;
    }

    // endregion

    // region: To Collection

    public static <TSource> ArrayList<TSource> toArrayList(Iterable<TSource> source)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }

        ArrayList<TSource> list = new ArrayList<>();
        for (TSource element : source)
        {
            list.add(element);
        }

        return list;
    }

    public static <TSource, TKey> HashMap<TKey, TSource> toDictionary(Iterable<TSource> source,
        Function<TSource, TKey> keySelector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (keySelector == null)
        {
            throw new IllegalArgumentException("keySelector is null.");
        }

        HashMap<TKey, TSource> dictionary = new HashMap<TKey, TSource>();
        for (TSource value : source)
        {
            TKey key = keySelector.apply(value);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    public static <TSource, TKey, TElement> HashMap<TKey, TElement> toDictionary(Iterable<TSource> source,
        Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (keySelector == null)
        {
            throw new IllegalArgumentException("keySelector is null.");
        }
        if (elementSelector == null)
        {
            throw new IllegalArgumentException("elementSelector is null.");
        }

        HashMap<TKey, TElement> dictionary = new HashMap<TKey, TElement>();
        for (TSource item : source)
        {
            TKey key = keySelector.apply(item);
            TElement value = elementSelector.apply(item);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    // endregion

    // endregion

    // region: Mutating

    // region: Select

    public static <TSource, TResult> Iterable<TResult> select(Iterable<TSource> source,
        Function<TSource, TResult> selector)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (selector == null)
        {
            throw new IllegalArgumentException("selector is null.");
        }

        return new Iterable<TResult>()
        {
            @Override
            public Iterator<TResult> iterator()
            {
                return new SimpleIterator<TResult>()
                {
                    private Iterator<TSource> sourceIterator = source.iterator();

                    @Override
                    public boolean moveNext()
                    {
                        if (sourceIterator.hasNext())
                        {
                            TSource input = sourceIterator.next();
                            TResult currentValue = selector.apply(input);
                            setCurrent(currentValue);
                            return true;
                        }

                        return false;
                    }
                };
            }
        };
    }

    // endregion

    // region: Where

    public static <TSource> Iterable<TSource> where(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        if (source == null)
        {
            throw new IllegalArgumentException("source is null.");
        }
        if (predicate == null)
        {
            throw new IllegalArgumentException("predicate is null.");
        }

        return new Iterable<TSource>()
        {
            @Override
            public Iterator<TSource> iterator()
            {
                return new SimpleIterator<TSource>()
                {
                    private Iterator<TSource> sourceIterator = source.iterator();

                    @Override
                    public boolean moveNext()
                    {
                        while (sourceIterator.hasNext())
                        {
                            setCurrent(sourceIterator.next());
                            if (predicate.apply(getCurrent()))
                            {
                                return true;
                            }
                        }

                        return false;
                    }
                };
            }
        };
    }

    // endregion

    // endregion

    private static abstract class SimpleIterator<T> extends DynamicIterator<T>
    {
        private T current;

        @Override
        public T getCurrent()
        {
            return current;
        }

        protected void setCurrent(T value)
        {
            current = value;
        }
    }

    private static abstract class DynamicIterator<T> implements IEnumerator<T>
    {
        private boolean hasBeenRead = true;

        @Override
        public boolean hasNext()
        {
            if (hasBeenRead)
            {
                hasBeenRead = false;
                return moveNext();
            }

            return true;
        }

        @Override
        public T next()
        {
            if (hasBeenRead)
            {
                if (moveNext() == false)
                {
                    throw new NoSuchElementException("Cannot iterate past the end of the collection.");
                }
            }

            hasBeenRead = true;
            return getCurrent();
        }
    }
}
