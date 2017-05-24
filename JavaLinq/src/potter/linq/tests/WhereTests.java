package potter.linq.tests;

import static org.junit.Assert.assertEquals;
import static potter.linq.tests.CollectionAssert.assertSequenceEquals;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import potter.linq.Linq;

public class WhereTests
{
    // region: where(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void where1_nullSource_throwsException()
    {
        Linq.where(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void where1_nullPredicate_throwsException()
    {
        Linq.where(new ArrayList<Object>(), (Function<Object, Boolean>) null);
    }

    @Test
    public void where1_listOfElements_filtersElements()
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
    }

    @Test
    public void where1_iterateTwice_filtersElements()
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

    // endregion

    // region: where(Iterable<TSource>, BiFunction<TSource, Integer, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void where2_nullSource_throwsException()
    {
        Linq.where(null, (item, index) -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void where2_nullPredicate_throwsException()
    {
        Linq.where(new ArrayList<Object>(), (BiFunction<Object, Integer, Boolean>) null);
    }

    @Test
    public void where2_listOfElements_filtersElements()
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
    }

    @Test
    public void where2_iterateTwice_filtersElements()
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

    @Test
    public void where2_listOfElements_indexIncrementsByOne()
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

        Reference<Integer> count = new Reference<>(0);

        // Act
        Iterable<Integer> actualElements = Linq.where(source, (item, index) ->
        {
            assertEquals(count.value++, index);
            return item % 2 == 0;
        });

        // Assert
        assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
