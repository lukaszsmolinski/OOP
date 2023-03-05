package pl.edu.mimuw;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.*;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.MatrixCellValue;
import pl.edu.mimuw.matrix.Shape;

import java.util.stream.IntStream;

public class Main {

  private final static int ROWS = 10;
  private final static int COLUMNS = 10;
  private final static Shape SHAPE = Shape.matrix(ROWS, COLUMNS);

  public static void main(String[] args) {

    MatrixCellValue[] valuesSparse = new MatrixCellValue[]{
            new MatrixCellValue(0, 0, 1),
            new MatrixCellValue(2, 3, 4),
            new MatrixCellValue(4, 5, 6),
            new MatrixCellValue(7, 8, 9)
    };
    double[][] valuesFull = new double[][]{
            {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9},
            {1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9},
            {2.0, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9},
            {3.0, 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 3.7, 3.8, 3.9},
            {4.0, 4.1, 4.2, 4.3, 4.4, 4.5, 4.6, 4.7, 4.8, 4.9},
            {5.0, 5.1, 5.2, 5.3, 5.4, 5.5, 5.6, 5.7, 5.8, 5.9},
            {6.0, 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9},
            {7.0, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9},
            {8.0, 8.1, 8.2, 8.3, 8.4, 8.5, 8.6, 8.7, 8.8, 8.9},
            {9.0, 9.1, 9.2, 9.3, 9.4, 9.5, 9.6, 9.7, 9.8, 9.9},
    };
    double[] values = IntStream.range(1, 11).asDoubleStream().toArray();

    IDoubleMatrix[] matrices = new IDoubleMatrix[] {
            sparse(SHAPE, valuesSparse),
            full(valuesFull),
            identity(ROWS),
            diagonal(values),
            antiDiagonal(values),
            vector(values),
            zero(SHAPE),
            row(ROWS, values),
            column(COLUMNS, values),
            constant(SHAPE, 12.3)
    };
    String[] matricesNames = new String[] {
            "Sparse matrix",
            "Full matrix",
            "Identity matrix",
            "Diagonal matrix",
            "Anti-diagonal matrix",
            "Vector",
            "Zero matrix",
            "Row matrix",
            "Column matrix",
            "Constant matrix"
    };

    for (int i = 0; i < matrices.length; i++) {
      System.out.println(matricesNames[i] + ":");
      System.out.println(matrices[i]);
    }
  }
}
