package potter.linq.tests;

import static potter.linq.tests.CollectionAssert.assertSequenceEquals;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class WhereTests
{
    @Test(expected = IllegalArgumentException.class)
    public void where_nullSource_throwsException()
    {
        Linq.where(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void where_nullPredicate_throwsException()
    {
        Linq.where(new ArrayList<Object>(), null);
    }

    @Test
    public void where_listOfElements_filtersElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(0);
        source.add(5);
        source.add(10);
        source.add(15);
        source.add(20);
        source.add(25);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(15);
        expectedElements.add(20);
        expectedElements.add(25);

        // Act
        Iterable<Integer> actualElements = Linq.where(source, item -> item > 10);

        // Assert
        assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void where_iterateTwice_filtersElements()
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
        expectedElements.add(2);
        expectedElements.add(4);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.where(source, item -> item % 2 == 0);

        // Assert
        assertSequenceEquals(expectedElements, actualElements);
        assertSequenceEquals(expectedElements, actualElements);
    }
}
