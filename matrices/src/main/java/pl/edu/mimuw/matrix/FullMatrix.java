package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class FullMatrix extends BaseDoubleMatrix {

  private final double[][] values;

  public FullMatrix(double[][] values) {
    assert values != null && values.length > 0 && values[0].length > 0;

    this.values = new double[values.length][];
    for (int i = 0; i < values.length; i++) {
      assert i == 0 || values[i].length == values[i - 1].length;
      this.values[i] = Arrays.copyOf(values[i], values[i].length);
    }
  }

  @Override
  public double valueAt(int row, int column) {
    return values[row][column];
  }

  @Override
  public Shape shape() {
    return Shape.matrix(values.length, values[0].length);
  }
}