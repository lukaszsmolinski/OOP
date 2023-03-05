package pl.edu.mimuw.matrix;

public class ZeroMatrix extends ConstantMatrix {

  public ZeroMatrix(int rows, int columns) {
    super(rows, columns, 0);
  }

  @Override
  public IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    return new ZeroMatrix(rows, other.shape().columns);
  }

  @Override
  public IDoubleMatrix multiplyBy(double scalar) {
    return this;
  }

  @Override
  public IDoubleMatrix add(IDoubleMatrix other, int sign) {
    return sign == 1 ? other : other.times(-1);
  }

  @Override
  public double[][] data() {
    return new double[rows][columns];
  }
}
