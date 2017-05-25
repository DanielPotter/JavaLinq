package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.Linq;

public class ContainsTests
{
    // region: contains(Iterable<TSource>, TSource)

    @Test(expected = IllegalArgumentException.class)
    public void contains1_nullSource_throwsException()
    {
        Linq.contains(null, new Object());
    }

    @Test
    public void contains1_elementExists_elementFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 3);

        // Assert
        Assert.assertTrue(found);
    }

    @Test
    public void contains1_elementDoesNotExists_elementNotFound()
    {
        // Arrange
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        boolean found = Linq.contains(source, 6);

        // Assert
        Assert.assertFalse(found);
    }

    @Test
    public void contains1_listOfElements_listNotIterated()
    {
        // Arrange
        Reference<Boolean> containsCalled = new Reference<>(false);

        ArrayList<Integer> source = new ArrayList<Integer>()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean contains(Object o)
            {
                containsCalled.value = true;
                return super.contains(o);
            }

            @Override
            public Iterator<Integer> iterator()
            {
                Assert.fail("Lists should not be iterated.");
                return super.iterator();
            }
        };
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);

        // Act
        Linq.contains(source, 3);

        // Assert
        Assert.assertTrue(containsCalled.value);
    }

    // endregion
}
