package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class SingleTests
{
    // region: single(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void single1_nullSource_throwsException()
    {
        Linq.single(null);
    }

    @Test(expected = IllegalStateException.class)
    public void single1_emptyList_throwsException()
    {
        Linq.single(new ArrayList<Object>());
    }

    @Test
    public void single1_listOfOneElement_returnsSingleElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);

        Integer expected = 1;

        // Act
        Integer actual = Linq.single(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void single1_listOfManyElements_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        Linq.single(source);
    }

    // endregion

    // region: single(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void single2_nullSource_throwsException()
    {
        Linq.single(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void single2_nullPredicate_throwsException()
    {
        Linq.single(new ArrayList<Object>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void single2_emptyList_throwsException()
    {
        Linq.single(new ArrayList<Object>(), item -> true);
    }

    @Test(expected = IllegalStateException.class)
    public void single2_noElementsMatch_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        Linq.single(source, item -> item > 3);
    }

    @Test
    public void single2_oneElementMatches_returnsSingleMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 2;

        // Act
        Integer actual = Linq.single(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void single2_manyElementsMatch_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        Linq.single(source, item -> item % 2 == 0);
    }

    // endregion

    // region: singleOrDefault(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void singleOrDefault1_nullSource_throwsException()
    {
        Linq.singleOrDefault(null);
    }

    @Test
    public void singleOrDefault1_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.singleOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void singleOrDefault1_listOfOneElement_returnsSingleElement()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);

        Integer expected = 1;

        // Act
        Integer actual = Linq.singleOrDefault(source);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void singleOrDefault1_listOfManyElements_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        Linq.singleOrDefault(source);
    }

    // endregion

    // region: singleOrDefault(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void singleOrDefault2_nullSource_throwsException()
    {
        Linq.singleOrDefault(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void singleOrDefault2_nullPredicate_throwsException()
    {
        Linq.singleOrDefault(new ArrayList<Object>(), null);
    }

    @Test
    public void singleOrDefault2_emptyList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        Integer expected = null;

        // Act
        Integer actual = Linq.singleOrDefault(source, item -> true);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void singleOrDefault2_noElementsMatch_returnsNul()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = null;

        // Act
        Integer actual = Linq.singleOrDefault(source, item -> item > 3);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void singleOrDefault2_oneElementMatches_returnsSingleMatch()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer expected = 2;

        // Act
        Integer actual = Linq.singleOrDefault(source, item -> item % 2 == 0);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void singleOrDefault2_manyElementsMatch_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        Linq.singleOrDefault(source, item -> item % 2 == 0);
    }

    // endregion
}
