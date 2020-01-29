package net.intelie.challenges;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.Assert.*;

public class EventTest {
    /*
     * Constructor test
     */

    @Test
    public void thisIsAWarning() throws Exception {

        Event event = new Event("some_type", 123L);

        //THIS IS A WARNING:
        //Some of us (not everyone) are coverage freaks.
        assertEquals(123L, event.timestamp());
        assertEquals("some_type", event.type());
    }

    @Test
    public void passNullForTypeShouldThrowIllegalArgumentException() {
        //Arrange
        boolean throwIllegalArgumentException = false;
        try
        {
            // Act
            Event event = new Event(null, 123L);
        }
        catch (IllegalArgumentException exception)
        {
            throwIllegalArgumentException=true;
        }

        //Assert
        assertTrue(throwIllegalArgumentException);
    }

    @Test
    public void passEmptyStringForTypeShouldNotThrowIllegalArgumentException()  {
        // Arrange
        boolean throwIllegalArgumentException = false;
        try
        {
            // Act
            Event event = new Event("", 123L);
        }
        catch (IllegalArgumentException exception)
        {
            throwIllegalArgumentException=true;
        }

        //Assert
        assertFalse(throwIllegalArgumentException);
    }

    /*
     * Equals test
     */

    @Test
    public void sameEventShouldBeEqual()  {
        // Arrange
        Event event = new Event("some_type", 123L);

        // Act
        boolean areEventEquals = event.equals(event);

        // Assert
        assertTrue(areEventEquals);
    }

    @Test
    public void twoDifferentEventsInstancesShouldBeDifferent()  {
        // Arrange
        Event event_one = new Event("some_type", 123L);
        Event event_two = new Event("some_type", 123L);

        // Act
        boolean areEventEquals = event_one.equals(event_two);

        // Assert
        assertFalse(areEventEquals);
    }

    @Test
    public void eventIsNotEqualOtherObject()  {
        // Arrange
        Event event_one = new Event("some_type", 1L);
        String otherObject = "other object";

        // Act
        boolean iseEventOneEqualsToString = event_one.equals(otherObject);

        // Assert
        assertFalse(iseEventOneEqualsToString);
    }


    /*
     * compareTo Tests
     */

    @Test
    public void eventWithEarlyTimestampIsSmaller()  {

        // Arrange
        Event eventOne = new Event("some_type", 1L);
        Event eventTwo = new Event("some_type", 2L);

        // Act
        int isEventOneSmallerThenEventTwo = eventOne.compareTo(eventTwo);

        // Assert
        assertTrue(isEventOneSmallerThenEventTwo == -1);
    }

    @Test
    public void eventWithLaterTimestampIsBigger()  {

        // Arrange
        Event eventOne = new Event("some_type", 1L);
        Event eventTwo = new Event("some_type", 2L);

        // Act
        int eventTwoOrder = eventTwo.compareTo(eventOne);

        // Assert
        assertTrue(eventTwoOrder == 1);
    }

    @Test
    public void eventWithSameTimestampIsConsideredEquals()  {

        // Arrange
        Event event_one = new Event("some_type", 1L);
        Event event_two = new Event("some_type", 1L);

        // Act
        int eventOneOrder = event_one.compareTo(event_two);

        // Assert
        assertTrue(eventOneOrder == 0);
    }

    @Test
    public void sameEventsAreConsideredEquals()  {

        // Arrange
        Event event_one = new Event("some_type", 1L);

        // Act
        int eventOneOrder = event_one.compareTo(event_one);

        // Assert
        assertTrue(eventOneOrder == 0);
    }

    /*
     * HashCode Tests
    */

    @Test
    public void sameEventHasSameHashs()  {

        // Arrange
        Event event_one = new Event("some_type", 1L);

        // Act
        int eventOneEqualsToString = event_one.hashCode();
        int sameEventOneEqualsToString = event_one.hashCode();

        // Assert
        assertTrue(eventOneEqualsToString == sameEventOneEqualsToString);
    }

    @Test
    public void twoDifferentEventHasDifferentHashs()  {

        // Arrange
        Event eventOne = new Event("some_type", 1L);
        Event eventTwo = new Event("some_type", 1L);

        // Act
        int eventOneEqualsToString = eventOne.hashCode();
        int eventTwoOneEqualsToString = eventTwo.hashCode();

        // Assert
        assertTrue(eventOneEqualsToString != eventTwoOneEqualsToString);
    }


}