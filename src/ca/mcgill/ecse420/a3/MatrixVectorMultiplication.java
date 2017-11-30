package ca.mcgill.ecse420.a3;

public class MatrixVectorMultiplication {

  public static double[] matrixVectorMultiply(double m[][], double v[]) {
    double output[] = new double[m.length];

    for (int i = 0; i < m.length; i++) {
      output[i] = 0;
      for (int j = 0; j < m[0].length; j++)
        output[i] += m[i][j] * v[j];
    }

    return output;
  }

  public static void main(String[] args) {
    double matrix[][] = {{2, 4, 6, 8}, {1, 3, 5, 7}, {2, 3, 6, 7}, {8, 9, 5, 4}};
    double vector[] = {1, 2, 3, 4};

    double out[] = matrixVectorMultiply(matrix, vector);

    System.out.print("[ ");
    for (double val : out) {
      System.out.print(val + " ");
    }
    System.out.print(" ]");
  }
}
