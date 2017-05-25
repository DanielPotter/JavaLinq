package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class ConcatAndUnionTests
{
    // region: concat(Iterable<TSource>, Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void concat1_nullFirst_throwsException()
    {
        Linq.concat(null, new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void concat1_nullSecond_throwsException()
    {
        Linq.concat(new ArrayList<Object>(), null);
    }

    @Test
    public void concat1_listsOfElements_concatsElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(4);
        second.add(5);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(5);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.concat(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void concat1_iterateTwice_concatsElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(4);
        second.add(5);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(5);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.concat(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: union(Iterable<TSource>, Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void union1_nullFirst_throwsException()
    {
        Linq.union(null, new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void union1_nullSecond_throwsException()
    {
        Linq.union(new ArrayList<Object>(), null);
    }

    @Test
    public void union1_listsOfElements_unifiesElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.union(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void union1_iterateTwice_unifiesElements()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);
        ArrayList<Integer> second = new ArrayList<>();
        second.add(2);
        second.add(4);
        second.add(6);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);
        expectedElements.add(4);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.union(first, second);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
