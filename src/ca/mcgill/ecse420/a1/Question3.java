package ca.mcgill.ecse420.a1;

public class Question3 {

  public static void main(String[] args) throws Exception{
    
    class Philosopher implements Runnable {
      
      // The forks on either side of this Philosopher 
      private Object Chopstick_L;
      private Object Chopstick_R;
      
     //Every philosopher starts with a right and left chopstick as soon as they sit down
      public Philosopher(Object Chopstick_L, Object Chopstick_R) {
          this.Chopstick_L = Chopstick_L;
          this.Chopstick_R = Chopstick_R;
      }
      
      //This method is ran whenever an action is performed in order for us to follow the philosopher's ressource allocation
      private void doSomething(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
      }
   
      
      //In the run method, we start by making each philosopher pick up the left chopstick and then the right, at which point they are now eating
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
    
    //We start by creating an array of philosophers and chopsticks
    Philosopher[] philosophers = new Philosopher[5];
    Object[] chopsticks = new Object[philosophers.length];

    for (int i = 0; i < chopsticks.length; i++) {
        chopsticks[i] = new Object();
    }

    //We then construct each philosopher within the array and start separate threads for each of them
    for (int i = 0; i < philosophers.length; i++) {
        Object leftFork = chopsticks[i];
        Object rightFork = chopsticks[(i + 1) % chopsticks.length];

        philosophers[i] = new Philosopher(leftFork, rightFork);
         
        Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
        t.start();
    }
  

  }

}
