package net.intelie.challenges;

import java.util.Iterator;
import java.util.NavigableSet;

public class EventIteratorImplementation implements EventIterator {


     //  Sub set of the ConcurrentSkipListSet
    private final NavigableSet<Event> _navigableSet;


     // The Iterator of the sub set of the ConcurrentSkipListSet
    private final Iterator<Event> _iterator;

    //The event type
    private final String _type;

     //The Current Event
     private Event _currentEvent;

     //Flag to indicate that method moveNext was called
     private boolean _hasCalledMoveNext = false;

    //Flag to indicate if there is a new element to be read
    private boolean _hasNext = false;

    /**
     * Create an EventIterator.
     * @param type The event type.
     * @param navigableSet A sub set of the ConcurrentSkipListSet
     */
    public EventIteratorImplementation(String type, NavigableSet<Event> navigableSet)
    {
        _type = type;
        _navigableSet = navigableSet;
        _iterator = navigableSet.iterator();
    }

    @Override
    public boolean moveNext() {
        _hasCalledMoveNext = true;
        _hasNext = false;
        while (_iterator.hasNext())
        {
            _currentEvent = _iterator.next();
            if(_currentEvent.type().equals(_type)){
                _hasNext = true;
                return true;
            }
        }
        _hasNext = false;
        return false;
    }

    @Override
    public Event current() {
        checkIfNextEventExistOrMoveNextWasCalled();
        return _currentEvent;
    }

    @Override
    public void remove() {
        checkIfNextEventExistOrMoveNextWasCalled();
        _navigableSet.remove(_currentEvent);
    }

    private void checkIfNextEventExistOrMoveNextWasCalled(){
        if(!_hasCalledMoveNext)
            throw new IllegalStateException("moveNext was not called.");
        if(!_hasNext)
            throw new IllegalStateException("There is no more events.");
    }

    @Override
    public void close() throws Exception {
        // For now I don't think it's necessary some implementation here.
    }
}
