package ca.mcgill.ecse420.a3;

public class FineGrainedList<T> {
  private Node head;

  public FineGrainedList() {
    head = new Node(Integer.MIN_VALUE);
    head.newNext(new Node(Integer.MAX_VALUE));
  }

  public boolean contains(int item) {
    Node pred = null;
    Node curr = null;
    head.lock();
    try {
      pred = head;
      curr = pred.currNext();
      curr.lock();
      try {
        while (curr.key() < item) {
          pred.unlock();
          pred = curr;
          curr = curr.currNext();
          curr.lock();
        }
        return (curr.key() == item);
      } finally {
        curr.unlock();
      }
    } finally {
      pred.unlock();
    }
  }

}
