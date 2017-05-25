package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class OfTypeTests
{
    // region: ofType(Iterable<?>, Class<TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void ofType1_nullSource_throwsException()
    {
        Linq.ofType(null, Object.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ofType1_nullType_throwsException()
    {
        Linq.ofType(new ArrayList<Object>(), null);
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
}
