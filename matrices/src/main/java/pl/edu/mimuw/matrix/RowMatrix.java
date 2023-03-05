package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class RowMatrix extends BaseDoubleMatrix {

  private final int rows;
  private final double[] columnsValues;

  public RowMatrix(int rows, double[] columnsValues) {
    assert columnsValues != null && columnsValues.length > 0 && rows > 0;
    this.rows = rows;
    this.columnsValues = Arrays.copyOf(columnsValues, columnsValues.length);
  }

  @Override
  public double valueAt(int row, int column) {
    return columnsValues[column];
  }

  @Override
  public double calculateNormOne() {
    double result = 0;
    for (double value : columnsValues) {
      result = Math.max(rows * Math.abs(value), result);
    }
    return result;
  }

  @Override
  public double calculateNormInfinity() {
    return Arrays.stream(columnsValues).map(Math::abs).sum();
  }

  @Override
  public double calculateFrobeniusNorm() {
    double result = rows * Arrays.stream(columnsValues).map(v -> v * v).sum();
    return Math.sqrt(result);
  }

  @Override
  public Shape shape() {
    return Shape.matrix(rows, columnsValues.length);
  }
}
