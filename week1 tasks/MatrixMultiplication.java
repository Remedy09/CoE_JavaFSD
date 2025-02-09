import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class MatrixMultiplication {
    static class MultiplyTask implements Callable<Void> {
        private final int[][] matrixA;
        private final int[][] matrixB;
        private final int[][] result;
        private final int row;
        private final int col;

        public MultiplyTask(int[][] matrixA, int[][] matrixB, int[][] result, int row, int col) {
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.row = row;
            this.col = col;
        }

        @Override
        public Void call() {
            int sum = 0;
            for (int k = 0; k < matrixA[0].length; k++) {
                sum += matrixA[row][k] * matrixB[k][col];
            }
            result[row][col] = sum;
            return null;
        }
    }

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) throws InterruptedException, ExecutionException {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int rowsB = matrixB.length;
        int colsB = matrixB[0].length;

        if (colsA != rowsB) {
            throw new IllegalArgumentException("Matrix A columns must be equal to Matrix B rows.");
        }

        int[][] result = new int[rowsA][colsB];
        ExecutorService executor = Executors.newFixedThreadPool(rowsA * colsB);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                MultiplyTask task = new MultiplyTask(matrixA, matrixB, result, i, j);
                futures.add(executor.submit(task));
            }
        }

        for (Future<Void> future : futures) {
            future.get();
        }

        executor.shutdown();
        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrixA = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int[][] matrixB = {
            {7, 8},
            {9, 10},
            {11, 12}
        };

        try {
            int[][] result = multiplyMatrices(matrixA, matrixB);
            System.out.println("Result of the multiplication:");
            printMatrix(result);
        } catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
            System.err.println("Error during matrix multiplication: " + e.getMessage());
        }
    }
}
