package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.IEnumerable;
import potter.linq.Linq;

public class CreationTests
{
    // region: asEnumerable(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void asEnumerable1_nullSource_throwsException()
    {
        Linq.asEnumerable((Iterable<Object>) null);
    }

    @Test
    public void asEnumerable1_listOfElements_containsSameElements()
    {
        // Arrange
        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        IEnumerable<Integer> actualElements = Linq.asEnumerable(expectedElements);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void asEnumerable1_iterateTwice_containsSameElements()
    {
        // Arrange
        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        IEnumerable<Integer> actualElements = Linq.asEnumerable(expectedElements);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: asEnumerable(TSource[])

    @Test(expected = IllegalArgumentException.class)
    public void asEnumerable2_nullSource_throwsException()
    {
        Linq.asEnumerable((Object[]) null);
    }

    @Test
    public void asEnumerable2_arrayOfElements_containsSameElements()
    {
        // Arrange
        Integer[] source =
        {
            1, 2, 3,
        };
        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        IEnumerable<Integer> actualElements = Linq.asEnumerable(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void asEnumerable2_iterateTwice_containsSameElements()
    {
        // Arrange
        Integer[] source =
        {
            1, 2, 3,
        };
        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        IEnumerable<Integer> actualElements = Linq.asEnumerable(source);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: empty(Class<TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void empty1_nullType_throwsException()
    {
        Linq.empty((Class<Object>) null);
    }

    @Test
    public void empty1_validType_containsNoElements()
    {
        // Arrange
        ArrayList<Integer> expectedElements = new ArrayList<>();

        // Act
        IEnumerable<Integer> actualElements = Linq.empty(Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void empty1_iterateTwice_containsNoElements()
    {
        // Arrange
        ArrayList<Integer> expectedElements = new ArrayList<>();

        // Act
        IEnumerable<Integer> actualElements = Linq.empty(Integer.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
