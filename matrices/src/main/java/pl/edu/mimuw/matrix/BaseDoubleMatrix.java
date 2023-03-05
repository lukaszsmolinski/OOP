package pl.edu.mimuw.matrix;

public abstract class BaseDoubleMatrix implements IDoubleMatrix {

  private Double normOne;
  private Double normInfinity;
  private Double frobeniusNorm;

  @Override
  public final IDoubleMatrix times(IDoubleMatrix other) {
    assert shape().columns == other.shape().rows;
    return multiplyBy(other);
  }

  @Override
  public final IDoubleMatrix times(double scalar) {
    return scalar == 0 ? new ZeroMatrix(shape().rows, shape().columns) : multiplyBy(scalar);
  }

  @Override
  public final IDoubleMatrix plus(IDoubleMatrix other) {
    assert shape().equals(other.shape());
    return add(other, 1);
  }

  @Override
  public final IDoubleMatrix plus(double scalar) {
    return add(scalar);
  }

  @Override
  public final IDoubleMatrix minus(IDoubleMatrix other) {
    assert shape().equals(other.shape());
    return add(other, -1);
  }

  @Override
  public final IDoubleMatrix minus(double scalar) {
    return add(-scalar);
  }

  @Override
  public final double get(int row, int column) {
    shape().assertInShape(row, column);
    return valueAt(row, column);
  }

  @Override
  public double[][] data() {
    int n = shape().rows, m = shape().columns;
    double[][] result = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        result[i][j] = get(i, j);
      }
    }
    return result;
  }

  @Override
  public final double normOne() {
    if (normOne == null) {
      normOne = calculateNormOne();
    }
    return normOne;
  }

  @Override
  public final double normInfinity() {
    if (normInfinity == null) {
      normInfinity = calculateNormInfinity();
    }
    return normInfinity;
  }

  @Override
  public final double frobeniusNorm() {
    if (frobeniusNorm == null) {
      frobeniusNorm = calculateFrobeniusNorm();
    }
    return frobeniusNorm;
  }

  @Override
  public final String toString() {
    return shapeToString() + contentToString();
  }

  @Override
  public abstract Shape shape();

  protected final String shapeToString() {
    return shape().rows + " rows x " + shape().columns + " columns\n";
  }

  public abstract double valueAt(int row, int column);

  protected String contentToString() {
    StringBuilder sb = new StringBuilder();
    int n = shape().rows, m = shape().columns;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        sb.append(get(i, j)).append(" ");
      }
      sb.append('\n');
    }
    return sb.toString();
  }

  protected IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    int n = shape().rows, m = other.shape().columns;
    double[][] values = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        for (int k = 0; k < shape().columns; k++) {
          values[i][j] += get(i, k) * other.get(k, j);
        }
      }
    }
    return new FullMatrix(values);
  }

  protected IDoubleMatrix multiplyBy(double scalar) {
    int n = shape().rows, m = shape().columns;
    double[][] values = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        values[i][j] = get(i, j) * scalar;
      }
    }
    return new FullMatrix(values);
  }

  protected IDoubleMatrix add(IDoubleMatrix other, int sign) {
    int n = shape().rows, m = shape().columns;
    double[][] values = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        values[i][j] = get(i, j) + sign * other.get(i, j);
      }
    }
    return new FullMatrix(values);
  }

  protected IDoubleMatrix add(double scalar) {
    int n = shape().rows, m = shape().columns;
    double[][] values = new double[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        values[i][j] = get(i, j) + scalar;
      }
    }
    return new FullMatrix(values);
  }

  protected double calculateNormOne() {
    double result = 0;
    for (int j = 0; j < shape().columns; j++) {
      int sum = 0;
      for (int i = 0; i < shape().rows; i++) {
        sum += Math.abs(get(i, j));
      }
      result = Math.max(result, sum);
    }
    return result;
  }

  protected double calculateNormInfinity() {
    double result = 0;
    for (int i = 0; i < shape().rows; i++) {
      int sum = 0;
      for (int j = 0; j < shape().columns; j++) {
        sum += Math.abs(get(i, j));
      }
      result = Math.max(result, sum);
    }
    return result;
  }

  protected double calculateFrobeniusNorm() {
    double result = 0;
    for (int i = 0; i < shape().rows; i++) {
      for (int j = 0; j < shape().columns; j++) {
        double a = get(i, j);
        result += a * a;
      }
    }
    return Math.sqrt(result);
  }

  protected static void appendZeros(StringBuilder sb, int n) {
    if (n >= 3) {
      sb.append("0.0 ... 0.0 ");
    } else {
      sb.append("0.0 ".repeat(n));
    }
  }
}
