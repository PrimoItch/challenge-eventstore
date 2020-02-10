package net.intelie.challenges;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This tests are intended to demonstrate the concurrent
 * capability of the implementation.
 *
 *
 */
public class ConcurrentTestAndStressTest {

    /**
     * This test create two  entities: one that insert data to the Evenet Store
     * and one that reads it.
     * They live in two separate threads that are active on the same time, reading
     * and adding events to the event store.
     *
     * Despite it's not a real test, because it doesn't have an assert step, but it
     * serve us to demonstrate the code ability to deal with concurrent access to the
     * event store.
     *
     */
    @Test
    public void concurrentTestAddAndInsert() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        EventStore eventStore = new EventStoreImplementation();
        long minTimestamp = (calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR, 1);
        long maxTimestamp = calendar.getTimeInMillis();

        long delayOnConsuming = 10L;
        long delayOnInserting = 5L;

        EventConsumer consumer = new EventConsumer(eventStore, minTimestamp, maxTimestamp, delayOnConsuming);
        EventInserter inserter = new EventInserter(eventStore, minTimestamp, maxTimestamp, delayOnInserting);

        Thread consumerThread = consumer.start();
        Thread inserterThread = inserter.start();

        Thread.sleep(60*100);

        consumerThread.interrupt();
        inserterThread.interrupt();

        Thread.sleep(100);
    }
}

