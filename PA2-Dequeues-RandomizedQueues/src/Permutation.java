import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    RandomizedQueue<String> rq = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      rq.enqueue(StdIn.readString());
    }
    int i = 0;
    for (String e : rq) {
      if (i == n) {
        break;
      }
      i++;
      StdOut.println(e);
    }
  }
}
