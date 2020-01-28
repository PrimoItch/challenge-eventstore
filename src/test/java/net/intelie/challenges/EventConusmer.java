package net.intelie.challenges;

import java.util.Random;

public class EventConusmer implements Runnable {
    private final Random randon;
    private EventStore eventStore;
    private long minTimestamp;
    private long maxTimestamp;

    /**
     *
     * @param eventStore
     * @param minTimestamp
     * @param maxTimestamp
     */
    public EventConusmer(EventStore eventStore, long minTimestamp, long maxTimestamp){

        this.eventStore = eventStore;
        this.minTimestamp = minTimestamp;
        this.maxTimestamp = maxTimestamp;
        this.randon = new Random();
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

            String type = randon.nextBoolean() == true ? "typeOne" : "typeTwo";

            EventIterator iterator = eventStore.query(type, numberOne, numberTwo);

            while (iterator.moveNext())
            {
                Event event = iterator.current();
                if(randon.nextBoolean())
                    iterator.remove();
            }
        }
    }

    public void start(){
        Thread thread = new Thread(this, "consumer thread");
        thread.start();
    }
}
