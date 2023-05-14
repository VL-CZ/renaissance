package org.renaissance.matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SquareMatrix {
    private final int[][] data;

    public SquareMatrix(int dimension) {
        this.data = new int[dimension][dimension];
    }

    public SquareMatrix(int[][] data) {
        if (data.length > 0 && data.length != data[0].length) {
            throw new IllegalArgumentException("Matrix isn't square");
        }

        this.data = data;
    }

    public int getValue(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public void setValue(int rowIndex, int columnIndex, int value) {
        data[rowIndex][columnIndex] = value;
    }

    public int getDimension() {
        return data.length;
    }

    /**
     * Multiply this matrix with another one and return the result
     * 
     * @param other
     * @return
     */
    public SquareMatrix multiplyWith(SquareMatrix other) {
        int dim = getDimension();

        if (dim != other.getDimension()) {
            throw new IllegalArgumentException("Dimensions don't match");
        }

        SquareMatrix result = new SquareMatrix(dim);

        for (int rowIndex = 0; rowIndex < dim; rowIndex++) {
            for (int columnIndex = 0; columnIndex < dim; columnIndex++) {
                for (int i = 0; i < dim; i++) {
                    result.data[rowIndex][columnIndex] += getValue(rowIndex, i) * other.getValue(i, columnIndex);
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int[] row : data) {
            String rowString = Arrays.stream(row).mapToObj(String::valueOf).collect(Collectors.joining(" "));
            sb.append(rowString);
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SquareMatrix that = (SquareMatrix) o;
        return Arrays.deepEquals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }

    /**
     * Load matrix data from the given file
     * 
     * @param fileName file contaning matrix data (values in a row are separated by
     *                 spaces)
     * @return
     * @throws IOException
     */
    public static SquareMatrix loadFrom(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String[] values = lines.get(0).split(" ");
        SquareMatrix result = new SquareMatrix(values.length);

        for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
            String[] rowValues = lines.get(rowIndex).split(" ");

            for (int columnIndex = 0; columnIndex < rowValues.length; columnIndex++) {
                int value = Integer.parseInt(rowValues[columnIndex]);
                result.setValue(rowIndex, columnIndex, value);
            }
        }

        return result;
    }
}
