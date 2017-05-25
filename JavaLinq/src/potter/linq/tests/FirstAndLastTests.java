package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class FirstAndLastTests
{
    // region: first(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void first1_nullSource_throwsException()
    {
        Linq.first(null);
    }

    @Test(expected = IllegalStateException.class)
    public void first1_emptyList_throwsException()
    {
        Linq.first(new ArrayList<Object>());
    }

    @Test
    public void first1_listOfElements_returnsFirstElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 1;

        // Act
        Integer actual = Linq.first(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: first(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void first2_nullSource_throwsException()
    {
        Linq.first(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void first2_nullPredicate_throwsException()
    {
        Linq.first(new ArrayList<Object>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void first2_emptyList_throwsException()
    {
        Linq.first(new ArrayList<Object>(), item -> true);
    }

    @Test(expected = IllegalStateException.class)
    public void first2_noElementsMatch_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        Linq.first(source, item -> item > 3);
    }

    @Test
    public void first2_elementsMatch_returnsFirstMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 2;

        // Act
        Integer actual = Linq.first(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: firstOrDefault(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void firstOrDefault1_nullSource_throwsException()
    {
        Linq.firstOrDefault(null);
    }

    @Test
    public void firstOrDefault1_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.firstOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstOrDefault1_listOfElements_returnsFirstElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 1;

        // Act
        Integer actual = Linq.firstOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: firstOrDefault(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void firstOrDefault2_nullSource_throwsException()
    {
        Linq.firstOrDefault(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstOrDefault2_nullPredicate_throwsException()
    {
        Linq.firstOrDefault(new ArrayList<Object>(), null);
    }

    @Test
    public void firstOrDefault2_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.firstOrDefault(source, item -> true);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstOrDefault2_noElementsMatch_returnsNul()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = null;

        // Act
        Integer actual = Linq.firstOrDefault(source, item -> item > 3);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void firstOrDefault2_elementsMatch_returnsFirstMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 2;

        // Act
        Integer actual = Linq.firstOrDefault(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: last(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void last1_nullSource_throwsException()
    {
        Linq.last(null);
    }

    @Test(expected = IllegalStateException.class)
    public void last1_emptyList_throwsException()
    {
        Linq.last(new ArrayList<Object>());
    }

    @Test
    public void last1_listOfElements_returnsLastElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 3;

        // Act
        Integer actual = Linq.last(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: last(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void last2_nullSource_throwsException()
    {
        Linq.last(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void last2_nullPredicate_throwsException()
    {
        Linq.last(new ArrayList<Object>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void last2_emptyList_throwsException()
    {
        Linq.last(new ArrayList<Object>(), item -> true);
    }

    @Test(expected = IllegalStateException.class)
    public void last2_noElementsMatch_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        Linq.last(source, item -> item > 3);
    }

    @Test
    public void last2_elementsMatch_returnsFirstMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        Integer expected = 4;

        // Act
        Integer actual = Linq.last(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: lastOrDefault(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void lastOrDefault1_nullSource_throwsException()
    {
        Linq.lastOrDefault(null);
    }

    @Test
    public void lastOrDefault1_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.lastOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lastOrDefault1_listOfElements_returnsLastElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 3;

        // Act
        Integer actual = Linq.lastOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // region: lastOrDefault(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void lastOrDefault2_nullSource_throwsException()
    {
        Linq.lastOrDefault(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lastOrDefault2_nullPredicate_throwsException()
    {
        Linq.lastOrDefault(new ArrayList<Object>(), null);
    }

    @Test
    public void lastOrDefault2_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.lastOrDefault(source, item -> true);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lastOrDefault2_noElementsMatch_returnsNul()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = null;

        // Act
        Integer actual = Linq.lastOrDefault(source, item -> item > 3);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lastOrDefault2_elementsMatch_returnsLastMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        Integer expected = 4;

        // Act
        Integer actual = Linq.lastOrDefault(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion
}
