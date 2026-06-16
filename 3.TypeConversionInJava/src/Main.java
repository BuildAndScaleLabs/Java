
public class Main {
    public static void main(String[] args) {
        // there is two type of conversion
        //Implicit and explicit
        // Implicit means java does internally
        // Explicit means we do manually by casting

        //Rule of Implicit conversion
        // 1> Destination data type should be wider that source data types

        //1. Implicit conversion
        // (byte to int)
        // byte b = 24;
        // int i;

        // i = b;
        // System.out.println(i); // 24

        // character to int
        // char c = 'a';
        // int i;

        // i = c;

        // System.out.println(i); // 97


        // explicit conversion or narrowing conversion
//        int i =300;
//        byte b;
//        b=(byte)i;
//        System.out.println(b);


        // 2. Explicit conversion
        // int i = 300;
        // byte b; // -128 to +127

        // b = (byte) i;
        // System.out.println(b); // 300 % 256 = 44


        // 3. Truncating conversion
        // float f = 15.678f;
        // int i;

        // i = (int) f;
        // System.out.println(i); // 15

        // Boolean to any data type
        // These conversions are not possible

        // boolean bool = false;
        // int i;

        // i = bool;

        //automatic type promotion
        //byte ,short and char these values are promoted to int
        // if any operand is long ,whole expression will become long same for
        // if any operand is float ,whole expression will become float
        // if any operand is double ,whole expression will become double
        byte b = 50;
        b = (byte) (b * 2); // 100

        System.out.println(b);
    }
}