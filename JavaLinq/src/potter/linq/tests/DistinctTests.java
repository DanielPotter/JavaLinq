package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class DistinctTests
{
    // region: distinct(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void distinct1_nullSource_throwsException()
    {
        Linq.distinct(null);
    }

    @Test
    public void distinct1_listWithDuplicates_returnsUniqueElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(1);
        source.add(2);
        source.add(2);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(4);
        source.add(4);
        source.add(5);
        source.add(5);
        source.add(5);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(5);

        // Act
        Iterable<Integer> actualElements = Linq.distinct(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void distinct1_iterateTwice_returnsUniqueElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(1);
        source.add(2);
        source.add(2);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(4);
        source.add(4);
        source.add(5);
        source.add(5);
        source.add(5);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(5);

        // Act
        Iterable<Integer> actualElements = Linq.distinct(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
