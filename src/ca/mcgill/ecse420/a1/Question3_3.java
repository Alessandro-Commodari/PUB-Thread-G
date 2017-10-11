package ca.mcgill.ecse420.a1;

import java.util.concurrent.Semaphore;

public class Question3_3 {

  // We start by creating an array of philosophers and a semaphore representing chopsticks

  static int numOfPhilo = 20;;

  static Philosopher[] philosophers = new Philosopher[numOfPhilo];

  static Semaphore chopstick = new Semaphore(1);


  // Philosopher class
  static class Philosopher extends Thread {

    // Each philosopher has an id, a state and a semaphore
    private int pid;
    private Semaphore me;
    private StateType state;

    // create an enum variable to keep track of each philosopher's state
    private enum StateType {
      THINK, EAT, HUNGRY
    };

    // Every philosopher starts with an id, a semaphore and in the think state
    public Philosopher(int pid) {
      this.pid = pid;
      me = new Semaphore(0);
      state = StateType.THINK;
    }


    // method that will be used to check the state of the philosopher on my left
    private Philosopher getLeft() {
      if (philosophers[pid].pid == 0) {
        return philosophers[numOfPhilo - 1];
      } else {
        return philosophers[pid - 1];
      }
    }

    // method that will be used to check the state of the philosopher on my right
    private Philosopher getRight() {
      return philosophers[(pid + 1) % numOfPhilo];
    }

    /*
     * The run method this time will be controlled by a switch statement. This statement verifies
     * the state of the philosopher at performs certain actions accordingly whilst updating the
     * state.
     */
    @Override
    public void run() {
      try {
        while (true) {
          doSomething();
          switch (state) {
            /*
             * The starting state of all philosophers. Philosophers will stay in this state for up
             * to 3 seconds and then attempt to acquire a chopstick. Their state will then be
             * switched to HUNGRY.
             */
            case THINK:
              notHungry();
              chopstick.acquire();
              state = StateType.HUNGRY;
              break;
            /*
             * This state is only reached from the HUNGRY state. In this state, the philosopher will
             * spend between 0-3 seconds eating. Once that's done, the philosopher will switch to
             * the THINK state and attempt to alert his left and right neighbors that he's done
             * eating. If either of those neighbors isn't currently eating and is currently Hungry,
             * then it will make them start eating too. Finally it releases the chopstick.
             */
            case EAT:
              notHungry();
              chopstick.acquire();
              state = StateType.THINK;
              checkPhilo(getLeft());
              checkPhilo(getRight());
              chopstick.release();
              break;
            /*
             * This state is reached from the THINK state. In this state, the philosopher will first
             * check that his neighbors aren't currently eating. If they aren't, he is switched to
             * the EAT state. If they are, he acquires a lock on being the next one to eat and is
             * switched to the EAT state as soon as he gets it.
             */
            case HUNGRY:
              checkPhilo(this);
              chopstick.release();
              me.acquire();
              state = StateType.EAT;
              break;
          }
        }
      } catch (InterruptedException e) {
      }
    }


    // allows us to see the state of the philosophers
    private void doSomething() {
      System.out.println("Philosopher " + pid + " is in the " + state + " state");
    }

    // method that is run when the philosophers aren't in the HUNGRY state
    private void notHungry() {
      try {
        Thread.sleep((long) Math.round(Math.random() * 3000));
      } catch (InterruptedException e) {
      }
    }

    // method to check the state of a philosophers neighbors and update his state accordingly
    static private void checkPhilo(Philosopher p) {
      if (p.getLeft().state != StateType.EAT && p.state == StateType.HUNGRY
          && p.getRight().state != StateType.EAT) {
        p.state = StateType.EAT;
        p.me.release();
      }
    }
  }

  public static void main(String[] args) {


    philosophers[0] = new Philosopher(0);
    for (int i = 1; i < numOfPhilo; i++) {
      philosophers[i] = new Philosopher(i);
    }

    for (Thread t : philosophers) {
      t.start();
    }
  }
}
