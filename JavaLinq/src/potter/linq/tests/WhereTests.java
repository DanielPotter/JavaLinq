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
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(0);
        elements.add(5);
        elements.add(10);
        elements.add(15);
        elements.add(20);
        elements.add(25);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(15);
        expectedElements.add(20);
        expectedElements.add(25);

        // Act
        Iterable<Integer> actualElements = Linq.where(elements, item -> item > 10);

        // Assert
        assertSequenceEquals(expectedElements, actualElements);
    }
}
