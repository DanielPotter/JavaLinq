package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import potter.linq.EqualityComparer;
import potter.linq.IEnumerable;
import potter.linq.IEqualityComparer;
import potter.linq.IGrouping;
import potter.linq.Linq;

public class GroupByTests
{
    private static final ArrayList<Object> NullSource = null;
    private static final Function<Object, Object> NullElementSelector = null;
    private static final IEqualityComparer<Object> NullComparer = null;
    private static final BiFunction<Object, IEnumerable<Object>, Object> NullResultSelector = null;
    private static final Class<Object> NullElementType = null;

    private static final ArrayList<Object> DummySource = new ArrayList<>();
    private static final Function<Object, Object> DummyElementSelector = item -> new Object();
    private static final IEqualityComparer<Object> DummyComparer = new DummyEqualityComparer<>();
    private static final BiFunction<Object, IEnumerable<Object>, Object> DummyResultSelector
        = (item, group) -> new Object();
    private static final Class<Object> DummyElementType = Object.class;

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, Class<TKey>, Class<TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy1_nullSource_throwsException()
    {
        Linq.groupBy(NullSource, DummyElementSelector, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy1_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource, NullElementSelector, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy1_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource, DummyElementSelector, NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy1_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource, DummyElementSelector, DummyElementType, NullElementType);
    }

    @Test
    public void groupBy1_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<IGrouping<Integer, Tuple>> expected = new ArrayList<>();

        MockGrouping<Integer, Tuple> expectedGroup1 = new MockGrouping<>(1);
        expectedGroup1.elements.add(new Tuple("a", 1));
        expectedGroup1.elements.add(new Tuple("b", 1));
        expectedGroup1.elements.add(new Tuple("c", 1));
        expected.add(expectedGroup1);

        MockGrouping<Integer, Tuple> expectedGroup2 = new MockGrouping<>(2);
        expectedGroup2.elements.add(new Tuple("a", 2));
        expectedGroup2.elements.add(new Tuple("b", 2));
        expectedGroup2.elements.add(new Tuple("c", 2));
        expected.add(expectedGroup2);

        MockGrouping<Integer, Tuple> expectedGroup3 = new MockGrouping<>(3);
        expectedGroup3.elements.add(new Tuple("a", 3));
        expectedGroup3.elements.add(new Tuple("b", 3));
        expectedGroup3.elements.add(new Tuple("c", 3));
        expected.add(expectedGroup3);

        // Act
        IEnumerable<IGrouping<Integer, Tuple>> actual
            = Linq.groupBy(source, item -> item.value, Integer.class, Tuple.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, IEqualityComparer<TKey>, Class<TKey>, Class<TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy2_nullSource_throwsException()
    {
        Linq.groupBy(NullSource, DummyElementSelector, DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy2_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource, NullElementSelector, DummyComparer, DummyElementType, DummyElementType);
    }

    @Test
    public void groupBy2_nullComparer_noExceptions()
    {
        Linq.groupBy(DummySource, DummyElementSelector, NullComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy2_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource, DummyElementSelector, DummyComparer, NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy2_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource, DummyElementSelector, DummyComparer, DummyElementType, NullElementType);
    }

    @Test
    public void groupBy2_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<IGrouping<Integer, Tuple>> expected = new ArrayList<>();

        MockGrouping<Integer, Tuple> expectedGroup1 = new MockGrouping<>(1);
        expectedGroup1.elements.add(new Tuple("a", 1));
        expectedGroup1.elements.add(new Tuple("b", 1));
        expectedGroup1.elements.add(new Tuple("c", 1));
        expected.add(expectedGroup1);

        MockGrouping<Integer, Tuple> expectedGroup2 = new MockGrouping<>(2);
        expectedGroup2.elements.add(new Tuple("a", 2));
        expectedGroup2.elements.add(new Tuple("b", 2));
        expectedGroup2.elements.add(new Tuple("c", 2));
        expected.add(expectedGroup2);

        MockGrouping<Integer, Tuple> expectedGroup3 = new MockGrouping<>(3);
        expectedGroup3.elements.add(new Tuple("a", 3));
        expectedGroup3.elements.add(new Tuple("b", 3));
        expectedGroup3.elements.add(new Tuple("c", 3));
        expected.add(expectedGroup3);

        // Act
        IEnumerable<IGrouping<Integer, Tuple>> actual
            = Linq.groupBy(source, item -> item.value,
                EqualityComparer.getDefault(Integer.class), Integer.class, Tuple.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, Function<TSource, TElement>, Class<TKey>, Class<TElement>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy3_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyElementSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy3_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyElementSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy3_nullElementSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullElementSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy3_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector,
            NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy3_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector,
            DummyElementType, NullElementType);
    }

    @Test
    public void groupBy3_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<IGrouping<Integer, String>> expected = new ArrayList<>();

        MockGrouping<Integer, String> expectedGroup1 = new MockGrouping<>(1);
        expectedGroup1.elements.add("a");
        expectedGroup1.elements.add("b");
        expectedGroup1.elements.add("c");
        expected.add(expectedGroup1);

        MockGrouping<Integer, String> expectedGroup2 = new MockGrouping<>(2);
        expectedGroup2.elements.add("a");
        expectedGroup2.elements.add("b");
        expectedGroup2.elements.add("c");
        expected.add(expectedGroup2);

        MockGrouping<Integer, String> expectedGroup3 = new MockGrouping<>(3);
        expectedGroup3.elements.add("a");
        expectedGroup3.elements.add("b");
        expectedGroup3.elements.add("c");
        expected.add(expectedGroup3);

        // Act
        IEnumerable<IGrouping<Integer, String>> actual
            = Linq.groupBy(source, item -> item.value, item -> item.name,
                Integer.class, String.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, Function<TSource, TElement>, IEqualityComparer<TKey>, Class<TKey>, Class<TElement>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy4_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyElementSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy4_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyElementSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy4_nullElementSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullElementSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test
    public void groupBy4_nullComparer_noExceptions()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector,
            NullComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy4_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector,
            DummyComparer, NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy4_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector,
            DummyComparer, DummyElementType, NullElementType);
    }

    @Test
    public void groupBy4_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<IGrouping<Integer, String>> expected = new ArrayList<>();

        MockGrouping<Integer, String> expectedGroup1 = new MockGrouping<>(1);
        expectedGroup1.elements.add("a");
        expectedGroup1.elements.add("b");
        expectedGroup1.elements.add("c");
        expected.add(expectedGroup1);

        MockGrouping<Integer, String> expectedGroup2 = new MockGrouping<>(2);
        expectedGroup2.elements.add("a");
        expectedGroup2.elements.add("b");
        expectedGroup2.elements.add("c");
        expected.add(expectedGroup2);

        MockGrouping<Integer, String> expectedGroup3 = new MockGrouping<>(3);
        expectedGroup3.elements.add("a");
        expectedGroup3.elements.add("b");
        expectedGroup3.elements.add("c");
        expected.add(expectedGroup3);

        // Act
        IEnumerable<IGrouping<Integer, String>> actual
            = Linq.groupBy(source, item -> item.value, item -> item.name,
                EqualityComparer.getDefault(Integer.class), Integer.class, String.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, BiFunction<TKey, IEnumerable<TSource>, TResult>, Class<TKey>, Class<TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy5_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy5_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy5_nullResultSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy5_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyResultSelector,
            NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy5_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyResultSelector,
            DummyElementType, NullElementType);
    }

    @Test
    public void groupBy5_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<Tuple> expected = new ArrayList<>();
        expected.add(new Tuple("a", 1));
        expected.add(new Tuple("a", 2));
        expected.add(new Tuple("a", 3));

        // Act
        IEnumerable<Tuple> actual
            = Linq.groupBy(source, item -> item.value, (item, group) -> group.first(), Integer.class, Tuple.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, BiFunction<TKey, IEnumerable<TSource>, TResult>, IEqualityComparer<TKey>, Class<TKey>, Class<TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy6_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy6_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy6_nullResultSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test
    public void groupBy6_nullComparer_noExceptions()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyResultSelector,
            NullComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy6_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyResultSelector,
            DummyComparer, NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy6_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, NullElementType);
    }

    @Test
    public void groupBy6_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<Tuple> expected = new ArrayList<>();
        expected.add(new Tuple("a", 1));
        expected.add(new Tuple("a", 2));
        expected.add(new Tuple("a", 3));

        // Act
        IEnumerable<Tuple> actual
            = Linq.groupBy(source, item -> item.value, (item, group) -> group.first(),
                EqualityComparer.getDefault(Integer.class), Integer.class, Tuple.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, Function<TSource, TElement>, BiFunction<TKey, IEnumerable<TElement>, TResult>, Class<TKey>, Class<TElement>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyElementSelector, DummyResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullElementSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullElementSelector, DummyResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullResultSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, NullResultSelector,
            DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy7_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            DummyElementType, NullElementType);
    }

    @Test
    public void groupBy7_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("a");
        expected.add("a");

        // Act
        IEnumerable<String> actual
            = Linq.groupBy(source, item -> item.value, item -> item.name, (item, group) -> group.first(),
                Integer.class, String.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupBy(Iterable<TSource>, Function<TSource, TKey>, Function<TSource, TElement>, BiFunction<TKey, IEnumerable<TElement>, TResult>, IEqualityComparer<TKey>, Class<TKey>, Class<TElement>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullSource_throwsException()
    {
        Linq.groupBy(NullSource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullKeySelector_throwsException()
    {
        Linq.groupBy(DummySource,
            NullElementSelector, DummyElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullElementSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, NullElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullResultSelector_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, NullResultSelector,
            DummyComparer, DummyElementType, DummyElementType);
    }

    @Test
    public void groupBy8_nullComparer_noExceptions()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            NullComparer, DummyElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullKeyType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            DummyComparer, NullElementType, DummyElementType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupBy8_nullElementType_throwsException()
    {
        Linq.groupBy(DummySource,
            DummyElementSelector, DummyElementSelector, DummyResultSelector,
            DummyComparer, DummyElementType, NullElementType);
    }

    @Test
    public void groupBy8_listOfElements_grouped()
    {
        // Arrange
        ArrayList<Tuple> source = new ArrayList<>();
        source.add(new Tuple("a", 1));
        source.add(new Tuple("b", 1));
        source.add(new Tuple("c", 1));
        source.add(new Tuple("a", 2));
        source.add(new Tuple("b", 2));
        source.add(new Tuple("c", 2));
        source.add(new Tuple("a", 3));
        source.add(new Tuple("b", 3));
        source.add(new Tuple("c", 3));

        ArrayList<String> expected = new ArrayList<>();
        expected.add("a");
        expected.add("a");
        expected.add("a");

        // Act
        IEnumerable<String> actual
            = Linq.groupBy(source, item -> item.value, item -> item.name, (item, group) -> group.first(),
                EqualityComparer.getDefault(Integer.class), Integer.class, String.class);

        // Assert
        CollectionAssert.assertNestedSequencesEquals(expected, actual);
    }

    // endregion

    private static class DummyEqualityComparer<T> implements IEqualityComparer<T>
    {
        @Override
        public boolean equals(T x, T y)
        {
            return true;
        }

        @Override
        public int hashCode(T obj)
        {
            return 0;
        }
    }

    private static class MockGrouping<TKey, TElement> implements IGrouping<TKey, TElement>
    {
        public MockGrouping(TKey key)
        {
            this(key, new ArrayList<TElement>(3));
        }

        public MockGrouping(TKey key, ArrayList<TElement> elements)
        {
            this.key = key;
            this.elements = elements;
        }

        private final TKey key;
        private final ArrayList<TElement> elements;

        @Override
        public Iterator<TElement> iterator()
        {
            return elements.iterator();
        }

        @Override
        public TKey getKey()
        {
            return key;
        }
    }

    private static class Tuple
    {
        public Tuple(String name, Integer value)
        {
            this.name = name;
            this.value = value;
        }

        public String name;
        public Integer value;

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
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

            if (obj instanceof Tuple == false)
            {
                return false;
            }

            Tuple other = (Tuple) obj;

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

            if (value == null)
            {
                if (other.value != null)
                {
                    return false;
                }
            }
            else if (value.equals(other.value) == false)
            {
                return false;
            }

            return true;
        }
    }
}
