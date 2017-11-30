package ca.mcgill.ecse420.a3;

public class ContainsTest {

  public static void main(String[] args) {

    FineGrainedList myList = new FineGrainedList();

    // Synchronous Test
    for (int i = 0; i < 10; i++) {
      if (i % 2 == 0) {
        myList.add(i);
      }
    }


    for (int i = 0; i < 10; i++) {
      System.out.print(i + "\n");
      System.out.print(myList.contains(i) + "\n");
    }
  }

  // NEED TO ADD MULTITHREADED TEST
  // TODO
}
