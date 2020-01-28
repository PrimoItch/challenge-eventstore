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
    public EventIterator query(String type, long startTime, long endTime) throws IllegalArgumentException {
        if(startTime > endTime)
            throw new IllegalArgumentException("startTime time should be smaller then endTime");

        if(_concurrentSkipListSet.isEmpty())
            return getEmptyEventIterator(type, startTime, endTime);

        Event startEvent = new Event("any_type", startTime);
        Event endEvent = new Event("any_type", endTime);
        endEvent = _concurrentSkipListSet.ceiling(endEvent);
        if(endEvent == null)
            //There is no such grater or equal timestamp, so get the newest event.
            endEvent = _concurrentSkipListSet.last();

        startEvent = _concurrentSkipListSet.floor(startEvent);
        if(startEvent == null)
            //There is no such smaller or equal timestamp, so get the oldest event.
            startEvent = _concurrentSkipListSet.first();

        NavigableSet<Event> navigableSet =
                _concurrentSkipListSet.subSet(startEvent, true, endEvent, true);
        return new EventIteratorImplementation(type, navigableSet, startTime, endTime);
    }

    private EventIteratorImplementation getEmptyEventIterator(String type, long startTime, long endTime) {
        return new EventIteratorImplementation(type,
                _concurrentSkipListSet.headSet(new Event(type, startTime))
                , startTime,
                endTime);
    }
}
