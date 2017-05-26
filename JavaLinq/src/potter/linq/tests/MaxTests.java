package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class MaxTests
{
    // region: maxDouble(Iterable<Double>)

    @Test(expected = IllegalArgumentException.class)
    public void maxDouble1_nullSource_throwsException()
    {
        Linq.maxDouble(null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxDouble1_emptyList_throwsException()
    {
        Linq.maxDouble(new ArrayList<Double>());
    }

    @Test
    public void maxDouble1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(1d);
        source.add(3d);
        source.add(2d);

        double expected = 3;

        // Act
        double actual = Linq.maxDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxDouble(Iterable<TSource>, Function<TSource, Double>)

    @Test(expected = IllegalArgumentException.class)
    public void maxDouble2_nullSource_throwsException()
    {
        Linq.maxDouble(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxDouble2_nullSelector_throwsException()
    {
        Linq.maxDouble(new ArrayList<Double>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxDouble2_emptyList_throwsException()
    {
        Linq.maxDouble(new ArrayList<Double>(), item -> null);
    }

    @Test
    public void maxDouble2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("3");
        source.add("2");

        double expected = 3;

        // Act
        double actual = Linq.maxDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxFloat(Iterable<Float>)

    @Test(expected = IllegalArgumentException.class)
    public void maxFloat1_nullSource_throwsException()
    {
        Linq.maxFloat(null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxFloat1_emptyList_throwsException()
    {
        Linq.maxFloat(new ArrayList<Float>());
    }

    @Test
    public void maxFloat1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(1f);
        source.add(3f);
        source.add(2f);

        float expected = 3;

        // Act
        float actual = Linq.maxFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxFloat(Iterable<TSource>, Function<TSource, Float>)

    @Test(expected = IllegalArgumentException.class)
    public void maxFloat2_nullSource_throwsException()
    {
        Linq.maxFloat(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxFloat2_nullSelector_throwsException()
    {
        Linq.maxFloat(new ArrayList<Float>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxFloat2_emptyList_throwsException()
    {
        Linq.maxFloat(new ArrayList<Float>(), item -> null);
    }

    @Test
    public void maxFloat2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("3");
        source.add("2");

        float expected = 3;

        // Act
        float actual = Linq.maxFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxInteger(Iterable<Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void maxInteger1_nullSource_throwsException()
    {
        Linq.maxInteger(null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxInteger1_emptyList_throwsException()
    {
        Linq.maxInteger(new ArrayList<Integer>());
    }

    @Test
    public void maxInteger1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(3);
        source.add(2);

        int expected = 3;

        // Act
        int actual = Linq.maxInteger(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxInteger(Iterable<TSource>, Function<TSource, Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void maxInteger2_nullSource_throwsException()
    {
        Linq.maxInteger(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxInteger2_nullSelector_throwsException()
    {
        Linq.maxInteger(new ArrayList<Integer>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxInteger2_emptyList_throwsException()
    {
        Linq.maxInteger(new ArrayList<Integer>(), item -> null);
    }

    @Test
    public void maxInteger2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("3");
        source.add("2");

        int expected = 3;

        // Act
        int actual = Linq.maxInteger(source, item -> Integer.parseInt(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxLong(Iterable<Long>)

    @Test(expected = IllegalArgumentException.class)
    public void maxLong1_nullSource_throwsException()
    {
        Linq.maxLong(null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxLong1_emptyList_throwsException()
    {
        Linq.maxLong(new ArrayList<Long>());
    }

    @Test
    public void maxLong1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Long> source = new ArrayList<>();
        source.add(1l);
        source.add(3l);
        source.add(2l);

        long expected = 3;

        // Act
        long actual = Linq.maxLong(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: maxLong(Iterable<TSource>, Function<TSource, Long>)

    @Test(expected = IllegalArgumentException.class)
    public void maxLong2_nullSource_throwsException()
    {
        Linq.maxLong(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxLong2_nullSelector_throwsException()
    {
        Linq.maxLong(new ArrayList<Long>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void maxLong2_emptyList_throwsException()
    {
        Linq.maxLong(new ArrayList<Long>(), item -> null);
    }

    @Test
    public void maxLong2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("3");
        source.add("2");

        long expected = 3;

        // Act
        long actual = Linq.maxLong(source, item -> Long.parseLong(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion
}
