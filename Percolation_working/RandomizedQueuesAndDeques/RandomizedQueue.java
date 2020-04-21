package RandomizedQueuesAndDeques;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s; // the queue
    private int n; // size of the queue


    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        s = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the queue
    private void resize(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tmp[i] = s[i];
        }
        s = tmp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == s.length) {
            resize(2 * s.length);
        }
        s[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (n == s.length / 4) {
            resize(s.length / 2);
        }
        int randomInt = StdRandom.uniform(n);
        Item item = s[randomInt];
        s[randomInt] = s[--n];
        s[n] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randomInt = StdRandom.uniform(n);
        return s[randomInt];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // iterator of the array
    private class ArrayIterator implements Iterator<Item> {
        private int current; // the current location in the iterate
        private int[] randomIndices; // a list of random index for iteration

        // set up iterator
        public ArrayIterator() {
            current = 0;
            randomIndices = new int[n];
            for (int j = 0; j < n; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        public boolean hasNext() {
            return current < n;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return s[randomIndices[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println("size :" + queue.size());
        for (Integer i : queue) {
            System.out.println(i);
        }
        System.out.println("sample :" + queue.sample());
        System.out.println("dequeue :");
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
        System.out.println("size :" + queue.size());
    }

}
