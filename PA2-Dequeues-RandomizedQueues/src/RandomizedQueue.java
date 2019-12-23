import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int n;               // number of elements on queue
  private int capacity;               // capacity of q
  private int minCapacity;               // capacity of q
  private Item[] q;

  private RandomizedQueue(int minCapacity) { // construct an empty randomized queue
    this.minCapacity = minCapacity;
    this.capacity = minCapacity;
    this.n = 0;
    this.q = (Item[]) new Object[capacity];
  }

  public RandomizedQueue() { // construct an empty randomized queue
    this(2);
  }

  public boolean isEmpty() { // is the randomized queue empty?
    return n == 0;
  }

  public int size() { // return the number of items on the randomized queue
    return n;
  }

  public void enqueue(Item item) { // add the item
    if (item == null)
      throw new java.lang.IllegalArgumentException();
    if (n == capacity) {
      resizeArray(capacity * 2);
    }
    q[n++] = item;
  }

  public Item dequeue() { // remove and return a random item
    if (n == 0)
      throw new NoSuchElementException();
    int index = StdRandom.uniform(n);
    Item ret = q[index];
    q[index] = q[--n];
    q[n] = null;
    if (n == capacity / 4)
      if (capacity / 2 >= minCapacity)
        resizeArray(capacity / 2);
    return ret;
  }

  public Item sample() { // return a random item (but do not remove it)
    if (n == 0)
      throw new NoSuchElementException();
    int index = StdRandom.uniform(n);
    return q[index];
  }

  public Iterator<Item> iterator() { // return an independent iterator over items in random order
    return new RandomIterator<Item>(q, n);
  }

  private class RandomIterator<Item> implements Iterator<Item> {
    private final Item[] q;
    private final int n;
    private final int[] indexes;
    private int current;

    public RandomIterator(Item[] q, int n) {
      this.q = Arrays.copyOf(q, n);
      this.n = n;
      this.indexes = StdRandom.permutation(n);
      this.current = 0;
    }

    public boolean hasNext() {
      return current != n;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      return q[indexes[current++]];
    }
  }

  private void resizeArray(int newSize) {
    q = Arrays.copyOf(q, newSize);
    capacity = newSize;
  }

  public static void main(String[] args) { // unit testing (optional)
    RandomizedQueue<String> rq = new RandomizedQueue<String>();

    assert rq.isEmpty();
    rq.enqueue("porcoddio");
    assert !rq.isEmpty();
    rq.enqueue("porcamadonna");
    rq.enqueue("tutti gli angeli");
    rq.enqueue("in colonna");
    rq.enqueue("un angelo son'io");
    rq.enqueue("che mi chiamo");
    rq.enqueue("porcoddio");
    System.out.println("RQ size: " + rq.size());
    assert !rq.isEmpty();
    System.out.println(rq.sample());
    System.out.println("RQ size: " + rq.size());
    for (String s : rq) {
      System.out.println(s);
    }
  }
}