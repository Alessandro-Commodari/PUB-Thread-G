package ca.mcgill.ecse420.a2;

import java.util.concurrent.locks.Lock;

public class SharedCounter implements Runnable {
  private Lock lock;
  private int count;
  private int limit;

  public SharedCounter(int limit, Lock lock) {
    this.limit = limit;
    this.lock = lock;
    this.count = 0;
  }

  public int count() {
    lock.lock();
    int currentCount;

    if (count >= limit) {
      return count;
    }
    try {
      currentCount = count;
      count = currentCount + 1;
    } finally {
      lock.unlock();
    }
    return currentCount;
  }


  @Override
  public void run() {
    System.out.println("Entered thread with ID: " + ThreadID.get() + " and count: " + this.count);
    while (count() < limit) {
    }
    System.out.println("Exited " + ThreadID.get() + " and count: " + this.count);
  }
}
