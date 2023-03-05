package pl.edu.mimuw.matrix;

import java.util.Arrays;

public class DiagonalMatrix extends BaseDoubleMatrix {

  protected final double[] diagonal;

  public DiagonalMatrix(double[] diagonal) {
    assert diagonal != null && diagonal.length > 0;
    this.diagonal = Arrays.copyOf(diagonal, diagonal.length);
  }

  private IDoubleMatrix multiplyByDiagonal(DiagonalMatrix other) {
    double[] newDiagonal = new double[diagonal.length];
    for (int i = 0; i < diagonal.length; i++) {
      newDiagonal[i] = diagonal[i] * other.diagonal[i];
    }
    return new DiagonalMatrix(newDiagonal);
  }

  @Override
  public IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    return getClass() == other.getClass() ?
            multiplyByDiagonal((DiagonalMatrix) other) : super.times(other);
  }

  @Override
  public IDoubleMatrix multiplyBy(double scalar) {
    double[] newDiagonal = new double[diagonal.length];
    for (int i = 0; i < diagonal.length; i++) {
      newDiagonal[i] = scalar * diagonal[i];
    }
    return new DiagonalMatrix(newDiagonal);
  }

  private IDoubleMatrix addDiagonal(DiagonalMatrix other, int sign) {
    double[] newDiagonal = new double[diagonal.length];
    for (int i = 0; i < diagonal.length; i++) {
      newDiagonal[i] = diagonal[i] + sign * other.diagonal[i];
    }
    return new DiagonalMatrix(newDiagonal);
  }

  @Override
  public IDoubleMatrix add(IDoubleMatrix other, int sign) {
    return getClass() == other.getClass() ?
            addDiagonal((DiagonalMatrix) other, sign) : super.add(other, sign);
  }

  @Override
  public double valueAt(int row, int column) {
    return row == column ? diagonal[row] : 0;
  }

  @Override
  public double[][] data() {
    double[][] matrix = new double[diagonal.length][diagonal.length];
    for (int i = 0; i < diagonal.length; i++) {
      matrix[i][i] = diagonal[i];
    }
    return matrix;
  }

  @Override
  public double calculateNormOne() {
    double result = 0;
    for (double v : diagonal) {
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
    double result = Arrays.stream(diagonal).map(v -> v * v).sum();
    return Math.sqrt(result);
  }

  @Override
  public String contentToString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < diagonal.length; i++) {
      appendZeros(sb, i);
      sb.append(diagonal[i]).append(" ");
      appendZeros(sb, diagonal.length - 1 - i);
      sb.append('\n');
    }

    return sb.toString();
  }

  @Override
  public Shape shape() {
    return Shape.matrix(diagonal.length, diagonal.length);
  }
}
