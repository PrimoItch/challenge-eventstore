package net.intelie.challenges;


/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event  implements Comparable<Event> {
    private final String type;
    private final long timestamp;

    /**
     * Instantiate an event. It needs to have a not null type
     * and a timestamp.
     *
     * @param type      The event type.
     * @param timestamp The {@param event } timestamp when it occurred.
     */
    public Event(String type, long timestamp)  {
        if( type == null )
            throw new IllegalArgumentException("Type attribute can'b be null");

        this.type = type;
        this.timestamp = timestamp;
    }

    /**
     * Returns the event type
     * @return The {@param event} type
     */
    public String type() {
        return type;
    }

    /**
     * The event timestamp.
     * @return the event {@param timestamp }.
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * The Comparable interface implementations.
     * Compare two events according to their timestamp.
     * Early events are considered "smaller" then older events.
     *
     * @param otherEvent Another event to be compared.
     * @return 1 if this event is older
     *         -1 if this event is earlier
     *         0 if both events has the same timestamp
     */
    @Override
    public int compareTo(Event otherEvent) {
        if (this.timestamp() > otherEvent.timestamp())
            return 1;
        if (this.timestamp() < otherEvent.timestamp())
            return -1;
        return 0;
    }
}
