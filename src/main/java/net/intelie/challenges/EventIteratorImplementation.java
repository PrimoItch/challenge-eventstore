package net.intelie.challenges;

import java.util.Iterator;
import java.util.NavigableSet;

public class EventIteratorImplementation implements EventIterator {

    private final Iterator<Event> _interator;
    private final String _type;
    private final NavigableSet<Event> _navigableSet;
    private Event _currentEvent;
    private boolean _hasCalledMoveNext = false;
    private boolean _hasNext = false;

    public EventIteratorImplementation(String type, NavigableSet<Event> navigableSet)
    {
        _type = type;
        _navigableSet = navigableSet;
        _interator = navigableSet.iterator();
    }

    @Override
    public boolean moveNext() {
        _hasCalledMoveNext = true;
        _hasNext = false;
        while (_interator.hasNext())
        {
            _currentEvent = _interator.next();
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

    }
}
