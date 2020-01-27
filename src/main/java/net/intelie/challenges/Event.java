package net.intelie.challenges;

import java.util.Comparator;

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
}
