package ca.mcgill.ecse420.a1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Question1 {

  public static double[][] parMatrixA = createRandomMatrix(4, 4);
  public static double[][] parMatrixB = createRandomMatrix(4, 4);
  public static double[][] parMatrixResult = new double[4][4];
  public static int numOfThreads = 8;

  static class MatrixMultiplication implements Runnable {
    private int start;
    private int end;

    public MatrixMultiplication(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    public void run() {
      for (int i = start; i < end; i++) {
        for (int j = 0; j < parMatrixB[0].length; j++) {
          for (int k = 0; k < parMatrixA[0].length; k++) {
            parMatrixResult[i][j] += (parMatrixA[i][k] * parMatrixB[k][j]);
          }
        }
      }
    }
  }

  public static double[][] createRandomMatrix(int rowSize, int colSize) {
    double[][] matrix = new double[rowSize][colSize];

    Random randNum = new Random();
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < colSize; j++) {
        matrix[i][j] = Math.ceil(randNum.nextDouble() * 9);
      }
    }
    return matrix;
  }

  public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
    int rowA = a.length;
    int colA = a[0].length;
    int rowB = b.length;
    int colB = b[0].length;

    double[][] matrix = new double[rowA][colB];

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

  public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
    int rowA = a.length;
    int colA = a[0].length;
    int rowB = b.length;
    int colB = b[0].length;

    double[][] matrix = new double[rowA][colB];

    if (colA != rowB) {
      throw new IllegalArgumentException(
          "ERROR: Matrix A's column length must be equal to matrix B's row length.");
    }

    ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
    for (int i = 0; i < numOfThreads; i++) {
      if (4 % numOfThreads != 0 && i == numOfThreads - 1) {
        executor.execute(new MatrixMultiplication(i * (4 / numOfThreads), 4));
      } else {
        executor.execute(
            new MatrixMultiplication(i * (4 / numOfThreads), (i + 1) * (4 / numOfThreads)));
      }
    }
    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {

    }

    return matrix;
  }

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
    double[][] matrixA = createRandomMatrix(3, 3);
    double[][] matrixB = createRandomMatrix(3, 3);

    System.out.println("Matrix A: ");
    printMatrix(matrixA);
    System.out.println("Matrix B: ");
    printMatrix(matrixB);
    System.out.println("======");
    System.out.println("Result: ");
    printMatrix(sequentialMultiplyMatrix(matrixA, matrixB));
    System.out.println("\n/****************/\n");
    System.out.println("Matrix A: ");
    printMatrix(parMatrixA);
    System.out.println("Matrix B: ");
    printMatrix(parMatrixB);
    System.out.println("======");
    System.out.println("Result: ");
    printMatrix(parallelMultiplyMatrix(parMatrixA, parMatrixB));
  }
}
