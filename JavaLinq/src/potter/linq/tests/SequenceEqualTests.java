package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class SequenceEqualTests
{
    // region: sequenceEqual(Iterable<TSource>, Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void sequenceEqual1_nullFirst_throwsException()
    {
        Linq.sequenceEqual(null, new ArrayList<Object>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sequenceEqual1_nullSecond_throwsException()
    {
        Linq.sequenceEqual(new ArrayList<Object>(), null);
    }

    @Test
    public void sequenceEqual1_equalLists_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);

        ArrayList<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(2);
        second.add(3);

        // Act
        boolean actual = Linq.sequenceEqual(first, second);

        // Assert
        Assert.assertTrue(actual);
    }

    @Test
    public void sequenceEqual1_unequalLists_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);

        ArrayList<Integer> second = new ArrayList<>();
        second.add(3);
        second.add(2);
        second.add(1);

        // Act
        boolean actual = Linq.sequenceEqual(first, second);

        // Assert
        Assert.assertFalse(actual);
    }

    // endregion
}
