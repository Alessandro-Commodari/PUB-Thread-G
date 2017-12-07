package ca.mcgill.ecse420.a3;

import java.util.Random;

public class MatrixMultiplicationTest {

  private static double matrixA[][];
  private static double matrixB[][];


  public static double[][] fillMatrix(int a, int b) {
    double result[][] = new double[a][b];

    for (int r = 0; r < a; r++) {
      for (int c = 0; c < b; c++) {
        Random random = new Random();
        result[r][c] = random.nextInt(5);
      }
    }

    return result;
  }

  public static void main(String[] args) {

    matrixA = fillMatrix(4, 4);
    matrixB = fillMatrix(4, 4);


  }
}
