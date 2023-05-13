package org.renaissance.matrix;

import org.renaissance.Benchmark;
import org.renaissance.BenchmarkContext;
import org.renaissance.BenchmarkResult;
import org.renaissance.BenchmarkResult.Validators;
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
      String fileName = "benchmarks/matrix/src/main/resources/matrix.txt";

      SquareMatrix matrix = SquareMatrix.loadFrom(fileName);
      SquareMatrix result = SquareMatrix.loadFrom(fileName);

      int iterations = 10;
      for (int i = 0; i < iterations; i++) {
        result = result.multiplyWith(matrix);
      }

      return Validators.simple("name", 0, 0, 0);
    } catch (IOException e) {
      return Validators.simple("name", 0, 1, 0);
    }
  }
}
