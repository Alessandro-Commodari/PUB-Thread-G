package ca.mcgill.ecse420.a2;

public class FilterTest {

  public static void main(String[] args) throws InterruptedException {
    int threads = 4;
    int limit = 3000;
    Filter filter = new Filter(threads);
    SharedCounter counter = new SharedCounter(limit, filter);


    Thread[] threadPool = new Thread[threads];
    for (int i = 0; i < threads; i++) {
      threadPool[i] = new Thread(counter);
    }

    for (int i = 0; i < threads; i++) {
      threadPool[i].start();
    }

    for (int i = 0; i < threads; i++) {
      threadPool[i].join();
    }

  }
}
