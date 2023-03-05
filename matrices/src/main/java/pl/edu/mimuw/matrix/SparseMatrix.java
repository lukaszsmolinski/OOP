package pl.edu.mimuw.matrix;

import java.util.*;

import static pl.edu.mimuw.matrix.MatrixCellValue.*;

public class SparseMatrix extends BaseDoubleMatrix {

  private final int rows;
  private final int columns;
  private final ArrayList<ArrayList<MatrixCellValue>> rowsValues;

  public SparseMatrix(int rows, int columns, MatrixCellValue[] values_) {
    assert rows > 0 && columns > 0 && values_ != null;

    this.rows = rows;
    this.columns = columns;
    this.rowsValues = new ArrayList<>();

    MatrixCellValue[] values = Arrays.copyOf(values_, values_.length);
    Arrays.sort(values, ROWS_COMPARATOR);

    for (int i = 0; i < values.length; i++) {
      assert values[i].row >= 0 && values[i].row < rows;
      assert values[i].column >= 0 && values[i].column < columns;
      // check if array contains duplicates
      assert i == 0 || ROWS_COMPARATOR.compare(values[i], values[i - 1]) != 0;
    }

    for (int i = 0; i < values.length; i++) {
      if (i == 0 || values[i - 1].row != values[i].row) {
        rowsValues.add(new ArrayList<>());
      }
      rowsValues.get(rowsValues.size() - 1).add(values[i]);
    }
  }

  private SparseMatrix(int rows, int columns, ArrayList<ArrayList<MatrixCellValue>> rowsValues) {
    this.rows = rows;
    this.columns = columns;
    this.rowsValues = rowsValues;
  }

  private IDoubleMatrix addSparse(SparseMatrix other, int sign) {
    ArrayList<MatrixCellValue> newValues = new ArrayList<>();
    ArrayList<MatrixCellValue> values1 = flatten(rowsValues),
                               values2 = flatten(other.rowsValues);

    int i = 0, j = 0, c;
    while (i < values1.size() || j < values2.size()) {
      if (i == values1.size()) c = 1;
      else if (j == values2.size()) c = -1;
      else c = ROWS_COMPARATOR.compare(values1.get(i), values2.get(j));

      if (c < 0) {
        // get element from the first list
        newValues.add(values1.get(i++));
      } else if (c > 0) {
        // get element from the second list
        MatrixCellValue v = values2.get(j++);
        newValues.add(sign == 1 ? v : cell(v.row, v.column, sign * v.value));
      } else {
        // merge elements from both lists
        MatrixCellValue v1 = values1.get(i++), v2 = values2.get(j++);
        newValues.add(cell(v1.row, v1.column, v1.value + sign * v2.value));
      }
    }

    return new SparseMatrix(rows, columns, newValues.toArray(new MatrixCellValue[0]));
  }

  private IDoubleMatrix multiplyBySparse(SparseMatrix other) {
    ArrayList<ArrayList<MatrixCellValue>> newRowsValues = new ArrayList<>();

    for (ArrayList<MatrixCellValue> row : rowsValues) {
      int i = row.get(0).row;
      newRowsValues.add(new ArrayList<>());
      for (int j = 0; j < other.columns; j++) {
        // calculating value in i-th row and j-th column of matrix
        double newValue = 0;
        for (MatrixCellValue v : row) {
          newValue += v.value * other.get(v.column, j);
        }

        if (newValue != 0) {
          newRowsValues.get(newRowsValues.size() - 1).add(cell(i, j, newValue));
        }
      }

      if (newRowsValues.get(newRowsValues.size() - 1).size() == 0) {
        // no element was added to current row, so we can remove it
        newRowsValues.remove(newRowsValues.size() - 1);
      }
    }

    return new SparseMatrix(rows, other.columns, newRowsValues);
  }

  @Override
  public IDoubleMatrix add(IDoubleMatrix other, int sign) {
    return getClass() == other.getClass() ?
            addSparse((SparseMatrix) other, sign) : super.add(other, sign);
  }

  @Override
  public IDoubleMatrix multiplyBy(double scalar) {
    ArrayList<ArrayList<MatrixCellValue>> newValues = new ArrayList<>();

    for (ArrayList<MatrixCellValue> row : rowsValues) {
      ArrayList<MatrixCellValue> newRow = new ArrayList<>();
      for (MatrixCellValue v : row) {
        newRow.add(cell(v.row, v.column, v.value * scalar));
      }
      newValues.add(newRow);
    }

    return new SparseMatrix(rows, columns, newValues);
  }

  @Override
  public IDoubleMatrix multiplyBy(IDoubleMatrix other) {
    return getClass() == other.getClass() ?
            multiplyBySparse((SparseMatrix) other) : super.multiplyBy(other);
  }

  @Override
  public double valueAt(int row, int column) {
    MatrixCellValue columnKey = cell(row, column, 0);
    ArrayList<MatrixCellValue> rowKey = new ArrayList<>(List.of(columnKey));

    // finding row index
    int i = Collections.binarySearch(rowsValues, rowKey,
            Comparator.comparingInt(a -> a.get(0).row));
    if (i < 0) return 0;

    // finding column index
    int j = Collections.binarySearch(rowsValues.get(i),
            columnKey, Comparator.comparingInt(a -> a.column));
    if (j < 0) return 0;

    return rowsValues.get(i).get(j).value;
  }

  @Override
  public double[][] data() {
    double[][] result = new double[rows][columns];
    for (ArrayList<MatrixCellValue> row : rowsValues) {
      for (MatrixCellValue v : row) {
        result[v.row][v.column] = v.value;
      }
    }
    return result;
  }

  @Override
  public double calculateNormOne() {
    ArrayList<MatrixCellValue> values = flatten(rowsValues);
    values.sort(COLUMNS_COMPARATOR);

    double result = 0, sum = 0;
    for (int i = 0; i < values.size(); i++) {
      if (i > 0 && values.get(i).column != values.get(i - 1).column) {
        result = Math.max(result, sum);
        sum = 0;
      }
      sum += Math.abs(values.get(i).value);
    }
    return Math.max(result, sum);
  }

  @Override
  public double calculateNormInfinity() {
    double result = 0;
    for (ArrayList<MatrixCellValue> row : rowsValues) {
      double sum = row.stream().mapToDouble(v -> Math.abs(v.value)).sum();
      result = Math.max(result, sum);
    }
    return result;
  }

  @Override
  public double calculateFrobeniusNorm() {
    double result = 0;
    for (ArrayList<MatrixCellValue> row : rowsValues) {
      result += row.stream().mapToDouble(v -> v.value * v.value).sum();
    }
    return Math.sqrt(result);
  }

  @Override
  public String contentToString() {
    StringBuilder sb = new StringBuilder();
    int j = 0;
    for (int i = 0; i < rows; i++) {
      int lastAdded = 0;
      // check if j-th element in rowsValues represents i-th row
      if (j < rowsValues.size() && rowsValues.get(j).get(0).row == i) {
        for (MatrixCellValue value : rowsValues.get(j)) {
          appendZeros(sb, value.column - lastAdded);
          sb.append(value.value).append(" ");
          lastAdded = value.column + 1;
        }
        j++;
      }
      appendZeros(sb, columns - lastAdded);
      sb.append('\n');
    }

    return sb.toString();
  }

  @Override
  public Shape shape() {
    return Shape.matrix(rows, columns);
  }

  // Transforms 2d ArrayList to 1d ArrayList.
  private static ArrayList<MatrixCellValue> flatten(ArrayList<ArrayList<MatrixCellValue>> arr) {
    ArrayList<MatrixCellValue> result = new ArrayList<>();
    for (ArrayList<MatrixCellValue> a : arr) {
      result.addAll(a);
    }
    return result;
  }
}
