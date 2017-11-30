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

  public boolean add(int item) {
    head.lock();
    Node pred = head;
    try {
      Node curr = pred.currNext();
      curr.lock();
      try {
        while (curr.key() < item) {
          pred.unlock();
          pred = curr;
          curr = curr.currNext();
          curr.lock();
        }
        if (curr.key() == item) {
          return false;
        }
        Node newNode = new Node(item);
        newNode.newNext(curr);
        pred.next = newNode;
        return true;
      } finally {
        curr.unlock();
      }
    } finally {
      pred.unlock();
    }
  }

  public boolean remove(int item) {
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
        if (curr.key() == item) {
          pred.newNext(curr.currNext());
          return true;
        }
        return false;
      } finally {
        curr.unlock();
      }
    } finally {
      pred.unlock();
    }
  }

}
