package ca.mcgill.ecse420.a1;

public class Question3 {

  public static void main(String[] args) throws Exception{
    
    Philosopher[] philosophers = new Philosopher[5];
    Object[] chopsticks = new Object[philosophers.length];

    for (int i = 0; i < chopsticks.length; i++) {
        chopsticks[i] = new Object();
    }

    for (int i = 0; i < philosophers.length; i++) {
        Object leftFork = chopsticks[i];
        Object rightFork = chopsticks[(i + 1) % chopsticks.length];

        philosophers[i] = new Philosopher(leftFork, rightFork);
         
        Thread t 
          = new Thread(philosophers[i], "Philosopher " + (i + 1));
        t.start();
    }
    
    public static class Philosopher implements Runnable {
      
      // The forks on either side of this Philosopher 
      private Object Chopstick_L;
      private Object Chopstick_R;
   
      public Philosopher(Object Chopstick_L, Object Chopstick_R) {
          this.Chopstick_L = Chopstick_L;
          this.Chopstick_R = Chopstick_R;
      }
      
      private void doSomething(String action) throws InterruptedException {
        System.out.println(
          Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
      }
   
      @Override
      public void run() {
        try {
          while (true) {
               
              // thinking
              doSomething("Thinking");
              synchronized (Chopstick_L) {
                  doSomething("Picked up left fork");
                  synchronized (Chopstick_R) {
                      // eating
                      doSomething("Picked up right fork - eating"); 
                      doSomething("Put down right fork");
                  }
                   
                  // Back to thinking
                  doSomething("Put down left fork. Back to thinking");
              }
          }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
          }
  
      }
   
    }

  }

}
