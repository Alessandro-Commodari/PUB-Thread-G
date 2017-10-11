package ca.mcgill.ecse420.a1;

public class Question2 {

  public static void main(String[] args) {
    // the two locks in question
    String l1 = "this is the first lock...";
    String l2 = "this is the second lock...";

    Thread thrd1 = new Thread() {
      public void run() {

        // first thread 1 tries to get the first lock which it does
        synchronized (l1) {
          System.out.println("Thread 1: Holding onto lock 1...");
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
          }
          System.out.println("Thread 1: Waiting for lock 2 to be free...");
          // next thread 1 tries to get the second lock which in this case won't happen
          synchronized (l2) {
            System.out.println("Thread 1: Holding onto lock 1 and 2...");
          }
        }
      }
    };

    Thread thrd2 = new Thread() {
      public void run() {

        // first thread 2 tries to get the second lock which it does
        synchronized (l2) {
          System.out.println("Thread 2: Holding onto lock 2...");

          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
          }
          System.out.println("Thread 2: Waiting for lock 1 to be free...");
          // next thread 1 tries to get the second lock which in this case won't happen
          synchronized (l1) {
            System.out.println("Thread 2: Holding onto lock 1 and 2...");
          }
        }
      }
    };
    // start both threads
    thrd1.start();
    thrd2.start();

  }
}
