package net.intelie.challenges;

import java.util.Iterator;
import java.util.NavigableSet;
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
        Event startEvent = new Event("any_type", startTime);
        Event endEvent = new Event("any_type", endTime);
        endEvent = _concurrentSkipListSet.ceiling(endEvent);
        startEvent = _concurrentSkipListSet.floor(startEvent);
        NavigableSet<Event> navegaleSet = _concurrentSkipListSet.subSet(startEvent, endEvent);
        return new EventIteratorImplementation(type, navegaleSet);

    }
}
