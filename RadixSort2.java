import java.util.Arrays;

public class RadixSort2 {
    /* Odd pass - first_buckets
     * Even pass - second_buckets
     * The first pass is for the least significant character (LSC) and the subsequent pass is for the next significant character.
     */

    // Method to clear the buckets and reset the bucket count
    public static void clearBuckets(String[][] buckets, int[] bucketCount) {
        for (int i = 0; i < buckets.length; i++) {
            Arrays.fill(buckets[i], null);
            bucketCount[i] = 0;
        }
    }

    // Method to find the maximum length of strings in the array
    public static int getMaxLength(String[] arr) {
        int maxLength = 0;
        for (String str : arr) {
            if (str.length() > maxLength) {
                maxLength = str.length();
            }
        }
        return maxLength;
    }

    // Method to print the contents of the buckets after each pass
    public static void printPass(int passNum, String[][] buckets, int[] bucketCount) {
        System.out.print("After pass " + passNum + ": ");
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < bucketCount[i]; j++) {
                System.out.print(buckets[i][j] + " ");
            }
        }
        System.out.println();
    }

    public static void radixSort(String[] arr) {
        // Find the maximum length of strings to know the number of passes
        int maxLength = getMaxLength(arr);

        // Initialize 2D array of buckets [rows][columns]
        // Each row represents a character (a-z, total 26) and each column represents a string in the array
        String[][] first_buckets = new String[26][arr.length];
        int[] first_bucketCount = new int[26]; // To keep track of the number of elements in each bucket

        String[][] second_buckets = new String[26][arr.length];
        int[] second_bucketCount = new int[26];

        // Fill the first_bucketCount and second_bucketCount with 0s
        Arrays.fill(first_bucketCount, 0);
        Arrays.fill(second_bucketCount, 0);

        // Distribute elements into first_buckets based on the current character
        for (String word : arr) {
            int charIndex = (maxLength-1) < word.length() ? word.charAt(maxLength-1) - 'a' : -1;
            if (charIndex >= 0) {
                first_buckets[charIndex][first_bucketCount[charIndex]] = word;
                first_bucketCount[charIndex]++;
            } else {
                first_buckets[0][first_bucketCount[0]] = word; // Place shorter words in the first bucket
                first_bucketCount[0]++;
            }
        }

        int passNum = 1; // Pass number starts from 1
        printPass(passNum, first_buckets, first_bucketCount);
        passNum++; // Increment pass number for the next pass
        
        // Start sorting from the least significant character
        for (int place = maxLength - 2; place >= 0; place--, passNum++) {
            if (place % 2 == 0) {
                clearBuckets(second_buckets, second_bucketCount);
                // Distribute elements into second_buckets based on the current character
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < first_bucketCount[i]; j++) {
                        String word = first_buckets[i][j];
                        int charIndex = place < word.length() ? word.charAt(place) - 'a' : -1;
                        if (charIndex >= 0) {
                            second_buckets[charIndex][second_bucketCount[charIndex]] = word;
                            second_bucketCount[charIndex]++;
                        } else {
                            second_buckets[0][second_bucketCount[0]] = word; // Place shorter words in the first bucket
                            second_bucketCount[0]++;
                        }
                    }
                }                
                printPass(passNum, second_buckets, second_bucketCount); // Print the second pass

            } else {
                clearBuckets(first_buckets, first_bucketCount);
                // Distribute elements into first_buckets based on the current character
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < second_bucketCount[i]; j++) {
                        String word = second_buckets[i][j];
                        int charIndex = place < word.length() ? word.charAt(place) - 'a' : -1;
                        if (charIndex >= 0) {
                            first_buckets[charIndex][first_bucketCount[charIndex]] = word;
                            first_bucketCount[charIndex]++;
                        } else {
                            first_buckets[0][first_bucketCount[0]] = word; // Place shorter words in the first bucket
                            first_bucketCount[0]++;
                        }
                    }                    
                }
                printPass(passNum, first_buckets, first_bucketCount); // Print the first pass
            }

            // Final collection of sorted elements into the original array
            int index = 0;
            if (maxLength % 2 == 1) {
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < first_bucketCount[i]; j++) {
                        arr[index++] = first_buckets[i][j];
                    }
                }
            } else {
                for (int i = 0; i < 26; i++) {
                    for (int j = 0; j < second_bucketCount[i]; j++) {
                        arr[index++] = second_buckets[i][j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] arr = {"banana", "grape", "apple", "kiwi", "peach", "at"};
        System.out.println("Unsorted list: ");
        for (String word : arr) {
            System.out.print(word + " ");
        }

        System.out.println();

        radixSort(arr);

        System.out.println("Sorted list: ");
        for (String word : arr) {
            System.out.print(word + " ");
        }
    }
}