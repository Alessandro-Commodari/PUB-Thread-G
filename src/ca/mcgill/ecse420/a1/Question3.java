package ca.mcgill.ecse420.a1;

public class Question3 {

  static class Philosopher implements Runnable {

    private Object Chopstick_L;
    private Object Chopstick_R;

    private int hasEaten;

    // Every philosopher starts with a right and left chopstick as soon as they sit down
    public Philosopher(Object Chopstick_L, Object Chopstick_R, int hasEaten) {
      this.Chopstick_L = Chopstick_L;
      this.Chopstick_R = Chopstick_R;
      this.hasEaten = hasEaten;
    }

    // This method is ran whenever an action is performed in order for us to follow the
    // philosopher's resource allocation
    private void doSomething(String action) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + " " + action);
      Thread.sleep(((int) (Math.random() * 100)));
    }

    // Only runs when philosopher is eating and increments his hasEaten
    private void eat() throws InterruptedException {
      this.hasEaten++;
      System.out.println("\n**************************\n" + Thread.currentThread().getName()
          + " is eating for the " + hasEaten + " time! \n**************************\n");
    }



    // In the run method, we start by making each philosopher pick up the left chopstick and then
    // the right, at which point they are now eating
    @Override
    public void run() {
      try {
        while (true) {

          // thinking
          doSomething("is thinking about his rice");
          synchronized (Chopstick_L) {
            doSomething("has picked up his left chopstick");
            synchronized (Chopstick_R) {
              // eating
              doSomething("has picked up his right chopstick");
              eat();
              doSomething("has put down his right chopstick and has stopped eating");
            }

            // Back to thinking
            doSomething("has put down his left chopstick and is now thinking about rice");
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }

    }

  }

  public static void main(String[] args) throws Exception {

    // We start by creating an array of philosophers and chopsticks

    // Modified for n number of philosophers

    int numOfPhilo = 20;

    Philosopher[] philosophers = new Philosopher[numOfPhilo];
    Object[] chopsticks = new Object[philosophers.length];

    for (int i = 0; i < chopsticks.length; i++) {
      chopsticks[i] = new Object();
    }

    // We then construct each philosopher within the array and start separate threads for each of
    // them
    for (int i = 0; i < philosophers.length; i++) {
      Object Chopstick_L = chopsticks[i];
      Object Chopstick_R = chopsticks[(i + 1) % chopsticks.length];

      // All philosophers except the last one pick up their left chopstick, the last one picks up
      // his right chopstick
      if (i == philosophers.length - 1) {
        philosophers[i] = new Philosopher(Chopstick_R, Chopstick_L, 0);
      } else {
        philosophers[i] = new Philosopher(Chopstick_L, Chopstick_R, 0);
      }

      Thread t = new Thread(philosophers[i], "Philosopher " + (i));
      t.start();
    }


  }

}
