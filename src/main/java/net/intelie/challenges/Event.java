package net.intelie.challenges;

import java.util.Objects;

import static java.util.UUID.randomUUID;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event  implements Comparable<Event> {
    private final String type;
    private final long timestamp;
    private final String eventId;

    public Event(String type, long timestamp)  {
        this.type = type;
        this.timestamp = timestamp;
        this.eventId = randomUUID().toString();
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

    public String eventId(){ return eventId; }

    @Override
    public int compareTo(Event otherEvent) {
        if(this.eventId().equals(otherEvent.eventId()))
            return 0;
        if (this.timestamp() >= otherEvent.timestamp())
            return 1;
        if (this.timestamp() < otherEvent.timestamp())
            return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }
}
