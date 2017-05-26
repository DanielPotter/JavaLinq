package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class AggregateTests
{
    // @formatter:off
    // region: aggregate(Iterable<TSource>, BiFunction<TSource, TSource, TSource>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void aggregate1_nullSource_throwsException()
    {
        Linq.aggregate(null, (left, right) -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aggregate1_nullFunction_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void aggregate1_emptyList_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), (left, right) -> null);
    }

    @Test
    public void aggregate1_listOfElements_elementsCombined()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        Integer expected = 120;

        // Act
        Integer actual = Linq.aggregate(source, (left, right) -> left * right);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: aggregate(Iterable<TSource>, TAccumulate, BiFunction<TAccumulate, TSource, TAccumulate>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void aggregate2_nullSource_throwsException()
    {
        Linq.aggregate(null, null, (left, right) -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aggregate2_nullFunction_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void aggregate2_emptyList_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null, (left, right) -> null);
    }

    @Test
    public void aggregate2_listOfElements_elementsCombined()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        Integer seed = 5;
        Integer expected = 120;

        // Act
        Integer actual = Linq.aggregate(source, seed, (left, right) -> left * right);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: aggregate(Iterable<TSource>, TAccumulate, BiFunction<TAccumulate, TSource, TAccumulate>, Function<TAccumulate, TResult>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void aggregate3_nullSource_throwsException()
    {
        Linq.aggregate(null, null, (left, right) -> null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aggregate3_nullFunction_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null, null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void aggregate3_nullResultSelector_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null, (left, right) -> null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void aggregate3_emptyList_throwsException()
    {
        Linq.aggregate(new ArrayList<Object>(), null, (left, right) -> null, item -> null);
    }

    @Test
    public void aggregate3_listOfElements_elementsCombined()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        Integer seed = 5;
        String expected = "120";

        // Act
        String actual = Linq.aggregate(source, seed, (left, right) -> left * right, number -> number.toString());

        // Assert
        Assert.assertEquals(expected, actual);
    }

    // endregion
}
