package ca.mcgill.ecse420.a2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadID {

  public static void main(String[] args) throws InterruptedException {

    int nThreads = 5;

    Thread[] pool = new Thread[nThreads];

    for (int i = 0; i < nThreads; i++) {
      pool[i] = new Thread(new PrintID());
    }

    for (int i = 0; i < nThreads; i++) {
      pool[i].start();
    }


    for (int i = 0; i < nThreads; i++) {
      pool[i].join();
    }

    ExecutorService expool = Executors.newFixedThreadPool(2);

    for (int i = 0; i < nThreads; i++) {
      expool.execute(new PrintID());
    }

    expool.shutdown();

  }

}
