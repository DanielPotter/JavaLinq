package potter.linq;

import java.util.Iterator;

public interface IEnumerator<T> extends Iterator<T>
{
    T getCurrent();

    boolean moveNext();

    // region: Wrap Iterator

    static <T> IEnumerator<T> wrap(Iterator<T> source)
    {
        return new EnumeratorWrapper<>(source);
    }

    static class EnumeratorWrapper<T> extends SimpleIterator<T>
    {
        private Iterator<T> source;

        public EnumeratorWrapper(Iterator<T> source)
        {
            this.source = source;
        }

        @Override
        public boolean moveNext()
        {
            if (source.hasNext())
            {
                setCurrent(source.next());
            }

            return false;
        }
    }

    // endregion
}
