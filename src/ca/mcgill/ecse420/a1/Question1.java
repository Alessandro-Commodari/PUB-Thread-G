package ca.mcgill.ecse420.a1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Question1 {
  // static variables used by the parallel matrix multiplication (shared resources)
  public static double[][] parMatrixA;
  public static double[][] parMatrixB;

  public static double[][] parMatrixResult;
  public static int numOfThreads = 8;

  // this class extends Runnable and will be used to run the parallel multiplication
  static class MatrixMultiplication implements Runnable {
    private int begin;
    private int end;

    public MatrixMultiplication(int begin, int end) {
      this.begin = begin;
      this.end = end;
    }

    @Override
    public void run() {
      for (int i = begin; i < end; i++) {
        for (int j = 0; j < parMatrixB[0].length; j++) {
          for (int k = 0; k < parMatrixA[0].length; k++) {
            parMatrixResult[i][j] += (parMatrixA[i][k] * parMatrixB[k][j]);
          }
        }
      }
    }
  }

  // this method takes in an int for row size and one for column and fills a 2D double array of that
  // size with random values.
  public static double[][] createRandomMatrix(int rowSize, int colSize) {
    double[][] matrix = new double[rowSize][colSize];

    Random randNum = new Random();
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < colSize; j++) {
        // make sure the numbers are type double and between 1 and 9 without decimals for simplicity
        matrix[i][j] = Math.ceil(randNum.nextDouble() * 9);
      }
    }
    return matrix;
  }

  // this method takes in two matrices and performs matrix multiplication on them sequentally
  public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
    int rowA = a.length;
    int colA = a[0].length;
    int rowB = b.length;
    int colB = b[0].length;

    double[][] matrix = new double[rowA][colB];

    // throw an error if the two matrices cannot be multiplied with each other
    if (colA != rowB) {
      throw new IllegalArgumentException(
          "ERROR: Matrix A's column length must be equal to matrix B's row length.");
    }

    for (int i = 0; i < rowA; i++) {
      for (int j = 0; j < colB; j++) {
        for (int k = 0; k < colA; k++) {
          matrix[i][j] += (a[i][k] * b[k][j]);
        }
      }
    }

    return matrix;
  }

  /*
   * this method takes in two matrices and performs matrix multiplication on them using multiple
   * threads and returns the modified static variable parMatrixResult.
   */

  public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
    int rowA = a.length;
    int colA = a[0].length;
    int rowB = b.length;
    int colB = b[0].length;

    // throw an error if the two matrices cannot be multiplied with each other
    if (colA != rowB) {
      throw new IllegalArgumentException(
          "ERROR: Matrix A's column length must be equal to matrix B's row length.");
    }

    /*
     * executor creates a threadpool with numOfThreads threads and each thread in this pool will
     * perform a multiplication for the row associated to it. These different multiplications act on
     * the static variable parMatrixResult.
     */
    parMatrixResult = new double[rowA][colB];
    ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
    for (int i = 0; i < numOfThreads; i++) {
      if (rowA % numOfThreads != 0 && i == numOfThreads - 1) {
        executor.execute(new MatrixMultiplication(i * (rowA / numOfThreads), rowA));
      } else {
        executor.execute(
            new MatrixMultiplication(i * (rowA / numOfThreads), (i + 1) * (rowA / numOfThreads)));
      }
    }
    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {

    }

    return parMatrixResult;
  }

  // this method goes through the given matrix and outputs it
  public static void printMatrix(double[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      System.out.print("[");
      for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.print("]");
      System.out.println();
    }
  }

  public static void main(String args[]) {
    /* PART 1 and PART 2: This section of code will execute the code for both part 1 and part 2. */
    double[][] matrixA = createRandomMatrix(3, 3);
    double[][] matrixB = createRandomMatrix(3, 3);
    parMatrixA = createRandomMatrix(3, 3);
    parMatrixB = createRandomMatrix(3, 3);
    // results for part 1 and 2 respectively
    System.out.println("Q1 Part 1 and 2");
    System.out.println("Sequential Matrix A: ");
    printMatrix(matrixA);
    System.out.println("Sequential Matrix B: ");
    printMatrix(matrixB);
    System.out.println("======");
    System.out.println("Sequential Result: ");
    printMatrix(sequentialMultiplyMatrix(matrixA, matrixB));
    System.out.println("\n/****************/\n");
    System.out.println("Parallel Matrix A: ");
    printMatrix(parMatrixA);
    System.out.println("Parallel Matrix B: ");
    printMatrix(parMatrixB);
    System.out.println("======");
    System.out.println("Parallel Result: ");
    printMatrix(parallelMultiplyMatrix(parMatrixA, parMatrixB));

    /* PART 3: the code below is for part 3 of question 1 */
    parMatrixA = createRandomMatrix(2000, 2000);
    parMatrixB = createRandomMatrix(2000, 2000);

    // sequential Multiplication
    long seqTimeStart = System.nanoTime();
    sequentialMultiplyMatrix(parMatrixA, parMatrixB);
    long seqTimeEnd = System.nanoTime();

    // parallel Multiplication
    long parTimeStart = System.nanoTime();
    parallelMultiplyMatrix(parMatrixA, parMatrixB);
    long parTimeEnd = System.nanoTime();

    System.out.println("Q1 Part 3");
    System.out.println(
        "Sequential Multiplication: " + (seqTimeEnd - seqTimeStart) / 1000000 + " milliseconds");
    System.out.println(
        "Parallel Multiplication: " + (parTimeEnd - parTimeStart) / 1000000 + " milliseconds");

  }
}
