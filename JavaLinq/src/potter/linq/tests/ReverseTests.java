package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class ReverseTests
{
    // region: reverse(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void reverse1_nullSource_throwsException()
    {
        Linq.reverse(null);
    }

    @Test
    public void reverse1_listOfElements_reversesElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);
        source.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(6);
        expectedElements.add(5);
        expectedElements.add(4);
        expectedElements.add(3);
        expectedElements.add(2);
        expectedElements.add(1);

        // Act
        Iterable<Integer> actualElements = Linq.reverse(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void reverse1_iterateTwice_reversesElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);
        source.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(6);
        expectedElements.add(5);
        expectedElements.add(4);
        expectedElements.add(3);
        expectedElements.add(2);
        expectedElements.add(1);

        // Act
        Iterable<Integer> actualElements = Linq.reverse(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
