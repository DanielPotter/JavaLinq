package potter.linq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public interface IEnumerable<T> extends Iterable<T>
{
    default IEnumerator<T> getEnumerator()
    {
        return IEnumerator.wrap(iterator());
    }

    // region: Aggregation

    // region: Count

    default int count()
    {
        return Linq.count(this);
    }

    // endregion

    // region: To Collection

    default ArrayList<T> toArrayList()
    {
        return Linq.toArrayList(this);
    }

    default <TKey> HashMap<TKey, T> toDictionary(Function<T, TKey> keySelector)
    {
        return Linq.toDictionary(this, keySelector);
    }

    default <TKey, TElement> HashMap<TKey, TElement> toDictionary(Function<T, TKey> keySelector,
        Function<T, TElement> elementSelector)
    {
        return Linq.toDictionary(this, keySelector, elementSelector);
    }

    // endregion

    // endregion

    // region: Mutating

    // region: Select

    default <TResult> IEnumerable<TResult> select(Function<T, TResult> selector)
    {
        return Linq.select(this, selector);
    }

    // endregion

    // region: Where

    default IEnumerable<T> where(Function<T, Boolean> selector)
    {
        return Linq.where(this, selector);
    }

    // endregion

    // endregion
}
