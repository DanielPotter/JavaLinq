package potter.linq.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static potter.linq.tests.CollectionAssert.assertMapEquals;
import static potter.linq.tests.CollectionAssert.assertSequenceEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class ToCollectionTests
{
    // region: toArray(Iterable<?>)

    @Test(expected = IllegalArgumentException.class)
    public void toArray1_nullSource_throwsException()
    {
        Linq.toArray(null);
    }

    @Test
    public void toArray1_emptyList_returnsEmptyArray()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        Object[] expectedArray = new Object[0];

        // Act
        Object[] actualArray = Linq.toArray(source);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void toArray1_listOfElements_returnsArrayOfElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        Object[] expectedArray =
        {
            1, 2, 3,
        };

        // Act
        Object[] actualArray = Linq.toArray(source);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    // endregion

    // region: toArray(Iterable<TSource>, TSource[])

    @Test(expected = IllegalArgumentException.class)
    public void toArray2_nullSource_throwsException()
    {
        Linq.toArray(null, new Object[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toArray2_nullArray_throwsException()
    {
        Linq.toArray(new ArrayList<Object>(), (Object[]) null);
    }

    @Test
    public void toArray2_emptyList_returnsEmptyArray()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        Integer[] expectedArray = new Integer[0];

        // Act
        Integer[] actualArray = Linq.toArray(source, new Integer[0]);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void toArray2_listOfElements_returnsArrayOfElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer[] expectedArray =
        {
            1, 2, 3,
        };

        // Act
        Integer[] actualArray = Linq.toArray(source, new Integer[3]);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    // endregion

    // region: toArray(Iterable<TSource>, Function<Integer, TSource[]>)

    @Test(expected = IllegalArgumentException.class)
    public void toArray3_nullSource_throwsException()
    {
        Linq.toArray(null, size -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toArray3_nullSelector_throwsException()
    {
        Linq.toArray(new ArrayList<Object>(), (Function<Integer, Object[]>) null);
    }

    @Test
    public void toArray3_emptyList_returnsEmptyArray()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        Integer[] expectedArray = new Integer[0];

        // Act
        Integer[] actualArray = Linq.toArray(source, size -> new Integer[size]);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    @Test
    public void toArray3_listOfElements_returnsArrayOfElements()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);

        Integer[] expectedArray =
        {
            1, 2, 3,
        };

        // Act
        Integer[] actualArray = Linq.toArray(source, size -> new Integer[size]);

        // Assert
        Assert.assertArrayEquals(expectedArray, actualArray);
    }

    // endregion

    // region: toArrayList(Iterable<TSource>)

    @Test(expected = IllegalArgumentException.class)
    public void toArrayList1_nullSource_throwsException()
    {
        Linq.toArrayList(null);
    }

    @Test
    public void toArrayList1_listOfElements_newListCreated()
    {
        // Arrange
        ArrayList<Integer> expectedList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(3);

        // Act
        ArrayList<Integer> actualList = Linq.toArrayList(expectedList);

        // Assert
        assertNotNull(actualList);
        assertNotSame(expectedList, actualList);
        assertSequenceEquals(expectedList, actualList);
    }

    // endregion

    // region: toDictionary(Iterable<TSource>, Function<TSource, TKey>)

    @Test(expected = IllegalArgumentException.class)
    public void toHashMap2_nullSource_throwsException()
    {
        Linq.toHashMap(null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toHashMap2_nullKeySelector_throwsException()
    {
        Linq.toHashMap(new ArrayList<Object>(), null);
    }

    @Test
    public void toHashMap2_listOfElements_newMapCreated()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(32);
        elements.add(33);
        elements.add(34);

        HashMap<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put((char) 32, 32);
        expectedMap.put((char) 33, 33);
        expectedMap.put((char) 34, 34);

        // Act
        HashMap<Character, Integer> actualMap = Linq.toHashMap(elements, item -> (char) (int) item);

        // Assert
        assertNotNull(actualMap);
        assertNotSame(expectedMap, actualMap);
        assertMapEquals(expectedMap, actualMap);
    }

    // endregion

    // region: toDictionary(Iterable<TSource>, Function<TSource, TKey>)

    @Test(expected = IllegalArgumentException.class)
    public void toHashMap3_nullSource_throwsException()
    {
        Linq.toHashMap(null, item -> item, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toHashMap3_nullKeySelector_throwsException()
    {
        Linq.toHashMap(new ArrayList<Object>(), null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toHashMap3_nullElementSelector_throwsException()
    {
        Linq.toHashMap(new ArrayList<Object>(), item -> item, null);
    }

    @Test
    public void toHashMap3_listOfElements_newMapCreated()
    {
        // Arrange
        ArrayList<Integer> elements = new ArrayList<>();
        elements.add(32);
        elements.add(33);
        elements.add(34);

        HashMap<Integer, Character> expectedMap = new HashMap<>();
        expectedMap.put(32, (char) 32);
        expectedMap.put(33, (char) 33);
        expectedMap.put(34, (char) 34);

        // Act
        HashMap<Integer, Character> actualMap = Linq.toHashMap(elements, item -> item, item -> (char) (int) item);

        // Assert
        assertNotNull(actualMap);
        assertNotSame(expectedMap, actualMap);
        assertMapEquals(expectedMap, actualMap);
    }

    // endregion
}
