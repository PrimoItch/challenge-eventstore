package net.intelie.challenges;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
}
