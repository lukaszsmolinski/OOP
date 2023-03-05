package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class AntiDiagonalMatrix extends BaseDoubleMatrix {

  private final double[] antiDiagonal; // antiDiagonal[i] is value in i-th column

  public AntiDiagonalMatrix(double[] antiDiagonal) {
    assert antiDiagonal != null && antiDiagonal.length > 0;
    this.antiDiagonal = Arrays.copyOf(antiDiagonal, antiDiagonal.length);
  }

  private IDoubleMatrix multiplyByAntiDiagonal(AntiDiagonalMatrix other) {
    double[] diagonal = new double[antiDiagonal.length];
    for (int i = 0; i < antiDiagonal.length; i++) {
      diagonal[i] = antiDiagonal[antiDiagonal.length - 1 - i] * other.antiDiagonal[i];
    }
    return new DiagonalMatrix(diagonal);
  }

  @Override
  public IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    return getClass() == other.getClass() ?
            multiplyByAntiDiagonal((AntiDiagonalMatrix) other) : super.times(other);
  }

  @Override
  public IDoubleMatrix multiplyBy(double scalar) {
    double[] newAntiDiagonal = new double[antiDiagonal.length];
    for (int i = 0; i < antiDiagonal.length; i++) {
      newAntiDiagonal[i] = scalar * antiDiagonal[i];
    }

    return new AntiDiagonalMatrix(newAntiDiagonal);
  }

  private IDoubleMatrix addAntiDiagonal(AntiDiagonalMatrix other, int sign) {
    double[] newAntiDiagonal = new double[antiDiagonal.length];
    for (int i = 0; i < antiDiagonal.length; i++) {
      newAntiDiagonal[i] = antiDiagonal[i] + sign * other.antiDiagonal[i];
    }
    return new AntiDiagonalMatrix(newAntiDiagonal);
  }

  @Override
  public IDoubleMatrix add(IDoubleMatrix other, int sign) {
    return getClass() == other.getClass() ?
            addAntiDiagonal((AntiDiagonalMatrix) other, sign) : super.add(other, sign);
  }

  @Override
  public double valueAt(int row, int column) {
    return row == antiDiagonal.length - column - 1 ? antiDiagonal[column] : 0;
  }

  @Override
  public double[][] data() {
    double[][] matrix = new double[antiDiagonal.length][antiDiagonal.length];
    for (int i = 0; i < antiDiagonal.length; i++) {
      matrix[antiDiagonal.length - i - 1][i] = antiDiagonal[i];
    }
    return matrix;
  }

  @Override
  public double calculateNormOne() {
    double result = 0;
    for (double v : antiDiagonal) {
      result = Math.max(Math.abs(v), result);
    }
    return result;
  }

  @Override
  public double calculateNormInfinity() {
    return calculateNormOne();
  }

  @Override
  public double calculateFrobeniusNorm() {
    double result = Arrays.stream(antiDiagonal).map(v -> v * v).sum();
    return Math.sqrt(result);
  }

  @Override
  public String contentToString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < antiDiagonal.length; i++) {
      appendZeros(sb, antiDiagonal.length - 1 - i);
      sb.append(antiDiagonal[antiDiagonal.length - 1 - i]).append(" ");
      appendZeros(sb, i);
      sb.append('\n');
    }
    return sb.toString();
  }

  @Override
  public Shape shape() {
    return Shape.matrix(antiDiagonal.length, antiDiagonal.length);
  }
}
