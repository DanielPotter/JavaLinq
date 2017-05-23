package potter.linq.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static potter.linq.tests.CollectionAssert.assertMapEquals;
import static potter.linq.tests.CollectionAssert.assertSequenceEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import potter.linq.Linq;

public class ToCollectionTests
{
    // region: toArray

    @Test(expected = IllegalArgumentException.class)
    public void toArray_nullSource_throwsException()
    {
        Linq.toArrayList(null);
    }

    @Test
    public void toArray_listOfElements_newListCreated()
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
    public void toDictionary2_nullSource_throwsException()
    {
        Linq.toDictionary(null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toDictionary2_nullKeySelector_throwsException()
    {
        Linq.toDictionary(new ArrayList<Object>(), null);
    }

    @Test
    public void toDictionary2_listOfElements_newMapCreated()
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
        HashMap<Character, Integer> actualMap = Linq.toDictionary(elements, item -> (char) (int) item);

        // Assert
        assertNotNull(actualMap);
        assertNotSame(expectedMap, actualMap);
        assertMapEquals(expectedMap, actualMap);
    }

    // endregion

    // region: toDictionary(Iterable<TSource>, Function<TSource, TKey>)

    @Test(expected = IllegalArgumentException.class)
    public void toDictionary3_nullSource_throwsException()
    {
        Linq.toDictionary(null, item -> item, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toDictionary3_nullKeySelector_throwsException()
    {
        Linq.toDictionary(new ArrayList<Object>(), null, item -> item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toDictionary3_nullElementSelector_throwsException()
    {
        Linq.toDictionary(new ArrayList<Object>(), item -> item, null);
    }

    @Test
    public void toDictionary3_listOfElements_newMapCreated()
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
        HashMap<Integer, Character> actualMap = Linq.toDictionary(elements, item -> item, item -> (char) (int) item);

        // Assert
        assertNotNull(actualMap);
        assertNotSame(expectedMap, actualMap);
        assertMapEquals(expectedMap, actualMap);
    }

    // endregion
}
