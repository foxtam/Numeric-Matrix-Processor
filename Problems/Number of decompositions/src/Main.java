import java.util.Scanner;

class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int n = in.nextInt();
        printDecompositions(n);
    }

    static void printDecompositions(int n) {
        printNextDecompositionsHelper(n, new int[n], 0);
    }

    static void printNextDecompositionsHelper(int n, int[] parts, int index) {
        if (n == 0) {
            printArray(parts, index);
        } else {
            int cap = index == 0 ? n : parts[index - 1];
            for (int i = 1; i <= cap && n - i >= 0; i++) {
                parts[index] = i;
                printNextDecompositionsHelper(n - i, parts, index + 1);
            }
        }
    }

    static void printArray(int[] array, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}