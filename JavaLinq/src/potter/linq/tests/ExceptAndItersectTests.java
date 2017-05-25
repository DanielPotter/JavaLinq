package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class ExceptAndItersectTests
{
    // region: except(Iterable<TSource>, Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void except1_nullFirst_throwsException()
    {
        Linq.except(null, new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void except1_nullSecond_throwsException()
    {
        Linq.except(new ArrayList<Object>(), null);
    }

    @Test
    public void except1_listsOfElements_excludesElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(4);
        first.add(5);
        first.add(6);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(3);
        expectedElements.add(5);

        // Act
        Iterable<Integer> actualElements = Linq.except(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void except1_iterateTwice_selectsElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(4);
        first.add(5);
        first.add(6);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(3);
        expectedElements.add(5);

        // Act
        Iterable<Integer> actualElements = Linq.except(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: intersect(Iterable<TSource>, Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void intersect1_nullFirst_throwsException()
    {
        Linq.intersect(null, new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void intersect1_nullSecond_throwsException()
    {
        Linq.intersect(new ArrayList<Object>(), null);
    }

    @Test
    public void intersect1_listsOfElements_selectsElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(4);
        first.add(5);
        first.add(6);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);
        second.add(8);
        second.add(10);
        second.add(12);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(4);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.intersect(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void intersect1_iterateTwice_selectsElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        first.add(4);
        first.add(5);
        first.add(6);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);
        second.add(8);
        second.add(10);
        second.add(12);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(4);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.intersect(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
