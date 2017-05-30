package potter.linq.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Map;

/**
 * Provides static methods for making assertions on collections.
 *
 * @author Daniel Potter
 */
public class CollectionAssert
{
    /**
     * Asserts that two sequences are equivalent.
     *
     * @param expected
     *            The sequence with the expected items.
     * @param actual
     *            The sequence to assert.
     */
    public static void assertSequenceEquals(Iterable<?> expected, Iterable<?> actual)
    {
        if (expected == null)
        {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        Iterator<?> expectedIterator = expected.iterator();
        Iterator<?> actualIterator = actual.iterator();

        while (expectedIterator.hasNext())
        {
            assertTrue("The actual sequence contains too few items.", actualIterator.hasNext());

            Object expectedItem = expectedIterator.next();
            Object actualItem = actualIterator.next();

            assertEquals(expectedItem, actualItem);
        }

        assertFalse("The actual sequence contains too many items.", actualIterator.hasNext());
    }

    /**
     * Asserts that two sequences and all nested sequences are equivalent.
     *
     * @param expected
     *            The sequence with the expected items.
     * @param actual
     *            The sequence to assert.
     */
    public static void assertNestedSequencesEquals(Iterable<?> expected, Iterable<?> actual)
    {
        if (expected == null)
        {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        Iterator<?> expectedIterator = expected.iterator();
        Iterator<?> actualIterator = actual.iterator();

        while (expectedIterator.hasNext())
        {
            assertTrue("The actual sequence contains too few items.", actualIterator.hasNext());

            Object expectedItem = expectedIterator.next();
            Object actualItem = actualIterator.next();

            if (Iterable.class.isInstance(expectedItem))
            {
                assertTrue(Iterable.class.isInstance(actualItem));

                Iterable<?> expectedSequence = (Iterable<?>) expectedItem;
                Iterable<?> actualSequence = (Iterable<?>) actualItem;

                assertNestedSequencesEquals(expectedSequence, actualSequence);
            }
            else
            {
                assertEquals(expectedItem, actualItem);
            }
        }

        assertFalse("The actual sequence contains too many items.", actualIterator.hasNext());
    }

    /**
     * Asserts that two maps are equivalent.
     *
     * @param expected
     *            The map with the expected entries.
     * @param actual
     *            The map to assert.
     */
    public static void assertMapEquals(Map<?, ?> expected, Map<?, ?> actual)
    {
        if (expected == null)
        {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);

        assertSequenceEquals(expected.entrySet(), actual.entrySet());
    }
}
