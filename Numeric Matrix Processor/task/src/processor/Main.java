package processor;

import java.util.Scanner;

public class Main {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int userChoice = readUserChoice();
            if (userChoice == 0) break;
            performOperation(userChoice);
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit");
    }

    private static int readUserChoice() {
        System.out.print("Your choice: > ");
        return SCANNER.nextInt();
    }

    private static void performOperation(int operationType) {
        switch (operationType) {
            case 1:
                addMatricesOperation();
                break;
            case 2:
                multiplyMatrixByNumberOperation();
                break;
            case 3:
                multiplyMatricesOperation();
                break;
            case 4:
                transposeMatrixOperation();
                break;
            case 5:
                calculateDeterminantOperation();
                break;
            case 6:
                inverseMatrixOperation();
                break;
        }
    }

    private static void addMatricesOperation() {
        double[][] firstMatrix = readNthMatrix("first ");
        double[][] secondMatrix = readNthMatrix("second ");

        if (!isMatricesSameDimensions(firstMatrix, secondMatrix)) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        System.out.println("The result is:");
        double[][] result = addMatrices(firstMatrix, secondMatrix);
        printMatrix(result);
    }

    private static void multiplyMatrixByNumberOperation() {
        double[][] matrix = readMatrix();
        double multiplier = readMultiplier();
        double[][] result = multiplyMatrixByNumber(matrix, multiplier);
        System.out.println("The result is:");
        printMatrix(result);
    }

    private static void multiplyMatricesOperation() {
        double[][] firstMatrix = readNthMatrix("first ");
        double[][] secondMatrix = readNthMatrix("second ");

        if (firstMatrix[0].length != secondMatrix.length) {
            System.out.println("The operation cannot be performed.");
            return;
        }

        double[][] result = multiplyMatrices(firstMatrix, secondMatrix);
        System.out.println("The result is:");
        printMatrix(result);
    }

    private static void transposeMatrixOperation() {
        int task = getUserTransposeTask();
        double[][] matrix = readMatrix();
        double[][] result = performTask(task, matrix);
        System.out.println("The result is:");
        printMatrix(result);
    }

    private static void calculateDeterminantOperation() {
        double[][] matrix = readMatrix();
        System.out.println("The result is:");
        System.out.println(calculateDeterminant(matrix));
    }

    private static void inverseMatrixOperation() {
        double[][] matrix = readMatrix();
        System.out.println("The result is:");
        printMatrix(inverseMatrix(matrix));
    }

    private static double[][] inverseMatrix(double[][] matrix) {
        double determinant = calculateDeterminant(matrix);
        double[][] transposedCofactorMatrix = mainDiagonalTranspose(getCofactorMatrix(matrix));
        return multiplyMatrixByNumber(transposedCofactorMatrix, 1.0 / determinant);
    }

    private static double[][] getCofactorMatrix(double[][] matrix) {
        double[][] cofactorMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                cofactorMatrix[i][j] = cofactor(matrix, i, j);
            }
        }
        return cofactorMatrix;
    }

    private static double calculateDeterminant(double[][] matrix) {
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            double determinant = 0;
            for (int i = 0; i < matrix.length; i++) {
                determinant += matrix[i][0] * cofactor(matrix, i, 0);
            }
            return determinant;
        }
    }

    private static double cofactor(double[][] matrix, int i, int j) {
        return Math.pow(-1, i + j) * calculateDeterminant(getMinorMatrix(matrix, i, j));
    }

    private static double[][] getMinorMatrix(double[][] matrix, int i, int j) {
        double[][] minorMatrix = new double[matrix.length - 1][matrix[0].length - 1];
        for (int k = 0, kMinor = 0; k < matrix.length; k++) {
            if (k == i) continue;
            for (int l = 0, lMinor = 0; l < matrix[0].length; l++) {
                if (l == j) continue;
                minorMatrix[kMinor][lMinor++] = matrix[k][l];
            }
            kMinor++;
        }
        return minorMatrix;
    }

    private static double[][] performTask(int task, double[][] matrix) {
        switch (task) {
            case 1:
                return mainDiagonalTranspose(matrix);
            case 2:
                return sideDiagonalTranspose(matrix);
            case 3:
                return verticalTranspose(matrix);
            case 4:
                return horizontalTranspose(matrix);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static double[][] mainDiagonalTranspose(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    private static double[][] sideDiagonalTranspose(double[][] matrix) {
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[matrix[0].length - j - 1][matrix.length - i - 1] = matrix[i][j];
            }
        }
        return result;
    }

    private static double[][] verticalTranspose(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][matrix[0].length - j - 1] = matrix[i][j];
            }
        }
        return result;
    }

    private static double[][] horizontalTranspose(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, result[matrix.length - i - 1], 0, matrix[0].length);
        }
        return result;
    }

    private static int getUserTransposeTask() {
        printTransposeMenu();
        return readUserChoice();
    }

    private static void printTransposeMenu() {
        System.out.println("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line");
    }

    private static double[][] readMatrix() {
        return readNthMatrix("");
    }

    private static double[][] readNthMatrix(String nth) {
        System.out.print("Enter size of " + nth + "matrix: > ");
        MatrixSize matrixSize = readSizeOfMatrix();
        System.out.println("Enter " + nth + "matrix:");
        return readMatrix(matrixSize.rows, matrixSize.columns);
    }

    private static boolean isMatricesSameDimensions(double[][] a, double[][] b) {
        return a.length == b.length && a[0].length == b[0].length;
    }

    private static double readMultiplier() {
        System.out.print("Enter constant: > ");
        return SCANNER.nextFloat();
    }

    private static double[][] addMatrices(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] line : matrix) {
            for (double n : line) {
                if (Math.round(n) == n) {
                    System.out.print((long) n + " ");
                } else {
                    System.out.print(n + " ");
                }
            }
            System.out.println();
        }
    }

    private static double[][] multiplyMatrixByNumber(double[][] matrix, double number) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result[i][j] = matrix[i][j] * number;
            }
        }
        return result;
    }

    private static MatrixSize readSizeOfMatrix() {
        return new MatrixSize(SCANNER.nextInt(), SCANNER.nextInt());
    }

    private static double[][] readMatrix(int rows, int columns) {
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.out.print("> ");
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = SCANNER.nextFloat();
            }
        }
        return matrix;
    }

    private static double[][] multiplyMatrices(double[][] a, double[][] b) {
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}

class MatrixSize {
    final int rows;
    final int columns;

    public MatrixSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}