package potter.linq.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Test;

import potter.linq.Linq;

public class SelectTests
{
    // region: select(Iterable<TSource>, Function<TSource, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void select1_nullSource_throwsException()
    {
        Linq.select(null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void select1_nullSelector_throwsException()
    {
        Linq.select(new ArrayList<Object>(), (Function<Object, String>) null);
    }

    @Test
    public void select1_listOfElements_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, item -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void select1_iterateTwice_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, item -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // region: select(Iterable<TSource>, BiFunction<TSource, Integer, TResult>)

    @Test(expected = IllegalArgumentException.class)
    public void select2_nullSource_throwsException()
    {
        Linq.select(null, (item, index) -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void select2_nullSelector_throwsException()
    {
        Linq.select(new ArrayList<Object>(), (BiFunction<Object, Integer, String>) null);
    }

    @Test
    public void select2_listOfElements_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, (item, index) -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void select2_iterateTwice_selectsElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        // Act
        Iterable<String> actualElements = Linq.select(source, (item, index) -> item.toString());

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void select2_listOfElements_indexIncrementsByOne()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1");
        expectedElements.add("2");
        expectedElements.add("3");

        Reference<Integer> count = new Reference<>(0);

        // Act
        Iterable<String> actualElements = Linq.select(source, (item, index) ->
        {
            assertEquals(count.value++, index);
            return item.toString();
        });

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: selectManyMany(Iterable<TSource>, Function<TSource, Iterable<TResult>>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void selectMany1_nullSource_throwsException()
    {
        Linq.selectMany((List<List<Object>>) null, list -> list);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectMany1_nullSelector_throwsException()
    {
        Linq.selectMany(new ArrayList<List<Object>>(), (Function<List<Object>, Iterable<Object>>) null);
    }

    @Test
    public void selectMany1_listOfElements_selectManysElements()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(source2);
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, list -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void selectMany1_iterateTwice_selectManysElements()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(source2);
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, list -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void selectMany1_listWithEmptyLists_noExceptionsThrown()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(new ArrayList<>());
        source.add(source2);
        source.add(new ArrayList<>());
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, list -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    // @formatter:off
    // region: selectManyMany(Iterable<TSource>, BiFunction<TSource, Integer, Iterable<TResult>>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void selectMany2_nullSource_throwsException()
    {
        Linq.selectMany((List<List<Object>>) null, (item, index) -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectMany2_nullSelector_throwsException()
    {
        Linq.selectMany(new ArrayList<List<Object>>(), (BiFunction<List<Object>, Integer, Iterable<Object>>) null);
    }

    @Test
    public void selectMany2_listOfElements_selectManysElements()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(source2);
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, (list, index) -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void selectMany2_iterateTwice_selectManysElements()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(source2);
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, (list, index) -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void selectMany2_listWithEmptyLists_noExceptionsThrown()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(new ArrayList<>());
        source.add(source2);
        source.add(new ArrayList<>());
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, (list, index) -> list);

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void selectMany2_listOfElements_indexIncrementsByOne()
    {
        // Arrange
        ArrayList<String> source1 = new ArrayList<>();
        source1.add("1a");
        source1.add("1b");
        source1.add("1c");
        ArrayList<String> source2 = new ArrayList<>();
        source2.add("2a");
        source2.add("2b");
        source2.add("2c");
        ArrayList<String> source3 = new ArrayList<>();
        source3.add("3a");
        source3.add("3b");
        source3.add("3c");
        ArrayList<List<String>> source = new ArrayList<>();
        source.add(source1);
        source.add(source2);
        source.add(source3);

        ArrayList<String> expectedElements = new ArrayList<>();
        expectedElements.add("1a");
        expectedElements.add("1b");
        expectedElements.add("1c");
        expectedElements.add("2a");
        expectedElements.add("2b");
        expectedElements.add("2c");
        expectedElements.add("3a");
        expectedElements.add("3b");
        expectedElements.add("3c");

        Reference<Integer> count = new Reference<>(0);

        // Act
        Iterable<String> actualElements = Linq.selectMany(source, (list, index) ->
        {
            assertEquals(count.value++, index);
            return list;
        });

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion
}
