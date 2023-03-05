package pl.edu.mimuw.matrix;

import java.util.Comparator;

public final class MatrixCellValue {

  // compares row and then column
  public final static Comparator<MatrixCellValue> ROWS_COMPARATOR =
          (v1, v2) -> v1.row != v2.row ? v1.row - v2.row : v1.column - v2.column;
  // compares column and then row
  public final static Comparator<MatrixCellValue> COLUMNS_COMPARATOR =
          (v1, v2) -> v1.column != v2.column ? v1.column - v2.column : v1.row - v2.row;

  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }
}
