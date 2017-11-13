package ca.mcgill.ecse420.a2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock {

  boolean[] flag;
  int[] label;

  public Bakery(int n) {
    flag = new boolean[n];
    label = new int[n];
    for (int i = 0; i < n; i++) {
      flag[i] = false;
      label[i] = 0;
    }
  }

  @Override
  public void lock() {
    int i = ThreadID.get();
    int n = label.length;
    flag[i] = true;
    int maxVal = -1;
    for (int j = 1; j < n; j++) {
      if (label[j] > maxVal) {
        maxVal = label[j];
      }
    }
    label[i] = maxVal + 1;
    for (int k = 0; k < n; k++) {
      while ((k != i) && (flag[k] && (label[k] < label[i]) || ((label[k] == label[i]) && k < i))) {
      } ;
    }
  }

  @Override
  public void unlock() {
    flag[ThreadID.get()] = false;
  }



  /* not needed for this algorithm */



  @Override
  public void lockInterruptibly() throws InterruptedException {
    // TODO Auto-generated method stub

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

  @Override
  public Condition newCondition() {
    // TODO Auto-generated method stub
    return null;
  }
}
