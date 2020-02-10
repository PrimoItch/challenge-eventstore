package net.intelie.challenges;

import java.util.Random;

/**
 * This class represents an entity that consumes the events that are
 * stored.
 * It makes queries, read the iterator, remove the element from the iterator
 * with a 50% of probability and remove all elements from the
 * eventStore with a 0.1% of probability
 */
public class EventConsumer implements Runnable {
    private final Random random;
    private EventStore eventStore;
    private long minTimestamp;
    private long maxTimestamp;
    private long delay;

    /**
     *
     * @param eventStore
     * @param minTimestamp
     * @param maxTimestamp
     */
    public EventConsumer(EventStore eventStore, long minTimestamp, long maxTimestamp,
                         long delay) {

        this.eventStore = eventStore;
        this.minTimestamp = minTimestamp;
        this.maxTimestamp = maxTimestamp;
        this.delay = delay;
        this.random = new Random();
    }
    @Override
    public void run() {

        while (true){
            long numberOne = (long)(Math.random() * (maxTimestamp-minTimestamp));
            long numberTwo = (long)(Math.random() * (maxTimestamp-minTimestamp));

            if(numberOne > numberTwo){
                long aux = numberOne;
                numberOne = numberTwo;
                numberTwo = numberOne;
            }

            String type = random.nextBoolean() == true ? "typeOne" : "typeTwo";

            EventIterator iterator = eventStore.query(type, numberOne, numberTwo);

            if(random.nextFloat() < 0.001F)
                eventStore.removeAll(type);

            while (iterator.moveNext())
            {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Event event = iterator.current();
                if(random.nextBoolean())
                    iterator.remove();
            }
        }
    }

    public Thread start(){
        Thread thread = new Thread(this, "consumer thread");
        thread.start();
        return thread;
    }
}
