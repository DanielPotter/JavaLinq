package potter.linq.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class CountTests
{
    @Test(expected = IllegalArgumentException.class)
    public void count_nullSource_throwException()
    {
        Linq.count(null);
    }

    @Test
    public void count_noElemens_returnsZero()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();

        // Act
        int actualCount = Linq.count(elements);

        // Assert
        assertEquals(0, actualCount);
    }

    @Test
    public void count_listOfElemens_sequenceCounted()
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
}
