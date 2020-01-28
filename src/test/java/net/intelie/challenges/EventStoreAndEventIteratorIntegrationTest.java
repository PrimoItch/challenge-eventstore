package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * For integration test basically all elements needed to be
 * tested together: Event, EventIteratorImplementation and EventStoreImplementation
 */
public class EventStoreAndEventIteratorIntegrationTest {

    /**
     * Insert Tests
     */

    @Test
    public void testAddOneNotNullEvent(){
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        Event eventOne = new Event("type-1", 1L);

        //Act
        try{
            eventStore.insert(eventOne);
        }catch (Exception exception){
            fail();
        }

        // Assert
        assertTrue(true);
    }

    @Test
    public void testAddOneNullEvent(){
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        boolean throwNullPointerException = false;
        //Act
        try{
            eventStore.insert(null);
        }catch (NullPointerException exception){
            // Assert
            throwNullPointerException=true;
        }

        assertTrue(throwNullPointerException);
    }

    /**
     * removeAll Tests
     */
    @Test
    public void testRemoveOneEventFromEventStore(){
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        Event event = new Event("type", 1L);
        eventStore.insert(event);
        EventIterator query = eventStore.query("type",1,1);
        if(query.moveNext())
        {
            Event currentEvent = query.current();

            //act
            query.remove();
        }else
        {
            fail();
        }

        query = eventStore.query("type",1,1);
        boolean hasNext = query.moveNext();

        // Assert
        assertFalse(hasNext);
    }

    @Test
    public void testRemoveEventOfOneTypeFromEventStore(){
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        Event event1 = new Event("type1", 1L);
        Event event2 = new Event("type2", 2L);
        eventStore.insert(event1);
        eventStore.insert(event2);

        //Act
        eventStore.removeAll("type1");

        EventIterator query = eventStore.query("type2",1,2);
        boolean hasNext = query.moveNext();
        Event currentEvent = query.current();

        // Assert
        if(hasNext)
        {
            assertEquals(event2, currentEvent);
        }
        else
        {
            fail();
        }
    }

    @Test
    public void testRemoveEventOfOneNonExistentTypeFromEventStore() throws IllegalStateException{
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        Event event1 = new Event("type1", 1L);
        eventStore.insert(event1);
        boolean hasThrowIllegalStateException = false;

        //Act
        eventStore.removeAll("type2");
        EventIterator query = eventStore.query("type1",1,1);
        boolean hasNext = query.moveNext();

        // Assert
        assertTrue(hasNext);
        assertTrue(event1.equals(query.current()));
    }


    @Test
    public void iteratorWithStartTimeSmallerThenGraterTimeThrowsIllegalArgumentException() {
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        boolean throwIllegalArgumentException = false;

        //Act
        try{
            EventIterator query = eventStore.query("type1",2,1);
        }catch (IllegalArgumentException exception){
            throwIllegalArgumentException = true;
        }

        // Assert
        assertTrue(throwIllegalArgumentException);
    }


    @Test
    public void testInsertFourEventsAndIterateAmongThen() {
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

    @Test
    public void callCurrentWithoutCallMoveNextShouldThrowIllegalStateException() {
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        boolean throwIllegalStateException = false;

        //Act
        try{
            EventIterator query = eventStore.query("type1",1,1);
            query.current();
        }catch (IllegalStateException exception){
            throwIllegalStateException = true;
        }

        // Assert
        assertTrue(throwIllegalStateException);
    }

    @Test
    public void callCurrentWhenMoveNextReturnedFalseShouldThrowIllegalStateException() {
        // Arrange
        EventStore eventStore = new EventStoreImplementation();
        boolean throwIllegalStateException = false;
        boolean hasNext = true;
        //Act
        try{
            EventIterator query = eventStore.query("type1",1,1);
            hasNext = query.moveNext();
            query.current();
        }catch (IllegalStateException exception){
            throwIllegalStateException = true;
        }

        // Assert
        assertTrue(throwIllegalStateException);
        assertFalse(hasNext);
    }



}
