package potter.linq.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class AnyAndAllTests
{
    // region: all(Iterable<TSource>, Function<TSource, Boolean>)

    @Test(expected = IllegalArgumentException.class)
    public void all2_nullSource_throwsException()
    {
        Linq.all(null, item -> true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void all2_nullPredicate_throwsException()
    {
        Linq.all(new ArrayList<Object>(), null);
    }

    @Test
    public void all2_emptyList_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();

        // Act
        boolean actual = Linq.all(source, item -> true);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void all2_noElementsMatch_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        boolean actual = Linq.all(source, item -> item > 10);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void all2_someElementsMatch_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        // Act
        boolean actual = Linq.all(source, item -> item % 2 == 0);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void all2_allElementsMatch_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(2);
        source.add(4);
        source.add(6);
        source.add(8);

        // Act
        boolean actual = Linq.all(source, item -> item % 2 == 0);

        // Assert
        assertTrue(actual);
    }

    // endregion

    // region: any(Iterable<TSource>)

    @Test
    public void any1_nullSource_returnsFalse()
    {
        // Act
        boolean actual = Linq.any(null);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void any1_emptyList_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();

        // Act
        boolean actual = Linq.any(source);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void any1_singleElement_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);

        // Act
        boolean actual = Linq.any(source);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void any1_listOfElements_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        boolean actual = Linq.any(source);

        // Assert
        assertTrue(actual);
    }

    // endregion

    // region: any(Iterable<TSource>, Function<TSource, Boolean>)

    @Test
    public void any2_nullSource_returnsFalse()
    {
        // Act
        boolean actual = Linq.any(null, item -> true);

        // Assert
        assertFalse(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void any2_nullPredicate_throwsException()
    {
        Linq.any(new ArrayList<Object>(), null);
    }

    @Test
    public void any2_emptyList_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();

        // Act
        boolean actual = Linq.any(source, item -> true);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void any2_noMatches_returnsFalse()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        // Act
        boolean actual = Linq.any(source, item -> item > 10);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void any2_withMatches_returnsTrue()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        // Act
        boolean actual = Linq.any(source, item -> item % 2 == 0);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void any2_secondElementMatches_onlyTwoElementsExamined()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);

        Reference<Integer> count = new Reference<>(0);

        // Act
        boolean actual = Linq.any(source, item ->
        {
            count.value++;
            return item == 2;
        });

        // Assert
        assertTrue(actual);
        assertEquals((int) count.value, 2);
    }

    // endregion
}
