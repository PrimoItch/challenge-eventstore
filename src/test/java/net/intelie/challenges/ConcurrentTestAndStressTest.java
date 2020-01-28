package net.intelie.challenges;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This tests are intended to demostrate the concurrent
 * capability of the implementation as well the
 * some stress test.
 */
public class ConcurrentTestAndStressTest {

    @Test
    public void concurrentTestAddAndInsert() throws InterruptedException {

        Calendar calendar = Calendar.getInstance();
        EventStore eventStore = new EventStoreImplementation();
        long minTimestamp = (calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR, 1);
        long maxTimestamp = calendar.getTimeInMillis();

        EventConusmer conusmer = new EventConusmer(eventStore, minTimestamp, maxTimestamp);
        EventInserter inserter = new EventInserter(eventStore, minTimestamp, maxTimestamp);

        conusmer.start();

        while (true)
        {
            Thread.sleep(100);

        }
    }
}

