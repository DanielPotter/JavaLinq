package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class AverageTests
{
    public final double MaxDoubleEqualityDelta = 0.000_000_000_001d;
    public final float MaxFloatEqualityDelta = 0.000_001f;

    // region: averageDouble(Iterable<Double>)

    @Test(expected = IllegalArgumentException.class)
    public void averageDouble1_nullSource_throwsException()
    {
        Linq.averageDouble(null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageDouble1_emptyList_throwsException()
    {
        Linq.averageDouble(new ArrayList<Double>());
    }

    @Test
    public void averageDouble1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(1d);
        source.add(2d);
        source.add(3d);

        double expected = 2;

        // Act
        double actual = Linq.averageDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void averageDouble1_listOfNumbers_returnsPointAverage()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(1.1d);
        source.add(2.1d);
        source.add(3.1d);

        double expected = 2.1;

        // Act
        double actual = Linq.averageDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, MaxDoubleEqualityDelta);
    }

    // endregion

    // region: averageDouble(Iterable<TSource>, Function<TSource, Double>)

    @Test(expected = IllegalArgumentException.class)
    public void averageDouble2_nullSource_throwsException()
    {
        Linq.averageDouble(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageDouble2_nullSelector_throwsException()
    {
        Linq.averageDouble(new ArrayList<Double>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageDouble2_emptyList_throwsException()
    {
        Linq.averageDouble(new ArrayList<Double>(), item -> null);
    }

    @Test
    public void averageDouble2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        double expected = 2;

        // Act
        double actual = Linq.averageDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void averageDouble2_listOfNumberStrings_returnsPointAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1.1");
        source.add("2.1");
        source.add("3.1");

        double expected = 2.1;

        // Act
        double actual = Linq.averageDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, MaxDoubleEqualityDelta);
    }

    // endregion

    // region: averageFloat(Iterable<Float>)

    @Test(expected = IllegalArgumentException.class)
    public void averageFloat1_nullSource_throwsException()
    {
        Linq.averageFloat(null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageFloat1_emptyList_throwsException()
    {
        Linq.averageFloat(new ArrayList<Float>());
    }

    @Test
    public void averageFloat1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(1f);
        source.add(2f);
        source.add(3f);

        float expected = 2;

        // Act
        float actual = Linq.averageFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void averageFloat1_listOfNumbers_returnsPointAverage()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(1.1f);
        source.add(2.1f);
        source.add(3.1f);

        float expected = 2.1f;

        // Act
        float actual = Linq.averageFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, MaxFloatEqualityDelta);
    }

    // endregion

    // region: averageFloat(Iterable<TSource>, Function<TSource, Float>)

    @Test(expected = IllegalArgumentException.class)
    public void averageFloat2_nullSource_throwsException()
    {
        Linq.averageFloat(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageFloat2_nullSelector_throwsException()
    {
        Linq.averageFloat(new ArrayList<Float>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageFloat2_emptyList_throwsException()
    {
        Linq.averageFloat(new ArrayList<Float>(), item -> null);
    }

    @Test
    public void averageFloat2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        float expected = 2;

        // Act
        float actual = Linq.averageFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void averageFloat2_listOfNumberStrings_returnsPointAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1.1");
        source.add("2.1");
        source.add("3.1");

        float expected = 2.1f;

        // Act
        float actual = Linq.averageFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, MaxFloatEqualityDelta);
    }

    // endregion

    // region: averageInteger(Iterable<Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void averageInteger1_nullSource_throwsException()
    {
        Linq.averageInteger(null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageInteger1_emptyList_throwsException()
    {
        Linq.averageInteger(new ArrayList<Integer>());
    }

    @Test
    public void averageInteger1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        int expected = 2;

        // Act
        int actual = Linq.averageInteger(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: averageInteger(Iterable<TSource>, Function<TSource, Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void averageInteger2_nullSource_throwsException()
    {
        Linq.averageInteger(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageInteger2_nullSelector_throwsException()
    {
        Linq.averageInteger(new ArrayList<Integer>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageInteger2_emptyList_throwsException()
    {
        Linq.averageInteger(new ArrayList<Integer>(), item -> null);
    }

    @Test
    public void averageInteger2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        int expected = 2;

        // Act
        int actual = Linq.averageInteger(source, item -> Integer.parseInt(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: averageLong(Iterable<Long>)

    @Test(expected = IllegalArgumentException.class)
    public void averageLong1_nullSource_throwsException()
    {
        Linq.averageLong(null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageLong1_emptyList_throwsException()
    {
        Linq.averageLong(new ArrayList<Long>());
    }

    @Test
    public void averageLong1_listOfNumbers_returnsAverage()
    {
        // Arrange
        ArrayList<Long> source = new ArrayList<>();
        source.add(1l);
        source.add(2l);
        source.add(3l);

        long expected = 2;

        // Act
        long actual = Linq.averageLong(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: averageLong(Iterable<TSource>, Function<TSource, Long>)

    @Test(expected = IllegalArgumentException.class)
    public void averageLong2_nullSource_throwsException()
    {
        Linq.averageLong(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void averageLong2_nullSelector_throwsException()
    {
        Linq.averageLong(new ArrayList<Long>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void averageLong2_emptyList_throwsException()
    {
        Linq.averageLong(new ArrayList<Long>(), item -> null);
    }

    @Test
    public void averageLong2_listOfNumberStrings_returnsAverage()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        long expected = 2;

        // Act
        long actual = Linq.averageLong(source, item -> Long.parseLong(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion
}
