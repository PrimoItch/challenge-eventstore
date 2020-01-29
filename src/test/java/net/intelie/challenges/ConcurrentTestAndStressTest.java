package net.intelie.challenges;

import org.junit.Test;

import java.sql.Time;
import java.time.DateTimeException;
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

    @Test
    public void concurrentTestAddAndInsert() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        EventStore eventStore = new EventStoreImplementation();
        long minTimestamp = (calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR, 1);
        long maxTimestamp = calendar.getTimeInMillis();

        long delayOnConsuming = 10L;
        long delayOnInserting = 5L;

        EventConusmer consumer = new EventConusmer(eventStore, minTimestamp, maxTimestamp, delayOnConsuming);
        EventInserter inserter = new EventInserter(eventStore, minTimestamp, maxTimestamp, delayOnInserting);

        Thread consumerThread = consumer.start();
        Thread inserterThread = inserter.start();

        Thread.sleep(60*100);

        consumerThread.interrupt();
        inserterThread.interrupt();

        Thread.sleep(100);
    }
}

