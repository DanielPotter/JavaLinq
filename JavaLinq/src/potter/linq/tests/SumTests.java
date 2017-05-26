package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class SumTests
{
    public final double MaxDoubleEqualityDelta = 0.000_000_000_001d;
    public final float MaxFloatEqualityDelta = 0.000_001f;

    // region: sumDouble(Iterable<Double>)

    @Test(expected = IllegalArgumentException.class)
    public void sumDouble1_nullSource_throwsException()
    {
        Linq.sumDouble(null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumDouble1_emptyList_throwsException()
    {
        Linq.sumDouble(new ArrayList<Double>());
    }

    @Test
    public void sumDouble1_listOfNumbers_returnsSum()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(1d);
        source.add(2d);
        source.add(3d);

        double expected = 6;

        // Act
        double actual = Linq.sumDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void sumDouble1_listOfNumbers_returnsPointSum()
    {
        // Arrange
        ArrayList<Double> source = new ArrayList<>();
        source.add(1.1d);
        source.add(2.1d);
        source.add(3.1d);

        double expected = 6.3;

        // Act
        double actual = Linq.sumDouble(source);

        // Assert
        Assert.assertEquals(expected, actual, MaxDoubleEqualityDelta);
    }

    // endregion

    // region: sumDouble(Iterable<TSource>, Function<TSource, Double>)

    @Test(expected = IllegalArgumentException.class)
    public void sumDouble2_nullSource_throwsException()
    {
        Linq.sumDouble(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumDouble2_nullSelector_throwsException()
    {
        Linq.sumDouble(new ArrayList<Double>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumDouble2_emptyList_throwsException()
    {
        Linq.sumDouble(new ArrayList<Double>(), item -> null);
    }

    @Test
    public void sumDouble2_listOfNumberStrings_returnsSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        double expected = 6;

        // Act
        double actual = Linq.sumDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void sumDouble2_listOfNumberStrings_returnsPointSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1.1");
        source.add("2.1");
        source.add("3.1");

        double expected = 6.3;

        // Act
        double actual = Linq.sumDouble(source, item -> Double.parseDouble(item));

        // Assert
        Assert.assertEquals(expected, actual, MaxDoubleEqualityDelta);
    }

    // endregion

    // region: sumFloat(Iterable<Float>)

    @Test(expected = IllegalArgumentException.class)
    public void sumFloat1_nullSource_throwsException()
    {
        Linq.sumFloat(null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumFloat1_emptyList_throwsException()
    {
        Linq.sumFloat(new ArrayList<Float>());
    }

    @Test
    public void sumFloat1_listOfNumbers_returnsSum()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(1f);
        source.add(2f);
        source.add(3f);

        float expected = 6;

        // Act
        float actual = Linq.sumFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void sumFloat1_listOfNumbers_returnsPointSum()
    {
        // Arrange
        ArrayList<Float> source = new ArrayList<>();
        source.add(1.1f);
        source.add(2.1f);
        source.add(3.1f);

        float expected = 6.3f;

        // Act
        float actual = Linq.sumFloat(source);

        // Assert
        Assert.assertEquals(expected, actual, MaxFloatEqualityDelta);
    }

    // endregion

    // region: sumFloat(Iterable<TSource>, Function<TSource, Float>)

    @Test(expected = IllegalArgumentException.class)
    public void sumFloat2_nullSource_throwsException()
    {
        Linq.sumFloat(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumFloat2_nullSelector_throwsException()
    {
        Linq.sumFloat(new ArrayList<Float>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumFloat2_emptyList_throwsException()
    {
        Linq.sumFloat(new ArrayList<Float>(), item -> null);
    }

    @Test
    public void sumFloat2_listOfNumberStrings_returnsSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        float expected = 6;

        // Act
        float actual = Linq.sumFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void sumFloat2_listOfNumberStrings_returnsPointSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1.1");
        source.add("2.1");
        source.add("3.1");

        float expected = 6.3f;

        // Act
        float actual = Linq.sumFloat(source, item -> Float.parseFloat(item));

        // Assert
        Assert.assertEquals(expected, actual, MaxFloatEqualityDelta);
    }

    // endregion

    // region: sumInteger(Iterable<Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void sumInteger1_nullSource_throwsException()
    {
        Linq.sumInteger(null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumInteger1_emptyList_throwsException()
    {
        Linq.sumInteger(new ArrayList<Integer>());
    }

    @Test
    public void sumInteger1_listOfNumbers_returnsSum()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        int expected = 6;

        // Act
        int actual = Linq.sumInteger(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: sumInteger(Iterable<TSource>, Function<TSource, Integer>)

    @Test(expected = IllegalArgumentException.class)
    public void sumInteger2_nullSource_throwsException()
    {
        Linq.sumInteger(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumInteger2_nullSelector_throwsException()
    {
        Linq.sumInteger(new ArrayList<Integer>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumInteger2_emptyList_throwsException()
    {
        Linq.sumInteger(new ArrayList<Integer>(), item -> null);
    }

    @Test
    public void sumInteger2_listOfNumberStrings_returnsSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        int expected = 6;

        // Act
        int actual = Linq.sumInteger(source, item -> Integer.parseInt(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: sumLong(Iterable<Long>)

    @Test(expected = IllegalArgumentException.class)
    public void sumLong1_nullSource_throwsException()
    {
        Linq.sumLong(null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumLong1_emptyList_throwsException()
    {
        Linq.sumLong(new ArrayList<Long>());
    }

    @Test
    public void sumLong1_listOfNumbers_returnsSum()
    {
        // Arrange
        ArrayList<Long> source = new ArrayList<>();
        source.add(1l);
        source.add(2l);
        source.add(3l);

        long expected = 6;

        // Act
        long actual = Linq.sumLong(source);

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion

    // region: sumLong(Iterable<TSource>, Function<TSource, Long>)

    @Test(expected = IllegalArgumentException.class)
    public void sumLong2_nullSource_throwsException()
    {
        Linq.sumLong(null, item -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sumLong2_nullSelector_throwsException()
    {
        Linq.sumLong(new ArrayList<Long>(), null);
    }

    @Test(expected = IllegalStateException.class)
    public void sumLong2_emptyList_throwsException()
    {
        Linq.sumLong(new ArrayList<Long>(), item -> null);
    }

    @Test
    public void sumLong2_listOfNumberStrings_returnsSum()
    {
        // Arrange
        ArrayList<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");

        long expected = 6;

        // Act
        long actual = Linq.sumLong(source, item -> Long.parseLong(item));

        // Assert
        Assert.assertEquals(expected, actual, 0);
    }

    // endregion
}
