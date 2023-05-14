package org.renaissance.matrix;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Assert;
import org.renaissance.matrix.SquareMatrix;
import org.renaissance.License;

import static org.renaissance.Benchmark.*;

import java.io.IOException;

@Name("matrix-multiplication")
@Group("matrix")
@Summary("Benchmark executing matrix multiplication")
@Licenses(License.MIT)
@Configuration(name = "test")
public final class MatrixMultiplication implements Benchmark {
  @Override
  public BenchmarkResult run(BenchmarkContext c) {

    try {
      String resourcesFolder = "benchmarks/matrix/src/main/resources/";
      String matrixFile = resourcesFolder + "matrix.txt";
      String expectedResultFile = resourcesFolder + "expected_result.txt";

      SquareMatrix matrix = SquareMatrix.loadFrom(matrixFile);
      SquareMatrix result = SquareMatrix.loadFrom(matrixFile);

      int iterations = 10;
      for (int i = 0; i < iterations; i++) {
        result = result.multiplyWith(matrix);
      }

      SquareMatrix expectedResult = SquareMatrix.loadFrom(expectedResultFile);

      SquareMatrix resultCopy = result; // variables passed to lambda need to be final or effectively final
      return () -> Assert.assertEquals(expectedResult, resultCopy, "Matrix comparison");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
