package pl.edu.mimuw.matrix;

import java.util.stream.DoubleStream;

public class IdentityMatrix extends DiagonalMatrix {

  public IdentityMatrix(int n) {
    super(DoubleStream.generate(() -> 1.).limit(Math.max(n, 0)).toArray());
  }

  @Override
  public IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    return other;
  }

  @Override
  public double calculateNormOne() {
    return 1;
  }

  @Override
  public double calculateNormInfinity() {
    return 1;
  }

  @Override
  public double calculateFrobeniusNorm() {
    return Math.sqrt(diagonal.length);
  }
}
