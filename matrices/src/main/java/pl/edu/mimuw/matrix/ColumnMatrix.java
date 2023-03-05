package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class ColumnMatrix extends BaseDoubleMatrix {

  private final double[] rowsValues;
  private final int columns;

  public ColumnMatrix(double[] rowsValues, int columns) {
    assert rowsValues != null && rowsValues.length > 0 && columns > 0;
    this.rowsValues = Arrays.copyOf(rowsValues, rowsValues.length);
    this.columns = columns;
  }

  @Override
  public double valueAt(int row, int column) {
    return rowsValues[row];
  }

  @Override
  public double calculateNormOne() {
    return Arrays.stream(rowsValues).map(Math::abs).sum();
  }

  @Override
  public double calculateNormInfinity() {
    double result = 0;
    for (double value : rowsValues) {
      result = Math.max(Math.abs(value) * columns, result);
    }
    return result;
  }

  @Override
  public double calculateFrobeniusNorm() {
    double result = Arrays.stream(rowsValues).map(v -> columns * v * v).sum();
    return Math.sqrt(result);
  }

  @Override
  public String contentToString() {
    if (columns < 3) {
      return super.contentToString();
    }

    StringBuilder sb = new StringBuilder();
    for (double value : rowsValues) {
      sb.append(value).append(" ... ").append(value).append('\n');
    }
    return sb.toString();
  }

  @Override
  public Shape shape() {
    return Shape.matrix(rowsValues.length, columns);
  }
}
