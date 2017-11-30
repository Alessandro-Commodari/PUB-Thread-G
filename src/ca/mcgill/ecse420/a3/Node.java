package ca.mcgill.ecse420.a3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
  private Lock lock;
  int key;
  Node next;

  public Node(int key) {
    this.key = key;
    next = null;
    this.lock = new ReentrantLock();
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

  public int key() {
    return key;
  }

  public Node currNext() {
    return next;
  }

  public void newNext(Node Next) {
    next = Next;
  }


}
