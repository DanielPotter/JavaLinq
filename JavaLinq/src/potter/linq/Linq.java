package potter.linq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class Linq
{
    public static <TSource> int count(Iterable<TSource> source)
    {
        int count = 0;
        Iterator<TSource> sequenceIterator = source.iterator();
        while (sequenceIterator.hasNext())
        {
            sequenceIterator.next();
            count++;
        }

        return count;
    }

    public static <TSource> ArrayList<TSource> toArrayList(Iterable<TSource> source)
    {
        ArrayList<TSource> list = new ArrayList<>();
        for (TSource element : source)
        {
            list.add(element);
        }

        return list;
    }

    public static <TSource, TKey> HashMap<TKey, TSource> toDictionary(Iterable<TSource> source, Function<TSource, TKey> keySelector)
    {
        HashMap<TKey, TSource> dictionary = new HashMap<TKey, TSource>();
        for (TSource value : source)
        {
            TKey key = keySelector.apply(value);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    public static <TSource, TKey, TElement> HashMap<TKey, TElement> toDictionary(Iterable<TSource> source, Function<TSource, TKey> keySelector, Function<TSource, TElement> elementSelector)
    {
        HashMap<TKey, TElement> dictionary = new HashMap<TKey, TElement>();
        for (TSource item : source)
        {
            TKey key = keySelector.apply(item);
            TElement value = elementSelector.apply(item);
            dictionary.put(key, value);
        }

        return dictionary;
    }

    public static <TSource> Iterable<TSource> where(Iterable<TSource> source, Function<TSource, Boolean> predicate)
    {
        Iterator<TSource> sourceIterator = source.iterator();
        return new Iterable<TSource>()
        {
            @Override
            public Iterator<TSource> iterator()
            {
                return new SimpleIterator<TSource>()
                {
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
            if (hasBeenRead == false)
            {
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

            return getCurrent();
        }
    }
}
