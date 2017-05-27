package potter.linq.tests;

import java.util.ArrayList;

import org.junit.Test;

import potter.linq.Linq;

public class ZipTests
{
    // @formatter:off
    // region: zip(Iterable<TFirst> first, Iterable<TSecond> second, BiFunction<TFirst, TSecond, TResult> resultSelector)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void zip1_nullFirst_throwsException()
    {
        Linq.zip(null, new ArrayList<Object>(), (first, second) -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zip1_nullSecond_throwsException()
    {
        Linq.zip(new ArrayList<Object>(), null, (first, second) -> null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zip1_nullResultSelector_throwsException()
    {
        Linq.zip(new ArrayList<Object>(), new ArrayList<Object>(), null);
    }

    @Test
    public void zip1_listsOfElements_elementsZipped()
    {
        // Arrange
        ArrayList<String> first = new ArrayList<>();
        first.add("a");
        first.add("b");
        first.add("c");

        ArrayList<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(2);
        second.add(3);

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple("a", 1));
        expectedElements.add(new Tuple("b", 2));
        expectedElements.add(new Tuple("c", 3));

        // Act
        Iterable<Tuple> actualElements = Linq.zip(first, second, (name, value) -> new Tuple(name, value));

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    @Test
    public void zip1_iterateTwice_elementsZipped()
    {
        // Arrange
        ArrayList<String> first = new ArrayList<>();
        first.add("a");
        first.add("b");
        first.add("c");

        ArrayList<Integer> second = new ArrayList<>();
        second.add(1);
        second.add(2);
        second.add(3);

        ArrayList<Tuple> expectedElements = new ArrayList<>();
        expectedElements.add(new Tuple("a", 1));
        expectedElements.add(new Tuple("b", 2));
        expectedElements.add(new Tuple("c", 3));

        // Act
        Iterable<Tuple> actualElements = Linq.zip(first, second, (name, value) -> new Tuple(name, value));

        // Assert
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
        CollectionAssert.assertSequenceEquals(expectedElements, actualElements);
    }

    // endregion

    private static class Tuple
    {
        public Tuple(String name, Integer value)
        {
            this.name = name;
            this.value = value;
        }

        public String name;
        public Integer value;

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }

            if (obj instanceof Tuple == false)
            {
                return false;
            }

            Tuple other = (Tuple) obj;

            if (name == null)
            {
                if (other.name != null)
                {
                    return false;
                }
            }
            else if (name.equals(other.name) == false)
            {
                return false;
            }

            if (value == null)
            {
                if (other.value != null)
                {
                    return false;
                }
            }
            else if (value.equals(other.value) == false)
            {
                return false;
            }

            return true;
        }
    }
}
