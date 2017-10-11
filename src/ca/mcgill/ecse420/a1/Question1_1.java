package ca.mcgill.ecse420.a1;

import java.util.Random;

public class Question1_1 {

  public static double[][] createRandomMatrix(int rowSize, int colSize) {
    double[][] matrix = new double[rowSize][colSize];

    Random randNum = new Random();
    for (int i = 0; i < rowSize; i++) {
      for (int j = 0; j < colSize; j++) {
        matrix[i][j] = Math.round(randNum.nextDouble() * 10);
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
          matrix[i][j] = (a[i][k] * b[k][j]);
        }
      }
    }

    return matrix;
  }

  public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
    double[][] matrix = new double[0][0];

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
    double[][] matrixA = createRandomMatrix(5, 5);
    double[][] matrixB = createRandomMatrix(5, 5);

    System.out.println("Matrix A: ");
    printMatrix(matrixA);
    System.out.println("Matrix B: ");
    printMatrix(matrixB);
    System.out.println("======");
    System.out.println("Result: ");
    printMatrix(sequentialMultiplyMatrix(matrixA, matrixB));
    // printMatrix(parallelMultiplyMatrix(matrixA, matrixB));
  }
}
