import java.util.Scanner;

class Main {
    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int size = SCANNER.nextInt();
        int[] numbers = new int[size];
        fillArrayFromScanner(numbers);
        int indexOfMax = findIndexOfMax(numbers);
        System.out.println(indexOfMax);
    }

    private static void fillArrayFromScanner(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = SCANNER.nextInt();
        }
    }

    private static int findIndexOfMax(int[] numbers) {
        int indexOfMax = 0;
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > numbers[indexOfMax]) {
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }
}