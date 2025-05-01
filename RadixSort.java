public class RadixSort {
    /* Odd pass - first_buckets
     * Even pass - second_buckets
     * The first pass is for the least significant digit (LSD) and the subsequent pass is for the next significant digit.
     */

    // Method to clear the buckets and reset the bucket count
    public static void clearBuckets(int[][] buckets, int[] bucketCount) {
        for (int i = 0; i < buckets.length; i++) {
            for (int j = 0; j < bucketCount[i]; j++) {
                buckets[i][j] = 0;
            }
            bucketCount[i] = 0;
        }
    }

    public static void printPass(int passNum, int[][] buckets, int[] bucketCount) {
        System.out.print("After pass " + passNum + ": ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < bucketCount[i]; j++) {
                System.out.print(buckets[i][j] + " ");
            }
        }
        System.out.println();
    }

    // Method to find the maximum value in the array
    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static void radixSort(int[] arr) {
        // Find the maximum number to know the number of digits
        int max = getMax(arr);
        int maxDigits = Integer.toString(max).length();


        // Initialize 2D array of buckets [rows][columns]
        // Each row represents a digit place (0-9) and each column represents an element in the array
        int[][] first_buckets = new int[10][arr.length];
        int[] first_bucketCount = new int[10]; // To keep track of the number of elements in each bucket

        int[][] second_buckets = new int[10][arr.length]; // Second buckets for even passes
        int[] second_bucketCount = new int[10]; // To keep track of the number of elements in each bucket

        int place = 1; // Start with the least significant digit

        // Fill the first_bucketCount and second_bucketCount with 0s
        for (int i = 0; i < 10; i++) {
            first_bucketCount[i] = 0;
            second_bucketCount[i] = 0;
        }

        // Distributing elements into first_buckets based on the current digit
        for (int i = 0; i < arr.length; i++) {
            int digit = (arr[i] / place) % 10;
            first_buckets[digit][first_bucketCount[digit]] = arr[i];
            first_bucketCount[digit]++;
        }

        printPass(1, first_buckets, first_bucketCount); // Print the first pass

        place *= 10; // Move to the next digit place

        for (int i = 2; i <= maxDigits; i++) {
            if (i % 2 == 0) {
                clearBuckets(second_buckets, second_bucketCount);
                // Collecting elements from first buckets into second buckets based on the current digit
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < first_bucketCount[j]; k++) {
                        int digit = (first_buckets[j][k] / place) % 10;
                        second_buckets[digit][second_bucketCount[digit]] = first_buckets[j][k];
                        second_bucketCount[digit]++;
                    }
                } 
                printPass(i, second_buckets, second_bucketCount); // Print the current pass               
            }
            else {
                clearBuckets(first_buckets, first_bucketCount);
                // Collecting elements from second buckets into first buckets based on the current digit
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < second_bucketCount[j]; k++) {
                        int digit = (second_buckets[j][k] / place) % 10;
                        first_buckets[digit][first_bucketCount[digit]] = second_buckets[j][k];
                        first_bucketCount[digit]++;
                    }
                }  
                printPass(i, first_buckets, first_bucketCount); // Print the current pass              
            }

            place *= 10; // Move to the next digit place
        }

        // Final collection of sorted elements into the original array
        int index = 0;
        if (maxDigits % 2 == 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < second_bucketCount[i]; j++) {
                    arr[index++] = second_buckets[i][j];
                }
            }
        } else { 
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < first_bucketCount[i]; j++) {
                    arr[index++] = first_buckets[i][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {275, 87, 426, 61, 409, 170, 677, 503};
        System.out.println("Unsorted list: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println("\n");

        radixSort(arr);

        System.out.println("\nSorted list: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}