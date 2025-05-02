import java.util.Arrays;

public class RadixSimplified2 {

    // Get largest length of word in the list

    public static int getMaxLength(String[] arr) {
        int maxLength = 0;
        for (String str : arr) {
            if (str.length() > maxLength) {
                maxLength = str.length();
            }
        }
        return maxLength;
    }

    // Print the buckets row by row

    public static void printPass(int pass, String[][] buckets, int[] bucketSize) {
        System.out.print("After Pass " + pass + ": ");
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < bucketSize[i]; j++)
                System.out.print(buckets[i][j] + " ");
        System.out.println();
    }

    // Copy from source to destination (bucket)

    private static void scatterBuckets(String[][] source, int[] sourceSize, String[][] destination, int[] destinationSize,
            int placeValue) {

        // Reset all the counters first

        Arrays.fill(destinationSize, 0);
                
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < sourceSize[i]; j++) {
                String word = source[i][j];
                int charIndex = placeValue < word.length() ? word.charAt(placeValue) - 'a' : -1;
                if (charIndex >= 0) {
                    destination[charIndex][destinationSize[charIndex]++] = word;
                } else {
                    destination[0][destinationSize[0]++] = word; // Place shorter words in the first bucket
                }
            }
        }
    }

    // Start sorting

    public static void radixSort(String[] list) {
        // Find the maximum length of strings to know the number of passes
        int maxLength = getMaxLength(list);

        int n = list.length;
        String[][] firstBuckets = new String[26][n];
        int[] firstBucketSize = new int[26];
        String[][] secondBuckets = new String[26][n];
        int[] secondBucketSize = new int[26];

        for (String word : list) {
            int charIndex = (maxLength-1) < word.length() ? word.charAt(maxLength-1) - 'a' : -1;
            if (charIndex >= 0) {
                firstBuckets[charIndex][firstBucketSize[charIndex]++] = word;
            } else {
                firstBuckets[0][firstBucketSize[0]++] = word; // Place shorter words in the first bucket
            }
        }

        int passNum = 1; // Pass number starts from 1
        printPass(passNum, firstBuckets, firstBucketSize);
        passNum++; // Increment pass number for the next pass

        // Remaining passes        
        for (int placeValue = maxLength-2; placeValue >= 0; placeValue--, passNum++) {
            if (passNum % 2 == 0) { // From odd to even pass
                scatterBuckets(firstBuckets, firstBucketSize,
                        secondBuckets, secondBucketSize,
                        placeValue);
                printPass(passNum, secondBuckets, secondBucketSize);
            } else { // From even to odd pass
                scatterBuckets(secondBuckets, secondBucketSize,
                        firstBuckets, firstBucketSize,
                        placeValue);
                printPass(passNum, firstBuckets, firstBucketSize);
            }
        }

        // Copy back to the array

        String[][] finalBuckets = (maxLength % 2 == 0) ? secondBuckets : firstBuckets;
        int[] finalBucketSize = (maxLength % 2 == 0) ? secondBucketSize
                : firstBucketSize;

        int outputIndex = 0;
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < finalBucketSize[i]; j++)
                list[outputIndex++] = finalBuckets[i][j];
    }

    public static void main(String[] args) {
        String[] data = {"banana", "grape", "apple", "kiwi", "peach", "at"};

        System.out.println("Unsorted: " + Arrays.toString(data) + "\n");
        radixSort(data);
        System.out.println("\nSorted: " + Arrays.toString(data));
    }
}
