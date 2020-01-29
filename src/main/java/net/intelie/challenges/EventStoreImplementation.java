package net.intelie.challenges;

import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * Implementation of the EventStore interface.
 */
public class EventStoreImplementation implements EventStore {

    /**
     * The EventStoreImplementation is the implementation for the EventStore
     * interface provided by the challenger.
     * The collection chosen to store the events was the ConcurrentSkipListSet.
     * It’s important characteristic, that suits to this specific challenger are,
     * according to the official documentations:
     *  - It is thread safe;
     *  - Elements in the collection are keep sorted, what is useful for iteration and
     *    range selection purpose;
     *  - It’s expected time cost is log(n) for contains, add and remove operations;
     *  - The subSet method returns a NavigableSet<Event>, that is a sub set EventStoreImplementation
     *    collection, capable of navigating through the sub set specified by the arguments;
     */
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

        // This validation was added to avoid the user insert a start time bigger then an endTime
        if(startTime > endTime)
            throw new IllegalArgumentException("startTime time should be smaller then endTime");

        Event startEvent = new Event("any_type", startTime);
        Event endEvent = new Event("any_type", endTime);

        NavigableSet<Event> navigableSet =
                _concurrentSkipListSet.subSet(startEvent, true, endEvent, false );
        return new EventIteratorImplementation(type, navigableSet);
    }
}
