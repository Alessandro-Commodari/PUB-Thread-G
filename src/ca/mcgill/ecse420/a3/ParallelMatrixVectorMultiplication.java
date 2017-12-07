package ca.mcgill.ecse420.a3;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelMatrixVectorMultiplication {
  static ExecutorService exec = Executors.newCachedThreadPool();

  public static double[][] add(double[][] a, double[][] b)
      throws ExecutionException, InterruptedException {
    double[][] c = new double[a.length][a[0].length];
    Future<?> future = exec.submit(new AddTask(a, b, c));
    future.get();
    return c;
  }

  public static double[][] multiply(double[][] a, double[][] b)
      throws ExecutionException, InterruptedException {
    double[][] c = new double[a.length][a[0].length];
    Future<?> future = exec.submit(new MulTask(a, b, c));
    future.get();
    return c;
  }

  static class AddTask implements Runnable {
    double[][] a, b, c;

    public AddTask(double[][] myA, double[][] myB, double[][] myC) {
      a = myA;
      b = myB;
      c = myC;
    }

    public void run() {
      try {
        int n = a.getDim();

        if (n == 1) {
          c.set(a.get() + b.get());
        } else {

          Matrix[][] aa = a.split(), bb = b.split(), cc = c.split();
          Future<?>[][] future = (Future<?>[][]) new Future[2][2];

          for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
              future[i][j] = exec.submit(new AddTask(aa[i][j], bb[i][j], cc[i][j]));
            }
          }
          for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
              future[i][j].get();
            }
          }

        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  static class MulTask implements Runnable {
    double[][] a, b, c, lhs, rhs;

    public MulTask(double[][] myA, double[][] myB, double[][] myC) {
      a = myA;
      b = myB;
      c = myC;
      lhs = new double[a.length][a[0].length];
      rhs = new double[a.length][a[0].length];
    }

    public void run() {
      try {
        if (a.getDim() == 1) {
          c.set(a.get() * b.get());

        } else {

          Matrix[][] aa = a.split(), bb = b.split();
          Matrix[][] ll = lhs.split(), rr = rhs.split();
          Future<?>[][][] future = (Future<?>[][][]) new Future[2][2][2];

          for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
              future[i][j][0] = exec.submit(new MulTask(aa[j][0], bb[0][i], ll[j][i]));
              future[i][j][1] = exec.submit(new MulTask(aa[j][1], bb[1][i], rr[j][i]));
            }
          }

          for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
              for (int k = 0; k < 2; k++)
                future[i][j][k].get();

          Future<?> done = exec.submit(new AddTask(lhs, rhs, c));
          done.get();
        }


      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
