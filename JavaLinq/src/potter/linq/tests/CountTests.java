package potter.linq.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class CountTests
{
    // region: count(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void count1_nullSource_throwException()
    {
        Linq.count(null);
    }

    @Test
    public void count1_noElements_returnsZero()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();

        // Act
        int actualCount = Linq.count(elements);

        // Assert
        assertEquals(0, actualCount);
    }

    @Test
    public void count1_listOfElements_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        int expectedCount = elements.size();

        // Act
        int actualCount = Linq.count(elements);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    // endregion

    // region: count(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void count2_nullSource_throwException()
    {
        Linq.count(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void count2_nullPredicate_throwException()
    {
        Linq.count(new ArrayList<Object>(), null);
    }

    @Test
    public void count2_noElementsMatch_returnsZero()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();

        // Act
        int actualCount = Linq.count(elements, item -> true);

        // Assert
        assertEquals(0, actualCount);
    }

    @Test
    public void count2_someElementsMatch_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        elements.add(4);
        int expectedCount = 2;

        // Act
        int actualCount = Linq.count(elements, item -> item % 2 == 0);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void count2_allElementsMatch_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(2);
        elements.add(4);
        elements.add(6);
        elements.add(8);
        int expectedCount = elements.size();

        // Act
        int actualCount = Linq.count(elements, item -> item % 2 == 0);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    // endregion

    // region: longCount(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void longCount1_nullSource_throwException()
    {
        Linq.longCount(null);
    }

    @Test
    public void longCount1_noElements_returnsZero()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();

        // Act
        long actualCount = Linq.longCount(elements);

        // Assert
        assertEquals(0, actualCount);
    }

    @Test
    public void longCount1_listOfElements_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        int expectedCount = elements.size();

        // Act
        long actualCount = Linq.longCount(elements);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    // endregion

    // region: longCount(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void longCount2_nullSource_throwException()
    {
        Linq.longCount(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void longCount2_nullPredicate_throwException()
    {
        Linq.longCount(new ArrayList<Object>(), null);
    }

    @Test
    public void longCount2_noElementsMatch_returnsZero()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();

        // Act
        long actualCount = Linq.longCount(elements, item -> true);

        // Assert
        assertEquals(0, actualCount);
    }

    @Test
    public void longCount2_someElementsMatch_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        elements.add(4);
        int expectedCount = 2;

        // Act
        long actualCount = Linq.longCount(elements, item -> item % 2 == 0);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void longCount2_allElementsMatch_sequenceCounted()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(2);
        elements.add(4);
        elements.add(6);
        elements.add(8);
        int expectedCount = elements.size();

        // Act
        long actualCount = Linq.longCount(elements, item -> item % 2 == 0);

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    // endregion
}
