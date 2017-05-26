package potter.linq.tests;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Test;

import potter.linq.IOrderedEnumerable;
import potter.linq.Linq;

public class OrderByAndThenByTests
{
    // region: orderBy(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>)

    @Test(expected = IllegalArgumentException.class)
    public void orderBy1_nullSource_throwsException()
    {
        Linq.orderBy(null, item -> null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderBy1_nullKeySelector_throwsException()
    {
        Linq.orderBy(new ArrayList<Object>(), null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderBy1_nullKeyType_throwsException()
    {
        Linq.orderBy(new ArrayList<Object>(), item -> null, null);
    }

    @Test
    public void orderBy1_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderBy(source, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void orderBy1_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("ba");
        source.add("bb");
        source.add("aaa");
        source.add("ab");
        source.add("bbb");
        source.add("aa");
        source.add("b");
        source.add("a");

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("a");
        expectedElements.add("aa");
        expectedElements.add("aaa");
        expectedElements.add("ab");
        expectedElements.add("b");
        expectedElements.add("ba");
        expectedElements.add("bb");
        expectedElements.add("bbb");

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderBy(source, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: orderBy(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>, Comparator<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void orderBy2_nullSource_throwsException()
    {
        Linq.orderBy(null, item -> null, Object.class, new DummyComparer<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderBy2_nullKeySelector_throwsException()
    {
        Linq.orderBy(new ArrayList<Object>(), null, Object.class, new DummyComparer<Object>());
    }

    @Test
    public void orderBy2_nullKeyType_noException()
    {
        Linq.orderBy(new ArrayList<Object>(), item -> null, null, new DummyComparer<Object>());
    }

    @Test
    public void orderBy2_nullComparer_noException()
    {
        Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderBy2_nullComparerAndKeyType_throwsException()
    {
        Linq.orderBy(new ArrayList<Object>(), item -> null, null, null);
    }

    @Test
    public void orderBy2_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderBy(source, item -> item, String.class, null);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void orderBy2_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("ba");
        source.add("bb");
        source.add("aaa");
        source.add("ab");
        source.add("bbb");
        source.add("aa");
        source.add("b");
        source.add("a");

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("a");
        expectedElements.add("b");
        expectedElements.add("aa");
        expectedElements.add("ab");
        expectedElements.add("ba");
        expectedElements.add("bb");
        expectedElements.add("aaa");
        expectedElements.add("bbb");

        Comparator<String> comparer = new StringLengthAndValueComparer();

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderBy(source, item -> item, String.class, comparer);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: orderByDescending(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending1_nullSource_throwsException()
    {
        Linq.orderByDescending(null, item -> null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending1_nullKeySelector_throwsException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending1_nullKeyType_throwsException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), item -> null, null);
    }

    @Test
    public void orderByDescending1_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderByDescending(source, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void orderByDescending1_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("ba");
        source.add("bb");
        source.add("aaa");
        source.add("ab");
        source.add("bbb");
        source.add("aa");
        source.add("b");
        source.add("a");

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("bbb");
        expectedElements.add("bb");
        expectedElements.add("ba");
        expectedElements.add("b");
        expectedElements.add("ab");
        expectedElements.add("aaa");
        expectedElements.add("aa");
        expectedElements.add("a");

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderByDescending(source, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: orderByDescending(Iterable<TSource>, Function<TSource, TKey>, Comparator<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending2_nullSource_throwsException()
    {
        Linq.orderByDescending(null, item -> null, Object.class, new DummyComparer<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending2_nullKeySelector_throwsException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), null, Object.class, new DummyComparer<Object>());
    }

    @Test
    public void orderByDescending2_nullKeyType_noException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), item -> null, null, new DummyComparer<Object>());
    }

    @Test
    public void orderByDescending2_nullComparer_noException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void orderByDescending2_nullComparerAndKeyType_throwsException()
    {
        Linq.orderByDescending(new ArrayList<Object>(), item -> null, null, null);
    }

    @Test
    public void orderByDescending2_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        // Act
        IOrderedEnumerable<String> actualElements = Linq.orderByDescending(source, item -> item, String.class, null);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void orderByDescending2_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("ba");
        source.add("bb");
        source.add("aaa");
        source.add("ab");
        source.add("bbb");
        source.add("aa");
        source.add("b");
        source.add("a");

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("bbb");
        expectedElements.add("aaa");
        expectedElements.add("bb");
        expectedElements.add("ba");
        expectedElements.add("ab");
        expectedElements.add("aa");
        expectedElements.add("b");
        expectedElements.add("a");

        Comparator<String> comparer = new StringLengthAndValueComparer();

        // Act
        IOrderedEnumerable<String> actualElements
            = Linq.orderByDescending(source, item -> item, String.class, comparer);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: thenBy(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>)

    @Test(expected = IllegalArgumentException.class)
    public void thenBy1_nullSource_throwsException()
    {
        Linq.thenBy(null, item -> null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenBy1_nullKeySelector_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenBy1_nullKeyType_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, item -> null, null);
    }

    @Test
    public void thenBy1_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        IOrderedEnumerable<String> orderedEnumerable = Linq.orderBy(source, item -> null, String.class);

        // Act
        IOrderedEnumerable<String> actualElements = Linq.thenBy(orderedEnumerable, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void thenBy1_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple(2, "c"));
        source.add(new Tuple(2, "a"));
        source.add(new Tuple(1, "c"));
        source.add(new Tuple(2, "b"));
        source.add(new Tuple(1, "a"));
        source.add(new Tuple(3, "a"));
        source.add(new Tuple(3, "c"));
        source.add(new Tuple(3, "b"));
        source.add(new Tuple(1, "b"));

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple(1, "a"));
        expectedElements.add(new Tuple(1, "b"));
        expectedElements.add(new Tuple(1, "c"));
        expectedElements.add(new Tuple(2, "a"));
        expectedElements.add(new Tuple(2, "b"));
        expectedElements.add(new Tuple(2, "c"));
        expectedElements.add(new Tuple(3, "a"));
        expectedElements.add(new Tuple(3, "b"));
        expectedElements.add(new Tuple(3, "c"));

        IOrderedEnumerable<Tuple> orderedEnumerable = Linq.orderBy(source, item -> item.value, Integer.class);

        // Act
        IOrderedEnumerable<Tuple> actualElements = Linq.thenBy(orderedEnumerable, item -> item.name, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: thenBy(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>, Comparator<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void thenBy2_nullSource_throwsException()
    {
        Linq.thenBy(null, item -> null, Object.class, new DummyComparer<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenBy2_nullKeySelector_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, null, Object.class, new DummyComparer<Object>());
    }

    @Test
    public void thenBy2_nullKeyType_noException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, item -> null, null, new DummyComparer<Object>());
    }

    @Test
    public void thenBy2_nullComparer_noException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, item -> null, Object.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenBy2_nullComparerAndKeyType_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderBy(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenBy(orderedEnumerable, item -> null, null, null);
    }

    @Test
    public void thenBy2_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        IOrderedEnumerable<String> orderedEnumerable = Linq.orderBy(source, item -> item, String.class);

        // Act
        IOrderedEnumerable<String> actualElements = Linq.thenBy(orderedEnumerable, item -> item, String.class, null);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void thenBy2_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple(1, "aa"));
        source.add(new Tuple(3, "a"));
        source.add(new Tuple(3, "bb"));
        source.add(new Tuple(2, "b"));
        source.add(new Tuple(1, "a"));
        source.add(new Tuple(1, "b"));
        source.add(new Tuple(3, "b"));
        source.add(new Tuple(1, "bb"));
        source.add(new Tuple(2, "bb"));
        source.add(new Tuple(3, "aa"));
        source.add(new Tuple(2, "a"));
        source.add(new Tuple(2, "aa"));

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple(1, "a"));
        expectedElements.add(new Tuple(1, "b"));
        expectedElements.add(new Tuple(1, "aa"));
        expectedElements.add(new Tuple(1, "bb"));
        expectedElements.add(new Tuple(2, "a"));
        expectedElements.add(new Tuple(2, "b"));
        expectedElements.add(new Tuple(2, "aa"));
        expectedElements.add(new Tuple(2, "bb"));
        expectedElements.add(new Tuple(3, "a"));
        expectedElements.add(new Tuple(3, "b"));
        expectedElements.add(new Tuple(3, "aa"));
        expectedElements.add(new Tuple(3, "bb"));

        Comparator<String> comparer = new StringLengthAndValueComparer();

        IOrderedEnumerable<Tuple> orderedEnumerable = Linq.orderBy(source, item -> item.value, Integer.class);

        // Act
        IOrderedEnumerable<Tuple> actualElements
            = Linq.thenBy(orderedEnumerable, item -> item.name, String.class, comparer);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: thenByDescending(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending1_nullSource_throwsException()
    {
        Linq.thenByDescending(null, item -> null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending1_nullKeySelector_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending1_nullKeyType_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, item -> null, null);
    }

    @Test
    public void thenByDescending1_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        IOrderedEnumerable<String> orderedEnumerable = Linq.orderByDescending(source, item -> null, String.class);

        // Act
        IOrderedEnumerable<String> actualElements
            = Linq.thenByDescending(orderedEnumerable, item -> item, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void thenByDescending1_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple(2, "c"));
        source.add(new Tuple(2, "a"));
        source.add(new Tuple(1, "c"));
        source.add(new Tuple(2, "b"));
        source.add(new Tuple(1, "a"));
        source.add(new Tuple(3, "a"));
        source.add(new Tuple(3, "c"));
        source.add(new Tuple(3, "b"));
        source.add(new Tuple(1, "b"));

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple(3, "c"));
        expectedElements.add(new Tuple(3, "b"));
        expectedElements.add(new Tuple(3, "a"));
        expectedElements.add(new Tuple(2, "c"));
        expectedElements.add(new Tuple(2, "b"));
        expectedElements.add(new Tuple(2, "a"));
        expectedElements.add(new Tuple(1, "c"));
        expectedElements.add(new Tuple(1, "b"));
        expectedElements.add(new Tuple(1, "a"));

        IOrderedEnumerable<Tuple> orderedEnumerable = Linq.orderByDescending(source, item -> item.value, Integer.class);

        // Act
        IOrderedEnumerable<Tuple> actualElements
            = Linq.thenByDescending(orderedEnumerable, item -> item.name, String.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: thenByDescending(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>, Comparator<TKey>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending2_nullSource_throwsException()
    {
        Linq.thenByDescending(null, item -> null, Object.class, new DummyComparer<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending2_nullKeySelector_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, null, Object.class, new DummyComparer<Object>());
    }

    @Test
    public void thenByDescending2_nullKeyType_noException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, item -> null, null, new DummyComparer<Object>());
    }

    @Test
    public void thenByDescending2_nullComparer_noException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, item -> null, Object.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenByDescending2_nullComparerAndKeyType_throwsException()
    {
        IOrderedEnumerable<Object> orderedEnumerable
            = Linq.orderByDescending(new ArrayList<Object>(), item -> null, Object.class);

        Linq.thenByDescending(orderedEnumerable, item -> null, null, null);
    }

    @Test
    public void thenByDescending2_emptyList_returnsEmptySequence()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        ArrayList<String> expectedElements = new ArrayList<>();

        IOrderedEnumerable<String> orderedEnumerable = Linq.orderByDescending(source, item -> item, String.class);

        // Act
        IOrderedEnumerable<String> actualElements
            = Linq.thenByDescending(orderedEnumerable, item -> item, String.class, null);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void thenByDescending2_listOfElements_ordersElements()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple(1, "aa"));
        source.add(new Tuple(3, "a"));
        source.add(new Tuple(3, "bb"));
        source.add(new Tuple(2, "b"));
        source.add(new Tuple(1, "a"));
        source.add(new Tuple(1, "b"));
        source.add(new Tuple(3, "b"));
        source.add(new Tuple(1, "bb"));
        source.add(new Tuple(2, "bb"));
        source.add(new Tuple(3, "aa"));
        source.add(new Tuple(2, "a"));
        source.add(new Tuple(2, "aa"));

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple(3, "bb"));
        expectedElements.add(new Tuple(3, "aa"));
        expectedElements.add(new Tuple(3, "b"));
        expectedElements.add(new Tuple(3, "a"));
        expectedElements.add(new Tuple(2, "bb"));
        expectedElements.add(new Tuple(2, "aa"));
        expectedElements.add(new Tuple(2, "b"));
        expectedElements.add(new Tuple(2, "a"));
        expectedElements.add(new Tuple(1, "bb"));
        expectedElements.add(new Tuple(1, "aa"));
        expectedElements.add(new Tuple(1, "b"));
        expectedElements.add(new Tuple(1, "a"));

        Comparator<String> comparer = new StringLengthAndValueComparer();

        IOrderedEnumerable<Tuple> orderedEnumerable = Linq.orderByDescending(source, item -> item.value, Integer.class);

        // Act
        IOrderedEnumerable<Tuple> actualElements
            = Linq.thenByDescending(orderedEnumerable, item -> item.name, String.class, comparer);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    private class DummyComparer<T> implements Comparator<T>
    {
        @Override
        public int compare(T o1, T o2)
        {
            return 0;
        }
    }

    private class StringLengthAndValueComparer implements Comparator<String>
    {
        @Override
        public int compare(String x, String y)
        {
            int difference = x.length() - y.length();
            if (difference != 0)
            {
                return difference;
            }

            return x.compareTo(y);
        }
    }

    private class Tuple
    {
        public Tuple(int value, String name)
        {
            this.value = value;
            this.name = name;
        }

        public int value;
        public String name;

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }

            Tuple other = (Tuple) obj;

            if (value != other.value)
            {
                return false;
            }

            if (name == null)
            {
                if (other.name != null)
                {
                    return false;
                }
            }
            else if (name.equals(other.name) == false)
            {
                return false;
            }

            return true;
        }
    }
}
