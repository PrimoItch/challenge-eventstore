package net.intelie.challenges;

import java.util.Random;

public class EventInserter implements Runnable {

    private final Random random;
    private EventStore eventStore;
    private long minTimestamp;
    private long maxTimestamp;

    /**
     *
     * @param eventStore
     * @param minTimestamp
     * @param maxTimestamp
     */
    public EventInserter(EventStore eventStore, long minTimestamp, long maxTimestamp){
        this.eventStore = eventStore;
        this.minTimestamp = minTimestamp;
        this.maxTimestamp = maxTimestamp;
        random = new Random();
    }

    @Override
    public void run() {
        while (true){
            long numberOne = (long)(Math.random() * (maxTimestamp-minTimestamp));

            String type = random.nextBoolean() == true ? "typeOne" : "typeTwo";

            eventStore.insert(new Event(type, numberOne));

            if(random.nextBoolean())
                eventStore.removeAll(type);
        }

    }
}
