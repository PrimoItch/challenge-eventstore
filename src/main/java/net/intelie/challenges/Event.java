package net.intelie.challenges;

import java.util.Objects;

import static java.util.UUID.randomUUID;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event  implements Comparable<Event> {
    private final String type;
    private final long timestamp;
    private final String _eventId;

    public Event(String type, long timestamp)  {
        if( type == null || type.isEmpty() )
            throw new IllegalArgumentException("Type attribute can'b be null or empty string");

        this.type = type;
        this.timestamp = timestamp;
        this._eventId = randomUUID().toString();
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

    private String eventId(){ return _eventId; }

    @Override
    public int compareTo(Event otherEvent) {
        if(this.eventId().equals(otherEvent.eventId()))
            return 0;
        if (this.timestamp() >= otherEvent.timestamp())
            return 1;
        if (this.timestamp() < otherEvent.timestamp())
            return -1;
        return 1; // this line will never be hit by test coverage, its here just for compiling purpose
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(this._eventId, event._eventId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(_eventId);
    }

}
