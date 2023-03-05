package pl.edu.mimuw.matrix;

public class ConstantMatrix extends BaseDoubleMatrix {

  protected final int rows;
  protected final int columns;
  protected final double value;

  public ConstantMatrix(int rows, int columns, double value) {
    assert rows > 0 && columns > 0;
    this.rows = rows;
    this.columns = columns;
    this.value = value;
  }

  @Override
  public IDoubleMatrix multiplyBy(double scalar) {
    return new ConstantMatrix(rows, columns, value * scalar);
  }

  @Override
  public IDoubleMatrix add(double scalar) {
    return new ConstantMatrix(rows, columns, value + scalar);
  }

  @Override
  public double valueAt(int row, int column) {
    return value;
  }

  @Override
  public double calculateNormOne() {
    return rows * Math.abs(value);
  }

  @Override
  public double calculateNormInfinity() {
    return columns * Math.abs(value);
  }

  @Override
  public double calculateFrobeniusNorm() {
    return Math.sqrt(Math.abs(rows * columns * value * value));
  }

  @Override
  public String contentToString() {
    if (columns < 3) {
      return super.contentToString();
    }
    return (value + " ... " + value + '\n').repeat(rows);
  }

  @Override
  public Shape shape() {
    return Shape.matrix(rows, columns);
  }
}
