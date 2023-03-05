package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values) {
    assert shape != null;
    return new SparseMatrix(shape.rows, shape.columns, values);
  }

  public static IDoubleMatrix full(double[][] values) {
    return new FullMatrix(values);
  }

  public static IDoubleMatrix identity(int size) {
    return new IdentityMatrix(size);
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    return new DiagonalMatrix(diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    return new AntiDiagonalMatrix(antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values) {
    assert values != null && values.length > 0;
    double[][] arr = new double[values.length][1];
    for (int i = 0; i < values.length; i++) {
      arr[i][0] = values[i];
    }
    return new FullMatrix(arr);
  }

  public static IDoubleMatrix zero(Shape shape) {
    assert shape != null;
    return new ZeroMatrix(shape.rows, shape.columns);
  }

  public static IDoubleMatrix row(int rows, double... columnsValues) {
    return new RowMatrix(rows, columnsValues);
  }

  public static IDoubleMatrix column(int columns, double... rowsValues) {
    return new ColumnMatrix(rowsValues, columns);
  }

  public static IDoubleMatrix constant(Shape shape, double value) {
    assert shape != null;
    return new ConstantMatrix(shape.rows, shape.columns, value);
  }
}
