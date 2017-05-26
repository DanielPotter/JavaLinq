package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class MinTests
{
    // region: minDouble(Iterable<Double>)

    @Test(expected = IllegalArgumentException.class)
    public void minDouble1_nullSource_throwsException()
    {
        Linq.minDouble(null);
    }

    @Test(expected = IllegalStateException.class)
    public void minDouble1_emptyList_throwsException()
    {
        Linq.minDouble(new ArrayList<Double>());
    }

    @Test
    public void minDouble1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(2d);
        source.add(1d);
        source.add(3d);

        double expected = 1;

        // Act
        double actual = Linq.minDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minDouble(Iterable<TSource>, Function<TSource, Double>)

    @Test(expected = IllegalArgumentException.class)
    public void minDouble2_nullSource_throwsException()
    {
        Linq.minDouble(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minDouble2_nullSelector_throwsException()
    {
        Linq.minDouble(new ArrayList<Double>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void minDouble2_emptyList_throwsException()
    {
        Linq.minDouble(new ArrayList<Double>(), item -> null);
    }

    @Test
    public void minDouble2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("2");
        source.add("1");
        source.add("3");

        double expected = 1;

        // Act
        double actual = Linq.minDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minFloat(Iterable<Float>)

    @Test(expected = IllegalArgumentException.class)
    public void minFloat1_nullSource_throwsException()
    {
        Linq.minFloat(null);
    }

    @Test(expected = IllegalStateException.class)
    public void minFloat1_emptyList_throwsException()
    {
        Linq.minFloat(new ArrayList<Float>());
    }

    @Test
    public void minFloat1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(2f);
        source.add(1f);
        source.add(3f);

        float expected = 1;

        // Act
        float actual = Linq.minFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minFloat(Iterable<TSource>, Function<TSource, Float>)

    @Test(expected = IllegalArgumentException.class)
    public void minFloat2_nullSource_throwsException()
    {
        Linq.minFloat(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minFloat2_nullSelector_throwsException()
    {
        Linq.minFloat(new ArrayList<Float>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void minFloat2_emptyList_throwsException()
    {
        Linq.minFloat(new ArrayList<Float>(), item -> null);
    }

    @Test
    public void minFloat2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("2");
        source.add("1");
        source.add("3");

        float expected = 1;

        // Act
        float actual = Linq.minFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minInteger(Iterable<Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void minInteger1_nullSource_throwsException()
    {
        Linq.minInteger(null);
    }

    @Test(expected = IllegalStateException.class)
    public void minInteger1_emptyList_throwsException()
    {
        Linq.minInteger(new ArrayList<Integer>());
    }

    @Test
    public void minInteger1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(2);
        source.add(1);
        source.add(3);

        int expected = 1;

        // Act
        int actual = Linq.minInteger(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minInteger(Iterable<TSource>, Function<TSource, Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void minInteger2_nullSource_throwsException()
    {
        Linq.minInteger(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minInteger2_nullSelector_throwsException()
    {
        Linq.minInteger(new ArrayList<Integer>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void minInteger2_emptyList_throwsException()
    {
        Linq.minInteger(new ArrayList<Integer>(), item -> null);
    }

    @Test
    public void minInteger2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("2");
        source.add("1");
        source.add("3");

        int expected = 1;

        // Act
        int actual = Linq.minInteger(source, item -> Integer.parseInt(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minLong(Iterable<Long>)

    @Test(expected = IllegalArgumentException.class)
    public void minLong1_nullSource_throwsException()
    {
        Linq.minLong(null);
    }

    @Test(expected = IllegalStateException.class)
    public void minLong1_emptyList_throwsException()
    {
        Linq.minLong(new ArrayList<Long>());
    }

    @Test
    public void minLong1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Long> source = new ArrayList<>();
        source.add(2l);
        source.add(1l);
        source.add(3l);

        long expected = 1;

        // Act
        long actual = Linq.minLong(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: minLong(Iterable<TSource>, Function<TSource, Long>)

    @Test(expected = IllegalArgumentException.class)
    public void minLong2_nullSource_throwsException()
    {
        Linq.minLong(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minLong2_nullSelector_throwsException()
    {
        Linq.minLong(new ArrayList<Long>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void minLong2_emptyList_throwsException()
    {
        Linq.minLong(new ArrayList<Long>(), item -> null);
    }

    @Test
    public void minLong2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("2");
        source.add("1");
        source.add("3");

        long expected = 1;

        // Act
        long actual = Linq.minLong(source, item -> Long.parseLong(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion
}
