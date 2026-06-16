
 // Notes: How Java Stores Negative Numbers and Floating Point Numbers

public class NegativeAndFloatingPointNotes {

    public static void main(String[] args) {

        System.out.println("========== 1. INTEGER STORAGE ==========");
        explainIntegerStorage();

        System.out.println("\n========== 2. NEGATIVE NUMBER STORAGE ==========");
        explainNegativeNumberStorage();

        System.out.println("\n========== 3. FLOATING POINT STORAGE ==========");
        explainFloatingPointStorage();

        System.out.println("\n========== 4. COMMON FLOATING POINT PROBLEM ==========");
        floatingPointProblem();

        System.out.println("\n========== 5. BEST PRACTICES ==========");
        bestPractices();
    }

    private static void explainIntegerStorage() {
        /*
         * Java stores integer values like byte, short, int, and long in binary.
         *
         * Important:
         * int  = 32 bits
         * long = 64 bits
         *
         * Example:
         * int x = 5;
         *
         * 5 in binary:
         * 00000000 00000000 00000000 00000101
         */

        int x = 5;

        System.out.println("Number: " + x);
        System.out.println("Binary: " + to32BitBinary(x));
    }

    private static void explainNegativeNumberStorage() {
        /*
         * Java does NOT store negative numbers by simply putting a minus sign.
         *
         * Java uses Two's Complement.
         *
         * Steps to store -5:
         *
         * Step 1: Write +5 in binary
         * 00000000 00000000 00000000 00000101
         *
         * Step 2: Invert all bits
         * 11111111 11111111 11111111 11111010
         *
         * Step 3: Add 1
         * 11111111 11111111 11111111 11111011
         *
         * So internally, -5 is stored as:
         * 11111111 11111111 11111111 11111011
         *
         * Easy memory line:
         * Negative number = invert bits of positive number + add 1
         */

        int positive = 5;
        int negative = -5;

        System.out.println("+5 Binary : " + to32BitBinary(positive));
        System.out.println("-5 Binary : " + to32BitBinary(negative));

        /*
         * Why int range is:
         * -2147483648 to 2147483647
         *
         * Because int has 32 bits.
         *
         * Range:
         * Minimum = -2^31
         * Maximum =  2^31 - 1
         *
         * One extra negative value exists because zero is counted on positive side.
         */

        System.out.println("Integer MIN value: " + Integer.MIN_VALUE);
        System.out.println("Integer MAX value: " + Integer.MAX_VALUE);
        System.out.println("MIN Binary: " + to32BitBinary(Integer.MIN_VALUE));
        System.out.println("MAX Binary: " + to32BitBinary(Integer.MAX_VALUE));
    }

    private static void explainFloatingPointStorage() {
        /*
         * Floating point numbers means decimal numbers.
         *
         * Examples:
         * 10.5
         * 99.99
         * 3.14
         *
         * Java has two main decimal primitive types:
         *
         * float  = 32 bits
         * double = 64 bits
         *
         * By default, decimal values in Java are double.
         *
         * Example:
         * double a = 10.5;
         *
         * For float, we must write f:
         * float b = 10.5f;
         *
         * Floating point numbers are stored in 3 parts:
         *
         * 1. Sign bit
         *    0 means positive
         *    1 means negative
         *
         * 2. Exponent
         *    Stores the power/scale of the number
         *
         * 3. Mantissa / Significand
         *    Stores the actual significant digits
         *
         * Easy memory line:
         * Floating point = sign + exponent + mantissa
         */

        float floatNumber = 10.5f;
        double doubleNumber = 10.5;

        System.out.println("float value : " + floatNumber);
        System.out.println("double value: " + doubleNumber);

        System.out.println("float bits : " + to32BitBinary(Float.floatToIntBits(floatNumber)));
        System.out.println("double bits: " + to64BitBinary(Double.doubleToLongBits(doubleNumber)));

        /*
         * float structure:
         * 1 bit  -> sign
         * 8 bits -> exponent
         * 23 bits -> mantissa/fraction
         *
         * double structure:
         * 1 bit  -> sign
         * 11 bits -> exponent
         * 52 bits -> mantissa/fraction
         *
         * That is why double is more accurate than float.
         */
    }

    private static void floatingPointProblem() {
        /*
         * Important production concept:
         *
         * Some decimal numbers cannot be stored perfectly in binary.
         *
         * Example:
         * 0.1 + 0.2 is expected to be 0.3
         *
         * But computers store floating point numbers in binary approximation.
         * So the result may not be exactly 0.3.
         */

        double result = 0.1 + 0.2;

        System.out.println("0.1 + 0.2 = " + result);
        System.out.println("Is result == 0.3? " + (result == 0.3));

        /*
         * Output is usually:
         * 0.30000000000000004
         * false
         *
         * So do not compare double/float directly using == for precision-based logic.
         */

        double expected = 0.3;
        double difference = Math.abs(result - expected);

        System.out.println("Difference: " + difference);
        System.out.println("Safe comparison: " + (difference < 0.000001));
    }

    private static void bestPractices() {
        /*
         * Best Practices:
         *
         * 1. Use int for normal whole numbers.
         *
         * 2. Use long when value can become very large.
         *    Example: IDs, timestamps, big counters.
         *
         * 3. Use double for general decimal calculations.
         *
         * 4. Use float only when memory is important.
         *    Example: graphics, games, large arrays.
         *
         * 5. Do NOT use float/double for money calculation.
         *
         * Bad:
         * double price = 99.99;
         *
         * Good:
         * Use BigDecimal for money.
         *
         * 6. Do not compare float/double using == directly.
         *    Use small difference, also called epsilon.
         *
         * 7. Remember:
         * Negative integers use Two's Complement.
         * Floating numbers use sign + exponent + mantissa.
         */

        System.out.println("Use BigDecimal for money.");
        System.out.println("Use epsilon comparison for double/float.");
        System.out.println("Negative number formula: invert bits + add 1.");
        System.out.println("Floating point formula: sign + exponent + mantissa.");
    }

    private static String to32BitBinary(int number) {
        String binary = Integer.toBinaryString(number);

        while (binary.length() < 32) {
            binary = "0" + binary;
        }

        return formatBits(binary);
    }

    private static String to64BitBinary(long number) {
        String binary = Long.toBinaryString(number);

        while (binary.length() < 64) {
            binary = "0" + binary;
        }

        return formatBits(binary);
    }

    private static String formatBits(String binary) {
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < binary.length(); i++) {
            formatted.append(binary.charAt(i));

            if ((i + 1) % 8 == 0 && i != binary.length() - 1) {
                formatted.append(" ");
            }
        }

        return formatted.toString();
    }
}