package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import potter.linq.Linq;

public class CastAndOfTypeTests
{
    private static final Iterable<Object> NullIterable = null;
    private static final Class<Object> NullClass = null;

    private static final Iterable<Object> DummyIterable = new ArrayList<>();
    private static final Class<Object> DummyClass = Object.class;

    // region: ofType(Iterable<?>, Class<TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void ofType1_nullSource_throwsException()
    {
        Linq.ofType(NullIterable, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofType1_nullType_throwsException()
    {
        Linq.ofType(DummyIterable, NullClass);
    }

    @Test
    public void ofType1_listOfElements_filtersIntegers()
    {
        // Arrange
        ArrayList<Object> source = new ArrayList<>();
        source.add(1);
        source.add("1");
        source.add(2);
        source.add(2d);
        source.add(3);
        source.add(new Object());

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.ofType(source, Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void ofType1_iterateTwice_filtersIntegers()
    {
        // Arrange
        ArrayList<Object> source = new ArrayList<>();
        source.add(1);
        source.add("1");
        source.add(2);
        source.add(2d);
        source.add(3);
        source.add(new Object());

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.ofType(source, Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: cast(Iterable<?>, Class<TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void cast1_nullSource_throwsException()
    {
        Linq.cast(NullIterable, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cast1_nullType_throwsException()
    {
        Linq.cast(DummyIterable, NullClass);
    }

    @Test(expected = ClassCastException.class)
    public void cast1_listOfObjects_throwsException()
    {
        // Arrange
        ArrayList<Object> source = new ArrayList<>();
        source.add(new Object());
        source.add(new Object());
        source.add(new Object());

        // Act
        Iterable<Integer> actual = Linq.cast(source, Integer.class);

        Iterator<Integer> iterator = actual.iterator();
        while (iterator.hasNext())
        {
            iterator.next();
        }
    }

    @Test
    public void cast1_listOfIntegers_castsToIntegers()
    {
        // Arrange
        ArrayList<Object> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.cast(source, Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void cast1_iterateTwice_castsToIntegers()
    {
        // Arrange
        ArrayList<Object> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.cast(source, Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
