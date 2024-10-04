package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        // mQueue = new LinkedIntQueue();
        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse(mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertEquals(mQueue.peek(), null);
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(testList.get(0));
        mQueue.enqueue(testList.get(1));
        assertEquals(mQueue.peek(), testList.get(0));
    }

    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testEmptyDequeue() {
        assertEquals(mQueue.dequeue(), null);
    }

    @Test
    public void testDequeue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(mQueue.dequeue(), testList.get(i));
            assertEquals(mQueue.size(), testList.size() - 1 - i);
        }
    }

    @Test
    public void testClear() {
        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertTrue(mQueue.size() == 0);
        assertEquals(mQueue.dequeue(), null);
        assertEquals(mQueue.peek(), null);
    }

    @Test
    public void testContentClear() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        testClear();
    }

    @Test
    public void testSize() {
        assertTrue(mQueue.size() == 0);
        mQueue.dequeue();
        assertTrue(mQueue.size() == 0);
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        assertTrue(mQueue.size() == 2);
        mQueue.peek();
        assertTrue(mQueue.size() == 2);
        mQueue.dequeue();
        assertTrue(mQueue.size() == 1);
        mQueue.clear();
        assertTrue(mQueue.size() == 0);
    }

    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testEnsureCapacity() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        assertTrue(mQueue.size() == testList.size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(mQueue.peek(), testList.get(i));   
            assertEquals(mQueue.dequeue(), testList.get(i));
            assertTrue(mQueue.size() == testList.size() - 1 - i);
        }
    }

    @Test
    public void testEnsureCapacityHeadOffset() {
        for (int i = 0; i < 6; i++) {
            mQueue.enqueue(testList.get(i));
            mQueue.dequeue();
        }
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(mQueue.peek(), testList.get(0));
        }
        assertTrue(mQueue.size() == testList.size());
        for (int i = 0; i < testList.size(); i++) {
            assertEquals(mQueue.peek(), testList.get(i));   
            assertEquals(mQueue.dequeue(), testList.get(i));
            assertTrue(mQueue.size() == testList.size() - 1 - i);
        }
    }

    @Test
    public void testClearAfterEnsureCapacity() {
        for (int i = 0; i < 6; i++) {
            mQueue.enqueue(testList.get(i));
            mQueue.dequeue();
        }
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        testClear();
    }

    @Test
    public void testCircleHead() {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 6; i++) {
                mQueue.enqueue(testList.get(i));
                assertEquals(mQueue.peek(), testList.get(0));   
            }
            assertTrue(mQueue.size() == 6);
            for (int i = 0; i < 6; i++) {
                assertEquals(mQueue.peek(), testList.get(i));   
                assertEquals(mQueue.dequeue(), testList.get(i)); 
                assertTrue(mQueue.size() == 6 - 1 - i);
            }
            assertTrue(mQueue.isEmpty());
        }
    }
}
