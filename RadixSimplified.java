import java.util.Arrays;

public class RadixSimplified {

    // Get largest value to get the max digit count

    public static int getMax(int[] values) {
        int max = values[0];
        for (int val : values)
            if (val > max)
                max = val;
        return max;
    }

    // Print the buckets row by row

    public static void printPass(int pass, int[][] buckets, int[] bucketSize) {
        System.out.print("After Pass " + pass + ": ");
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < bucketSize[i]; j++)
                System.out.print(buckets[i][j] + " ");
        System.out.println();
    }

    // Copy from source to destination (bucket)

    private static void scatterBuckets(int[][] source, int[] sourceSize, int[][] destination, int[] destinationSize,
            int placeValue) {

        // Reset all the counters first

        Arrays.fill(destinationSize, 0);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < sourceSize[i]; j++) {
                int value = source[i][j]; // "value" is the elements in the passed array
                int destinationRow = (value / placeValue) % 10;
                destination[destinationRow][destinationSize[destinationRow]++] = value;
            }
    }

    // Start sorting

    public static void radixSort(int[] list) {

        int n = list.length;
        int[][] firstBuckets = new int[10][n];
        int[] firstBucketSize = new int[10];
        int[][] secondBuckets = new int[10][n];
        int[] secondBucketSize = new int[10];

        int placeValue = 1; // 1 = 1st digit, 2 = 2nd digit, etc.

        for (int key : list) {
            int row = (key / placeValue) % 10;
            firstBuckets[row][firstBucketSize[row]++] = key;
        }
        printPass(1, firstBuckets, firstBucketSize);
        placeValue *= 10;

        // Remaining passes
        int maxDigits = Integer.toString(getMax(list)).length(); // Get the max digit from max value
        for (int pass = 2; pass <= maxDigits; pass++, placeValue *= 10) {

            if (pass % 2 == 0) { // From odd to even pass
                scatterBuckets(firstBuckets, firstBucketSize,
                        secondBuckets, secondBucketSize,
                        placeValue);
                printPass(pass, secondBuckets, secondBucketSize);
            } else { // From even to odd pass
                scatterBuckets(secondBuckets, secondBucketSize,
                        firstBuckets, firstBucketSize,
                        placeValue);
                printPass(pass, firstBuckets, firstBucketSize);
            }
        }

        // Copy back to the array

        int[][] finalBuckets = (maxDigits % 2 == 0) ? secondBuckets : firstBuckets;
        int[] finalBucketSize = (maxDigits % 2 == 0) ? secondBucketSize
                : firstBucketSize;

        int outputIndex = 0;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < finalBucketSize[i]; j++)
                list[outputIndex++] = finalBuckets[i][j];
    }

    public static void main(String[] args) {
        int[] data = { 275, 87, 426, 61, 409, 170, 677, 503 };

        System.out.println("Unsorted: " + Arrays.toString(data) + "\n");
        radixSort(data);
        System.out.println("\nSorted: " + Arrays.toString(data));
    }
}
