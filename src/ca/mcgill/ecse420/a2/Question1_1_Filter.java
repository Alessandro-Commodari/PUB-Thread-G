package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Question1_1_Filter implements Lock {
  int[] level;
  int[] victim;

  public Question1_1_Filter(int n) {
    level = new int[n];
    victim = new int[n];
    for (int i = 0; i < n; i++) {
      level[i] = 0;
    }
  }

  public void lock() {
    int me = ThreadID.get();
    int n = level.length;
    for (int i = 1; i < n; i++) {
      level[me] = i;
      victim[i] = me;
      boolean conflicts = true;
      while (conflicts) {
        conflicts = false;
        for (int k = 1; k < n; k++) {
          if (k != me && level[k] >= i && victim[i] == me) {
            conflicts = true;
            break;
          }
        }
      }
    }
  }

  public void unlock() {
    int me = ThreadID.get();
    level[me] = 0;
  }



  /* not needed for this algorithm */



  @Override
  public void lockInterruptibly() throws InterruptedException {
    // TODO Auto-generated method stub

  }

  @Override
  public Condition newCondition() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean tryLock() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    // TODO Auto-generated method stub
    return false;
  }
}
