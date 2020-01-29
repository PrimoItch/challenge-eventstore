package net.intelie.challenges;

import java.util.Random;

public class EventInserter implements Runnable {

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
    public EventInserter(EventStore eventStore, long minTimestamp, long maxTimestamp,
                         long delay){
        this.eventStore = eventStore;
        this.minTimestamp = minTimestamp;
        this.maxTimestamp = maxTimestamp;
        this.delay = delay;
        random = new Random();
    }

    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long numberOne = (long)(Math.random() * (maxTimestamp-minTimestamp));

            String type = random.nextBoolean() == true ? "typeOne" : "typeTwo";

            eventStore.insert(new Event(type, numberOne));
        }

    }

    public Thread start() {
        Thread thread = new Thread(this, "Inserter thread");
        thread.start();
        return thread;
    }

}
