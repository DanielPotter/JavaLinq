package potter.linq.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Test;

import potter.linq.EqualityComparer;
import potter.linq.IEnumerable;
import potter.linq.IEqualityComparer;
import potter.linq.Linq;

public class JoinAndGroupJoinTests
{
    private static final Iterable<Object> NullIterable = null;
    private static final Function<Object, Object> NullFunction = null;
    private static final BiFunction<Object, Object, Object> NullBiFunction = null;
    private static final BiFunction<Object, IEnumerable<Object>, Object> NullBiFunctionWithSequence = null;
    private static final IEqualityComparer<Object> NullEqualityComparer = null;
    private static final Class<Object> NullClass = null;

    private static final Iterable<Object> DummyIterable = new ArrayList<>();
    private static final Function<Object, Object> DummyFunction = x -> x;
    private static final BiFunction<Object, Object, Object> DummyBiFunction = (x, y) -> x;
    private static final BiFunction<Object, IEnumerable<Object>, Object> DummyBiFunctionWithSequence = (x, y) -> x;
    private static final IEqualityComparer<Object> DummyEqualityComparer = new IEqualityComparer<Object>()
    {
        @Override
        public boolean equals(Object x, Object y)
        {
            return false;
        }

        @Override
        public int hashCode(Object obj)
        {
            return 0;
        }
    };
    private static final Class<Object> DummyClass = Object.class;

    // @formatter:off
    // region: join(Iterable<TOuter>, Iterable<TInner>, Function<TOuter, TKey>, Function<TInner, TKey>, BiFunction<TOuter, TInner, TResult>, Class<TKey>, Class<TInner>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullOuter_throwsException()
    {
        Linq.join(NullIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullInner_throwsException()
    {
        Linq.join(DummyIterable, NullIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullOuterKeySelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            NullFunction, DummyFunction, DummyBiFunction,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullInnerKeySelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, NullFunction, DummyBiFunction,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullResultSelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, NullBiFunction,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullKeyType_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            NullClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join1_nullElementType_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyClass, NullClass);
    }

    @Test
    public void join1_listsOfElements_joined()
    {
        // Reference:
        // https://msdn.microsoft.com/en-us/library/bb534675(v=vs.110).aspx
        // (5/31/2017)

        // Arrange
        Person magnus = new Person("Magnus Hedlund");
        Person terry = new Person("Terry Adams");
        Person charlotte = new Person("Charlotte Weiss");

        Pet barley = new Pet("Barley", terry);
        Pet boots = new Pet("Boots", terry);
        Pet whiskers = new Pet("Whiskers", charlotte);
        Pet daisy = new Pet("Daisy", magnus);

        ArrayList<Person> outer = new ArrayList<>();
        outer.add(magnus);
        outer.add(terry);
        outer.add(charlotte);

        ArrayList<Pet> inner = new ArrayList<>();
        inner.add(barley);
        inner.add(boots);
        inner.add(whiskers);
        inner.add(daisy);

        ArrayList<Tuple2<String, String>> expected = new ArrayList<>();
        expected.add(new Tuple2<>(magnus.name, daisy.name));
        expected.add(new Tuple2<>(terry.name, barley.name));
        expected.add(new Tuple2<>(terry.name, boots.name));
        expected.add(new Tuple2<>(charlotte.name, whiskers.name));

        // Act
        Iterable<Tuple2<String, String>> actual
            = Linq.join(outer, inner,
                person -> person,
                pet -> pet.owner,
                (person, pet) -> new Tuple2<>(person.name, pet.name),
                Person.class, Pet.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: join(Iterable<TOuter>, Iterable<TInner>, Function<TOuter, TKey>, Function<TInner, TKey>, BiFunction<TOuter, TInner, TResult>, IEqualityComparer<TKey>, Class<TKey>, Class<TInner>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullOuter_throwsException()
    {
        Linq.join(NullIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullInner_throwsException()
    {
        Linq.join(DummyIterable, NullIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullOuterKeySelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            NullFunction, DummyFunction, DummyBiFunction,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullInnerKeySelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, NullFunction, DummyBiFunction,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullResultSelector_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, NullBiFunction,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test
    public void join2_nullResultSelector_noExceptions()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            NullEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullKeyType_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyEqualityComparer, NullClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void join2_nullElementType_throwsException()
    {
        Linq.join(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunction,
            DummyEqualityComparer, DummyClass, NullClass);
    }

    @Test
    public void join2_listsOfElements_joined()
    {
        // Arrange
        final String magnusName = "Magnus Hedlund";
        final String terryName = "Terry Adams";
        final String charlotteName = "Charlotte Weiss";

        Pet barley = new Pet("Barley", new Person(terryName));
        Pet boots = new Pet("Boots", new Person(terryName));
        Pet whiskers = new Pet("Whiskers", new Person(charlotteName));
        Pet daisy = new Pet("Daisy", new Person(magnusName));

        ArrayList<Person> outer = new ArrayList<>();
        outer.add(new Person(magnusName));
        outer.add(new Person(terryName));
        outer.add(new Person(charlotteName));

        ArrayList<Pet> inner = new ArrayList<>();
        inner.add(barley);
        inner.add(boots);
        inner.add(whiskers);
        inner.add(daisy);

        ArrayList<Tuple2<String, String>> expected = new ArrayList<>();
        expected.add(new Tuple2<>(magnusName, daisy.name));
        expected.add(new Tuple2<>(terryName, barley.name));
        expected.add(new Tuple2<>(terryName, boots.name));
        expected.add(new Tuple2<>(charlotteName, whiskers.name));

        EqualityComparer<String> stringComparer = EqualityComparer.getDefault(String.class);
        IEqualityComparer<Person> personComparer = new IEqualityComparer<Person>()
        {
            @Override
            public boolean equals(Person x, Person y)
            {
                return stringComparer.equals(x.name, y.name);
            }

            @Override
            public int hashCode(Person obj)
            {
                return stringComparer.hashCode(obj.name);
            }
        };

        // Act
        Iterable<Tuple2<String, String>> actual
            = Linq.join(outer, inner,
                person -> person,
                pet -> pet.owner,
                (person, pet) -> new Tuple2<>(person.name, pet.name),
                personComparer,
                Person.class, Pet.class);

        // Assert
        CollectionAssert.assertSequenceEquals(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupJoin(Iterable<TOuter>, Iterable<TInner>, Function<TOuter, TKey>, Function<TInner, TKey>, BiFunction<TOuter, IEnumerable<TInner>, TResult>, Class<TKey>, Class<TInner>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullOuter_throwsException()
    {
        Linq.groupJoin(NullIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullInner_throwsException()
    {
        Linq.groupJoin(DummyIterable, NullIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullOuterKeySelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            NullFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullInnerKeySelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, NullFunction, DummyBiFunctionWithSequence,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullResultSelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, NullBiFunctionWithSequence,
            DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullKeyType_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            NullClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin1_nullElementType_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyClass, NullClass);
    }

    @Test
    public void groupJoin1_listsOfElements_groupJoined()
    {
        // Reference:
        // https://msdn.microsoft.com/en-us/library/bb534675(v=vs.110).aspx
        // (5/31/2017)

        // Arrange
        Person magnus = new Person("Magnus Hedlund");
        Person terry = new Person("Terry Adams");
        Person charlotte = new Person("Charlotte Weiss");

        Pet barley = new Pet("Barley", terry);
        Pet boots = new Pet("Boots", terry);
        Pet whiskers = new Pet("Whiskers", charlotte);
        Pet daisy = new Pet("Daisy", magnus);

        ArrayList<Person> outer = new ArrayList<>();
        outer.add(magnus);
        outer.add(terry);
        outer.add(charlotte);

        ArrayList<Pet> inner = new ArrayList<>();
        inner.add(barley);
        inner.add(boots);
        inner.add(whiskers);
        inner.add(daisy);

        ArrayList<String> magnusPetNames = new ArrayList<>();
        magnusPetNames.add(daisy.name);

        ArrayList<String> terryPetNames = new ArrayList<>();
        terryPetNames.add(barley.name);
        terryPetNames.add(boots.name);

        ArrayList<String> charlottePetNames = new ArrayList<>();
        charlottePetNames.add(whiskers.name);

        ArrayList<Tuple2<String, Iterable<String>>> expected = new ArrayList<>();
        expected.add(new Tuple2<>(magnus.name, magnusPetNames));
        expected.add(new Tuple2<>(terry.name, terryPetNames));
        expected.add(new Tuple2<>(charlotte.name, charlottePetNames));

        // Act
        Iterable<Tuple2<String, Iterable<String>>> actual
            = Linq.groupJoin(outer, inner,
                person -> person,
                pet -> pet.owner,
                (person, pets) -> new Tuple2<>(person.name, pets.select(pet -> pet.name)),
                Person.class, Pet.class);

        // Assert
        assertOwnerAndPetsNamesSequencesAreEqual(expected, actual);
    }

    // endregion

    // @formatter:off
    // region: groupJoin(Iterable<TOuter>, Iterable<TInner>, Function<TOuter, TKey>, Function<TInner, TKey>, BiFunction<TOuter, IEnumerable<TInner>, TResult>, IEqualityComparer<TKey>, Class<TKey>, Class<TInner>)
    // @formatter:on

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullOuter_throwsException()
    {
        Linq.groupJoin(NullIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullInner_throwsException()
    {
        Linq.groupJoin(DummyIterable, NullIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullOuterKeySelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            NullFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullInnerKeySelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, NullFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullResultSelector_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, NullBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, DummyClass);
    }

    @Test
    public void groupJoin2_nullResultSelector_noExceptions()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            NullEqualityComparer, DummyClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullKeyType_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, NullClass, DummyClass);
    }

    @Test(expected = IllegalArgumentException.class)
    public void groupJoin2_nullElementType_throwsException()
    {
        Linq.groupJoin(DummyIterable, DummyIterable,
            DummyFunction, DummyFunction, DummyBiFunctionWithSequence,
            DummyEqualityComparer, DummyClass, NullClass);
    }

    @Test
    public void groupJoin2_listsOfElements_groupJoined()
    {
        // Arrange
        Person magnus = new Person("Magnus Hedlund");
        Person terry = new Person("Terry Adams");
        Person charlotte = new Person("Charlotte Weiss");

        Pet barley = new Pet("Barley", terry);
        Pet boots = new Pet("Boots", terry);
        Pet whiskers = new Pet("Whiskers", charlotte);
        Pet daisy = new Pet("Daisy", magnus);

        ArrayList<Person> outer = new ArrayList<>();
        outer.add(magnus);
        outer.add(terry);
        outer.add(charlotte);

        ArrayList<Pet> inner = new ArrayList<>();
        inner.add(barley);
        inner.add(boots);
        inner.add(whiskers);
        inner.add(daisy);

        ArrayList<String> magnusPetNames = new ArrayList<>();
        magnusPetNames.add(daisy.name);

        ArrayList<String> terryPetNames = new ArrayList<>();
        terryPetNames.add(barley.name);
        terryPetNames.add(boots.name);

        ArrayList<String> charlottePetNames = new ArrayList<>();
        charlottePetNames.add(whiskers.name);

        ArrayList<Tuple2<String, Iterable<String>>> expected = new ArrayList<>();
        expected.add(new Tuple2<>(magnus.name, magnusPetNames));
        expected.add(new Tuple2<>(terry.name, terryPetNames));
        expected.add(new Tuple2<>(charlotte.name, charlottePetNames));

        EqualityComparer<String> stringComparer = EqualityComparer.getDefault(String.class);
        IEqualityComparer<Person> personComparer = new IEqualityComparer<Person>()
        {
            @Override
            public boolean equals(Person x, Person y)
            {
                return stringComparer.equals(x.name, y.name);
            }

            @Override
            public int hashCode(Person obj)
            {
                return stringComparer.hashCode(obj.name);
            }
        };

        // Act
        Iterable<Tuple2<String, Iterable<String>>> actual
            = Linq.groupJoin(outer, inner,
                person -> person,
                pet -> pet.owner,
                (person, pets) -> new Tuple2<>(person.name, pets.select(pet -> pet.name)),
                personComparer,
                Person.class, Pet.class);

        // Assert
        assertOwnerAndPetsNamesSequencesAreEqual(expected, actual);
    }

    // endregion

    private void assertOwnerAndPetsNamesSequencesAreEqual(
        Iterable<Tuple2<String, Iterable<String>>> expected,
        Iterable<Tuple2<String, Iterable<String>>> actual)
    {
        Assert.assertNotNull(actual);

        Iterator<Tuple2<String, Iterable<String>>> expectedIterator = expected.iterator();
        Iterator<Tuple2<String, Iterable<String>>> actualIterator = actual.iterator();

        while (expectedIterator.hasNext())
        {
            Assert.assertTrue("The actual sequence contains too few items.", actualIterator.hasNext());

            Tuple2<String, Iterable<String>> expectedItem = expectedIterator.next();
            Tuple2<String, Iterable<String>> actualItem = actualIterator.next();

            if (expectedItem == null)
            {
                Assert.assertNull(actualItem);
                continue;
            }

            Assert.assertNotNull(actualItem);

            Assert.assertEquals(expectedItem.getItem1(), actualItem.getItem1());
            CollectionAssert.assertSequenceEquals(expectedItem.getItem2(), actualItem.getItem2());
        }

        Assert.assertFalse("The actual sequence contains too many items.", actualIterator.hasNext());
    }

    private static class Person
    {
        public Person(String name)
        {
            this.name = name;
        }

        public String name;

        @Override
        public String toString()
        {
            return "Person [name=" + name + "]";
        }
    }

    private static class Pet
    {
        public Pet(String name, Person owner)
        {
            this.name = name;
            this.owner = owner;
        }

        public String name;
        public Person owner;

        @Override
        public String toString()
        {
            return "Pet [name=" + name + ", owner=" + owner + "]";
        }
    }
}
