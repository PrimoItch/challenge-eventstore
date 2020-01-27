package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.*;


public class EventStoreImplementationTest {
    @Test
    public void testAddOneEvent(){
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        Event eventOne = new Event("type-1", 1L);

        //Act
        eventStore.insert(eventOne);

        // Assert
        assertTrue(true);
    }

    @Test
    public void testGetItarator() {
        // Arrange
        EventStore eventStore = new EventStoreImplementation();

        Event event_1 = new Event("type-1", 1L);
        Event event_2 = new Event("type-1", 2L);
        Event event_3 = new Event("type-1", 3L);
        Event event_4 = new Event("type-1", 4L);

        eventStore.insert(event_1);
        eventStore.insert(event_2);
        eventStore.insert(event_3);
        eventStore.insert(event_4);

        // Assert
        EventIterator iterator = eventStore.query("type-1", 2, 3);

        boolean hasNext;

        hasNext = iterator.moveNext();
        assertTrue(hasNext);
        assertEquals(event_2, iterator.current());

        hasNext = iterator.moveNext();
        assertTrue(hasNext);
        assertEquals(event_3, iterator.current());

        hasNext = iterator.moveNext();
        assertFalse(hasNext);


    }
}
