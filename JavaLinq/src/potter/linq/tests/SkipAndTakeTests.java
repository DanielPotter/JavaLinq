package potter.linq.tests;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class SkipAndTakeTests
{
    // region: skip(Iterable<TSource>, int)

    @Test(expected = IllegalArgumentException.class)
    public void skip1_nullSource_throwsException()
    {
        Linq.skip(null, 0);
    }

    @Test
    public void skip1_listOfElements_skipsFirstElements()
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
        expectedElements.add(4);
        expectedElements.add(5);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.skip(source, 3);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void skip1_iterateTwice_skipsFirstElements()
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
        expectedElements.add(4);
        expectedElements.add(5);
        expectedElements.add(6);

        // Act
        Iterable<Integer> actualElements = Linq.skip(source, 3);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: skipWhile(Iterable<TSource>, Function<TSource, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void skipWhile1_nullSource_throwsException()
    {
        Linq.skipWhile(null, item -> false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skipWhile1_nullPredicate_throwsException()
    {
        Linq.skipWhile(new ArrayList<Object>(), (Function<Object, Boolean>) null);
    }

    @Test
    public void skipWhile1_listOfElements_skipsFirstElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.skipWhile(source, item -> item % 2 == 1);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void skipWhile1_iterateTwice_skipsFirstElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.skipWhile(source, item -> item % 2 == 1);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: skipWhile(Iterable<TSource>, BiFunction<TSource, Integer, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void skipWhile2_nullSource_throwsException()
    {
        Linq.skipWhile(null, (item, index) -> false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void skipWhile2_nullPredicate_throwsException()
    {
        Linq.skipWhile(new ArrayList<Object>(), (BiFunction<Object, Integer, Boolean>) null);
    }

    @Test
    public void skipWhile2_listOfElements_skipsFirstElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.skipWhile(source, (item, index) -> item % 2 == 1);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void skipWhile2_iterateTwice_skipsFirstElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<Integer> expectedElements = new ArrayList<>();
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.skipWhile(source, (item, index) -> item % 2 == 1);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void skipWhile2_listOfElements_indexIncrementsByOne()
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
        expectedElements.add(4);
        expectedElements.add(5);
        expectedElements.add(6);

        Reference<Integer> count = new Reference<>(0);

        // Act
        Iterable<Integer> actualElements = Linq.skipWhile(source, (item, index) ->
        {
            Assert.assertEquals(count.value++, index);
            return item < 4;
        });

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        Assert.assertEquals(4, (int) count.value);
    }

    // endregion

    // region: take(Iterable<TSource>, int)

    @Test(expected = IllegalArgumentException.class)
    public void take1_nullSource_throwsException()
    {
        Linq.take(null, 0);
    }

    @Test
    public void take1_listOfElements_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.take(source, 3);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void take1_iterateTwice_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.take(source, 3);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: takeWhile(Iterable<TSource>, Function<TSource, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void takeWhile1_nullSource_throwsException()
    {
        Linq.takeWhile(null, item -> false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void takeWhile1_nullPredicate_throwsException()
    {
        Linq.takeWhile(new ArrayList<Object>(), (Function<Object, Boolean>) null);
    }

    @Test
    public void takeWhile1_listOfElements_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.takeWhile(source, item -> item % 2 == 1 || item == 2);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void takeWhile1_iterateTwice_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.takeWhile(source, item -> item % 2 == 1 || item == 2);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: takeWhile(Iterable<TSource>, BiFunction<TSource, Integer, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void takeWhile2_nullSource_throwsException()
    {
        Linq.takeWhile(null, (item, index) -> false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void takeWhile2_nullPredicate_throwsException()
    {
        Linq.takeWhile(new ArrayList<Object>(), (BiFunction<Object, Integer, Boolean>) null);
    }

    @Test
    public void takeWhile2_listOfElements_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.takeWhile(source, (item, index) -> item % 2 == 1 || item == 2);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void takeWhile2_iterateTwice_takesFirstElements()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        // Act
        Iterable<Integer> actualElements = Linq.takeWhile(source, (item, index) -> item % 2 == 1 || item == 2);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void takeWhile2_listOfElements_indexIncrementsByOne()
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
        expectedElements.add(1);
        expectedElements.add(2);
        expectedElements.add(3);

        Reference<Integer> count = new Reference<>(0);

        // Act
        Iterable<Integer> actualElements = Linq.takeWhile(source, (item, index) ->
        {
            Assert.assertEquals(count.value++, index);
            return item < 4;
        });

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        Assert.assertEquals(4, (int) count.value);
    }

    // endregion
}
