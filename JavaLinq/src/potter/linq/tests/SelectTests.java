package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class SelectTests
{
    @Test(expected = IllegalArgumentException.class)
    public void select_nullSource_throwsException()
    {
        Linq.select(null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void select_nullSelector_throwsException()
    {
        Linq.select(new ArrayList<Object>(), null);
    }

    @Test
    public void select_listOfElements_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, item -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void select_iterateTwice_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, item -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }
}
