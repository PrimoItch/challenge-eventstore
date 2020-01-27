package net.intelie.challenges;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * Implementation of the EventStore interface.
 */
public class EventStoreImplementation implements EventStore {

    ConcurrentSkipListSet<Event> _concurrentSkipListSet = new ConcurrentSkipListSet<>();

    @Override
    public void insert(Event event) {
        if(event == null)
            throw new NullPointerException();
        _concurrentSkipListSet.add(event);

    }

    @Override
    public void removeAll(String type) {
        Iterator<Event> iterator =  _concurrentSkipListSet.iterator();
        iterator.forEachRemaining(event -> {
            if(event.type().equals(type))
                _concurrentSkipListSet.remove(event);
        });
    }

    @Override
    public EventIterator query(String type, long startTime, long endTime) {
        return null;
    }
}
