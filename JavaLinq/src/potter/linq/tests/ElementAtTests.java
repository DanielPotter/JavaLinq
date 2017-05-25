package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class ElementAtTests
{
    // region: elementAt(Iterable<TSource>, int)

    @Test(expected = IllegalArgumentException.class)
    public void elementAt1_nullSource_throwsException()
    {
        Linq.elementAt(null, 0);
    }

    @Test
    public void elementAt1_indexExistsInList_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        int index = 3;
        Integer expected = 4;

        // Act
        Integer actual = Linq.elementAt(source, index);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void elementAt1_indexDoesNotExistInList_throwsException()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();

        // Act
        Linq.elementAt(source, 0);
    }

    @Test
    public void elementAt1_indexExistsInSequence_elementFound()
    {
        // Arrange
        ArrayList<Integer> sourceList = new ArrayList<>();
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);
        sourceList.add(4);
        sourceList.add(5);
        Iterable<Integer> source = mockIterable(sourceList);

        int index = 3;
        Integer expected = 4;

        // Act
        Integer actual = Linq.elementAt(source, index);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void elementAt1_indexDoesNotExistInSequence_throwsException()
    {
        // Arrange
        ArrayList<Integer> sourceList = new ArrayList<>();
        Iterable<Integer> source = mockIterable(sourceList);

        // Act
        Linq.elementAt(source, 0);
    }

    // endregion

    // region: elementAtOrDefault(Iterable<TSource>, int)

    @Test(expected = IllegalArgumentException.class)
    public void elementAtOrDefault1_nullSource_throwsException()
    {
        Linq.elementAtOrDefault(null, 0);
    }

    @Test
    public void elementAtOrDefault1_indexExistsInList_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        int index = 3;
        Integer expected = 4;

        // Act
        Integer actual = Linq.elementAtOrDefault(source, index);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void elementAtOrDefault1_indexDoesNotExistInList_returnsNull()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();

        // Act
        Integer actual = Linq.elementAtOrDefault(source, 0);

        // Assert
        Assert.assertNull(actual);
    }

    @Test
    public void elementAtOrDefault1_indexExistsInSequence_elementFound()
    {
        // Arrange
        ArrayList<Integer> sourceList = new ArrayList<>();
        sourceList.add(1);
        sourceList.add(2);
        sourceList.add(3);
        sourceList.add(4);
        sourceList.add(5);
        Iterable<Integer> source = mockIterable(sourceList);

        int index = 3;
        Integer expected = 4;

        // Act
        Integer actual = Linq.elementAtOrDefault(source, index);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void elementAtOrDefault1_indexDoesNotExistInSequence_returnsNull()
    {
        // Arrange
        ArrayList<Integer> sourceList = new ArrayList<>();
        Iterable<Integer> source = mockIterable(sourceList);

        // Act
        Integer actual = Linq.elementAtOrDefault(source, 0);

        // Assert
        Assert.assertNull(actual);
    }

    // endregion

    // region: Helper Methods

    private static <T> Iterable<T> mockIterable(Iterable<T> source)
    {
        return new Iterable<T>()
        {
            @Override
            public Iterator<T> iterator()
            {
                return new Iterator<T>()
                {
                    private Iterator<T> sourceIterator = source.iterator();

                    @Override
                    public boolean hasNext()
                    {
                        return sourceIterator.hasNext();
                    }

                    @Override
                    public T next()
                    {
                        return sourceIterator.next();
                    }
                };
            }

        };
    }

    // endregion
}
