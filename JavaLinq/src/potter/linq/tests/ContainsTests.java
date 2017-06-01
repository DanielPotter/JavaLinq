package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.EqualityComparer;
import potter.linq.IEqualityComparer;
import potter.linq.Linq;

public class ContainsTests
{
    private static final Iterable<Object> NullIterable = null;
    private static final Object NullObject = null;
    private static final IEqualityComparer<Object> NullComparer = null;
    private static final Class<Object> NullClass = null;

    private static final Iterable<Object> DummyIterable = new ArrayList<>();
    private static final Object DummyObject = new Object();
    private static final IEqualityComparer<Object> DummyComparer = new IEqualityComparer<Object>()
    {
        @Override
        public boolean equals(Object x, Object y)
        {
            return false;
        }

        @Override
        public int hashCode(Object obj)
        {
            return 0;
        }
    };
    private static final Class<Object> DummyClass = Object.class;

    // region: contains(Iterable<TSource>, TSource)

    @Test(expected = IllegalArgumentException.class)
    public void contains1_nullSource_throwsException()
    {
        Linq.contains(NullIterable, DummyObject);
    }

    @Test
    public void contains1_nullValue_noExceptions()
    {
        Linq.contains(DummyIterable, NullObject);
    }

    @Test
    public void contains1_elementExistsInList_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 3);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains1_elementDoesNotExistInList_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 6);

        // Assert
        Assert.assertFalse(found);
    }

    @Test
    public void contains1_nullElementExistsInIterable_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(null);

        Iterable<Integer> iterableSource = new Iterable<Integer>()
        {
            @Override
            public Iterator<Integer> iterator()
            {
                return source.iterator();
            }
        };

        // Act
        boolean found = Linq.contains(iterableSource, null);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains1_elementExistsInIterable_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        Iterable<Integer> iterableSource = new Iterable<Integer>()
        {
            @Override
            public Iterator<Integer> iterator()
            {
                return source.iterator();
            }
        };

        // Act
        boolean found = Linq.contains(iterableSource, 3);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains1_elementDoesNotExistsInIterable_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        Iterable<Integer> iterableSource = new Iterable<Integer>()
        {
            @Override
            public Iterator<Integer> iterator()
            {
                return source.iterator();
            }
        };

        // Act
        boolean found = Linq.contains(iterableSource, 6);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains1_listOfElements_listNotIterated()
    {
        // Arrange
        Reference<Boolean> containsCalled = new Reference<>(false);

        ArrayList<Integer> source = new ArrayList<Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean contains(Object item)
            {
                containsCalled.value = true;
                return super.contains(item);
            }

            @Override
            public Iterator<Integer> iterator()
            {
                Assert.fail("Lists should not be iterated.");
                return super.iterator();
            }
        };
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        Linq.contains(source, 3);

        // Assert
        Assert.assertTrue(containsCalled.value);
    }

    // endregion

    // region: contains(Iterable<TSource>, TSource, Class<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void contains2_nullSource_throwsException()
    {
        Linq.contains(NullIterable, DummyObject, DummyClass);
    }

    @Test
    public void contains2_nullValue_noExceptions()
    {
        Linq.contains(DummyIterable, NullObject, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains2_nullType_throwsException()
    {
        Linq.contains(DummyIterable, DummyObject, NullClass);
    }

    @Test
    public void contains2_nullElementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(null);

        // Act
        boolean found = Linq.contains(source, null, Integer.class);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains2_elementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 3, Integer.class);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains2_elementDoesNotExist_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 6, Integer.class);

        // Assert
        Assert.assertFalse(found);
    }

    @Test
    public void contains2_listOfElements_listIterated()
    {
        // Arrange
        Reference<Boolean> containsCalled = new Reference<>(false);
        Reference<Boolean> iteratorCalled = new Reference<>(false);

        ArrayList<Integer> source = new ArrayList<Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean contains(Object item)
            {
                containsCalled.value = true;
                return super.contains(item);
            }

            @Override
            public Iterator<Integer> iterator()
            {
                iteratorCalled.value = true;
                return super.iterator();
            }
        };
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        Linq.contains(source, 3, Integer.class);

        // Assert
        Assert.assertFalse(containsCalled.value);
        Assert.assertTrue(iteratorCalled.value);
    }

    // endregion

    // region: contains(Iterable<TSource>, TSource, IEqualityComparer<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void contains3_nullSource_throwsException()
    {
        Linq.contains(NullIterable, DummyObject, DummyComparer);
    }

    @Test
    public void contains3_nullValue_noExceptions()
    {
        Linq.contains(DummyIterable, NullObject, DummyComparer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains3_nullComparer_throwsException()
    {
        Linq.contains(DummyIterable, DummyObject, NullComparer);
    }

    @Test
    public void contains3_nullElementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(null);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, null, comparer);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains3_elementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, 3, comparer);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains3_elementDoesNotExist_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, 6, comparer);

        // Assert
        Assert.assertFalse(found);
    }

    @Test
    public void contains3_listOfElements_listIterated()
    {
        // Arrange
        Reference<Boolean> containsCalled = new Reference<>(false);
        Reference<Boolean> iteratorCalled = new Reference<>(false);

        ArrayList<Integer> source = new ArrayList<Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean contains(Object item)
            {
                containsCalled.value = true;
                return super.contains(item);
            }

            @Override
            public Iterator<Integer> iterator()
            {
                iteratorCalled.value = true;
                return super.iterator();
            }
        };
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        Linq.contains(source, 3, comparer);

        // Assert
        Assert.assertFalse(containsCalled.value);
        Assert.assertTrue(iteratorCalled.value);
    }

    // endregion

    // @formatter:off
    // region: contains(Iterable<TSource>, TSource, IEqualityComparer<TSource>, Class<TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void contains4_nullSource_throwsException()
    {
        Linq.contains(NullIterable, DummyObject, DummyComparer, DummyClass);
    }

    @Test
    public void contains4_nullValue_noExceptions()
    {
        Linq.contains(DummyIterable, NullObject, DummyComparer, DummyClass);
    }

    @Test
    public void contains4_nullComparer_noExceptions()
    {
        Linq.contains(DummyIterable, DummyObject, NullComparer, DummyClass);
    }

    @Test
    public void contains4_nullType_noExceptions()
    {
        Linq.contains(DummyIterable, DummyObject, DummyComparer, NullClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains4_nullComparerAndType_throwsException()
    {
        Linq.contains(DummyIterable, DummyObject, NullComparer, NullClass);
    }

    @Test
    public void contains4_nullElementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(null);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, null, comparer, Integer.class);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains4_elementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, 3, comparer, Integer.class);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains4_elementDoesNotExist_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        boolean found = Linq.contains(source, 6, comparer, Integer.class);

        // Assert
        Assert.assertFalse(found);
    }

    @Test
    public void contains4_listOfElements_listIterated()
    {
        // Arrange
        Reference<Boolean> containsCalled = new Reference<>(false);
        Reference<Boolean> iteratorCalled = new Reference<>(false);

        ArrayList<Integer> source = new ArrayList<Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean contains(Object item)
            {
                containsCalled.value = true;
                return super.contains(item);
            }

            @Override
            public Iterator<Integer> iterator()
            {
                iteratorCalled.value = true;
                return super.iterator();
            }
        };
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        IEqualityComparer<Integer> comparer = EqualityComparer.getDefault(Integer.class);

        // Act
        Linq.contains(source, 3, comparer, Integer.class);

        // Assert
        Assert.assertFalse(containsCalled.value);
        Assert.assertTrue(iteratorCalled.value);
    }

    // endregion
}
