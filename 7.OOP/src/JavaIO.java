public class JavaIO {

    /*
    ========================================================================
                            JAVA I/O — COMPLETE NOTES
    ========================================================================

    I/O stands for:

        I  -> Input
        O  -> Output

    Java I/O is mainly used to:

        -> Read data
        -> Write data

    Examples:

        Input:
            Keyboard
            File
            Network
            Database/System source

        Output:
            Console
            File
            Network
            etc.


    ========================================================================
                         1. WHAT IS A STREAM?
    ========================================================================

    Java I/O is STREAM based.

    A Stream represents a flow of data.

    Think of a stream like a pipe through which data travels.

        SOURCE
          |
          |  data
          ↓
       STREAM
          |
          ↓
       PROGRAM


    INPUT STREAM
    ------------

    Data comes INTO our program.

        Keyboard
            ↓
        Input Stream
            ↓
        Java Program


    OUTPUT STREAM
    -------------

    Data goes OUT from our program.

        Java Program
            ↓
        Output Stream
            ↓
        Console / File / Network


    IMPORTANT:

        Input  = Data coming INTO the program
        Output = Data going OUT of the program


    ========================================================================
                         2. SYSTEM CLASS
    ========================================================================

    System class belongs to:

        java.lang

    java.lang is automatically imported by Java.

    System provides three important objects:

        System.in
        System.out
        System.err


    ------------------------------------------------------------------------
    System.out
    ------------------------------------------------------------------------

    System.out is used for standard output.

    Its type is:

        PrintStream

    Example:

        System.out.println("Hello");


    Conceptually:

        System
          |
          +---- out
                  |
                  +---- PrintStream


    ------------------------------------------------------------------------
    System.err
    ------------------------------------------------------------------------

    System.err is used for error output.

    Its type is also:

        PrintStream

    Example:

        System.err.println("Something went wrong");


    In many IDEs, System.err may appear in a different color
    such as red.

    IMPORTANT:

        The color is provided by the IDE/terminal.
        It is not a special "red stream" in Java.


    ------------------------------------------------------------------------
    System.in
    ------------------------------------------------------------------------

    System.in is used for standard input.

    Its type is:

        InputStream

    By default, standard input comes from the keyboard.

        Keyboard
            ↓
        System.in
            ↓
        Java Program


    ========================================================================
                    3. SYSTEM.IN.READ() — BYTE LEVEL INPUT
    ========================================================================

    System.in is an InputStream.

    InputStream works with BYTES.

    Example:

        int data = System.in.read();

    read() reads ONE BYTE at a time.

    IMPORTANT:

    System.in.read() returns an int, not a char.

    It returns:

        0 to 255  -> byte value
        -1        -> end of stream


    Example:

        int x = System.in.read();

    If you enter:

        A

    The character 'A' is represented in common ASCII/UTF-8-compatible
    input as byte value 65.

    So:

        System.in.read()

    can return:

        65


    You can convert it:

        char ch = (char) System.in.read();


    ------------------------------------------------------------------------
    PROBLEM WITH System.in.read()
    ------------------------------------------------------------------------

    Suppose the user enters:

        Aditya

    A single read() does NOT read the entire word.

    It reads one byte:

        A

    The remaining input remains in the input stream/buffer.

    Therefore, reading a complete line using only read()
    requires repeated reads.


    Example concept:

        int data;

        while ((data = System.in.read()) != -1) {

            System.out.print((char) data);
        }


    This is why System.in.read() is considered low-level input.


    ========================================================================
                    4. WHY BUFFERING IS REQUIRED
    ========================================================================

    Imagine reading 1,000 characters.

    If we read one character/byte at a time, we may perform many
    low-level read operations.

    Calling the underlying I/O source repeatedly can be expensive.

    BUFFERING solves this problem.

    Instead of:

        OS
         ↓
        1 byte
         ↓
        Java
         ↓
        OS
         ↓
        1 byte
         ↓
        Java
         ↓
        ...

    We can conceptually do:

        OS
         ↓
        Large chunk
         ↓
        Java Buffer
         ↓
        Program reads from buffer


    The buffer allows the program to read data efficiently from
    Java-managed memory after larger chunks have been fetched.


    ========================================================================
                    5. BufferedReader
    ========================================================================

    BufferedReader belongs to:

        java.io

    It is used to efficiently read CHARACTER data.

    Important method:

        readLine()

    Example:

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in)
            );

        String name = br.readLine();


    But why do we need InputStreamReader?

    Because:

        System.in
            |
            ↓
        InputStream
            |
            ↓
        BYTE STREAM


    Whereas:

        BufferedReader
            |
            ↓
        CHARACTER STREAM


    Byte and character streams are different concepts.


    ========================================================================
                    6. InputStreamReader — THE BRIDGE
    ========================================================================

    InputStreamReader converts bytes into characters.

    Think:

        System.in
        (bytes)
           |
           ↓
        InputStreamReader
        (byte → character decoding)
           |
           ↓
        BufferedReader
        (buffered characters)
           |
           ↓
        Your Program


    This is why the old/legacy syntax looks like:

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in)
            );


    Then:

        String name = br.readLine();


    THREE LAYERS:

        System.in
            ↓
        InputStreamReader
            ↓
        BufferedReader


    Their responsibilities:

        System.in
            -> provides raw input bytes

        InputStreamReader
            -> converts bytes to characters using a charset

        BufferedReader
            -> buffers characters and provides convenient
               methods such as readLine()


    ========================================================================
                    7. WHY readLine() IS USEFUL
    ========================================================================

    BufferedReader provides:

        readLine()

    It reads an entire line.

    Example:

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in)
            );

        System.out.print("Enter name: ");

        String name = br.readLine();

        System.out.println("Name = " + name);


    If user enters:

        Aditya Sharma

    readLine() returns:

        "Aditya Sharma"


    INCLUDING spaces within the line.


    ========================================================================
                    8. IMPORTANT: BUFFEREDREADER RETURNS STRING
    ========================================================================

    BufferedReader's readLine() returns:

        String


    If we want an integer:

        String input = br.readLine();

        int age = Integer.parseInt(input);


    Example:

        System.out.print("Enter age: ");

        int age =
            Integer.parseInt(br.readLine());


    So BufferedReader generally requires explicit parsing
    when reading primitive values.


    ========================================================================
                    9. SCANNER
    ========================================================================

    Scanner was introduced in:

        Java 1.5

    Scanner belongs to:

        java.util

    NOT:

        java.io


    Scanner is a utility class used to make input easier.

    Example:

        Scanner sc = new Scanner(System.in);


    Now we can directly read different types:

        String:
            sc.nextLine();

        int:
            sc.nextInt();

        double:
            sc.nextDouble();

        float:
            sc.nextFloat();

        long:
            sc.nextLong();


    Example:

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();


    We don't need:

        Integer.parseInt()


    because Scanner performs the conversion for us.


    ========================================================================
                    10. HOW SCANNER WORKS — TOKENIZATION
    ========================================================================

    Scanner can divide input into TOKENS.

    Suppose the input is:

        Rahul 25 Pune

    Scanner can treat these as separate tokens:

        Rahul
        25
        Pune


    By default, Scanner uses whitespace as a delimiter.

    Whitespace includes things such as:

        spaces
        tabs
        line separators


    Example:

        Scanner sc = new Scanner(System.in);

        String name = sc.next();
        int age = sc.nextInt();


    Input:

        Rahul 25


    Result:

        name = "Rahul"
        age  = 25


    next()
    -------

    Reads the next token.

    nextLine()
    ----------

    Reads the remaining part of the current line.


    ========================================================================
                    11. SCANNER vs BUFFEREDREADER
    ========================================================================

    This is an IMPORTANT INTERVIEW QUESTION.


    ------------------------------------------------------------------------
    Scanner
    ------------------------------------------------------------------------

    Package:

        java.util

    Main advantage:

        Easy to use

    Supports:

        nextInt()
        nextDouble()
        nextLong()
        next()
        nextLine()
        etc.

    It performs parsing/conversion for you.

    It also tokenizes input.


    ------------------------------------------------------------------------
    BufferedReader
    ------------------------------------------------------------------------

    Package:

        java.io

    Main advantage:

        Efficient character reading

    Common method:

        readLine()

    It primarily gives you String data.

    If you want an integer:

        Integer.parseInt(br.readLine());


    ========================================================================
                    12. PERFORMANCE — SCANNER VS BUFFEREDREADER
    ========================================================================

    In many high-input-volume situations:

        BufferedReader
              ↓
        generally faster

    Scanner
              ↓
        generally more convenient


    WHY CAN SCANNER BE SLOWER?

    Scanner performs additional work such as:

        -> tokenization
        -> parsing
        -> type conversion
        -> delimiter processing

    Scanner is therefore designed more for convenience than
    maximum raw input performance.


    BufferedReader mainly focuses on buffered character reading.


    IMPORTANT:

    Don't say:

        "Scanner is always slow."

    Better statement:

        "BufferedReader is generally faster for large volumes of
        text input, while Scanner is easier and more convenient
        because it provides tokenization and type parsing."


    ========================================================================
                    13. SIMPLE COMPARISON
    ========================================================================

                    Scanner              BufferedReader
                    -----------------------------------------

    Package         java.util             java.io

    Ease            Very easy             More manual

    Input            Tokens/types          Lines/characters

    nextInt()        YES                   NO

    nextDouble()     YES                   NO

    readLine()       YES                   YES

    Parsing          Built-in              Usually manual

    Performance      Generally slower      Generally faster

    Large input      Less suitable         More suitable


    ========================================================================
                    14. COMPLETE EXAMPLE
    ========================================================================

    The following program demonstrates:

        -> System.out
        -> System.err
        -> System.in
        -> Scanner
        -> BufferedReader
        -> System.in.read()
    */


    public static void main(String[] args) throws Exception {

        /*
        ================================================================
        System.out
        ================================================================
        */

        System.out.println("Normal output using System.out");


        /*
        ================================================================
        System.err
        ================================================================
        */

        System.err.println("Error output using System.err");


        /*
        ================================================================
        System.in.read()
        ================================================================

        Uncomment this section if you want to experiment with
        byte-level input.

        Example:

            System.out.print("Enter one character: ");

            int data = System.in.read();

            System.out.println(
                "ASCII/byte value = " + data
            );

        If you enter:

            A

        data will normally be:

            65

        Remember:
            read() returns int.
        */


        /*
        ================================================================
        SCANNER EXAMPLE
        ================================================================

        Uncomment to test separately.

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your age: ");
        int age = sc.nextInt();

        System.out.println("Name = " + name);
        System.out.println("Age = " + age);

        sc.close();
        */


        /*
        ================================================================
        BUFFEREDREADER EXAMPLE
        ================================================================

        Uncomment to test separately.

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in)
            );

        System.out.print("Enter your name: ");
        String name = br.readLine();

        System.out.print("Enter your age: ");
        int age = Integer.parseInt(br.readLine());

        System.out.println("Name = " + name);
        System.out.println("Age = " + age);
        */


        /*
        ================================================================
        IMPORTANT IMPORTS
        ================================================================

        For Scanner:

            import java.util.Scanner;


        For BufferedReader:

            import java.io.BufferedReader;
            import java.io.InputStreamReader;


        In a normal Java file, imports must appear ABOVE the class.

        Example:

            import java.io.BufferedReader;
            import java.io.InputStreamReader;
            import java.util.Scanner;

            public class JavaIO {
                ...
            }


        =================================================================
        QUICK REVISION
        =================================================================

        I/O
            ↓
        Input / Output


        Input Stream
            ↓
        Data coming INTO program


        Output Stream
            ↓
        Data going OUT of program


        System.in
            ↓
        InputStream
            ↓
        Standard input / keyboard


        System.out
            ↓
        PrintStream
            ↓
        Standard output


        System.err
            ↓
        PrintStream
            ↓
        Error output


        System.in.read()
            ↓
        Reads one byte
            ↓
        Low-level input


        InputStreamReader
            ↓
        Converts bytes → characters


        BufferedReader
            ↓
        Buffers characters
            ↓
        readLine()


        Scanner
            ↓
        Utility class
            ↓
        Tokenization + parsing
            ↓
        nextInt(), nextDouble(), next(), nextLine()


        =================================================================
        INTERVIEW MEMORY TRICK
        =================================================================

        System.in
            = BYTE INPUT


        InputStreamReader
            = BYTE → CHARACTER BRIDGE


        BufferedReader
            = BUFFERED CHARACTER READER


        Scanner
            = EASY INPUT + TOKENIZATION + TYPE PARSING


        =================================================================
        MOST IMPORTANT INTERVIEW QUESTIONS
        =================================================================

        Q1. What is System.in?

        A:
        System.in is a static InputStream representing standard input.


        Q2. What is System.out?

        A:
        System.out is a static PrintStream representing standard output.


        Q3. What is System.err?

        A:
        System.err is a static PrintStream used for standard error output.


        Q4. What does System.in.read() do?

        A:
        It reads one byte and returns it as an int.
        It returns -1 when the end of the stream is reached.


        Q5. Why do we use InputStreamReader?

        A:
        It acts as a bridge between byte streams and character streams,
        decoding bytes into characters.


        Q6. Why do we use BufferedReader?

        A:
        It buffers character input and provides convenient methods such
        as readLine(), reducing the overhead of repeatedly accessing the
        underlying input source.


        Q7. Why is BufferedReader usually faster than Scanner?

        A:
        Scanner performs additional parsing, tokenization and conversion,
        while BufferedReader focuses mainly on efficient buffered
        character reading.


        Q8. What is the package of Scanner?

        A:
        java.util


        Q9. What is the package of BufferedReader?

        A:
        java.io


        Q10. Can BufferedReader directly read an int?

        A:
        No. readLine() returns String, so we generally parse it:

            int age = Integer.parseInt(br.readLine());


        Q11. Can Scanner read an int directly?

        A:
        Yes:

            int age = sc.nextInt();


        Q12. What is the difference between next() and nextLine()?

        A:

            next()
                -> reads the next token

            nextLine()
                -> reads the remaining/current line


        =================================================================
        FINAL CONCEPT
        =================================================================

        Java I/O is built around the idea of DATA FLOW.

        BYTE LEVEL:

            System.in
                ↓
            InputStream


        BYTE → CHARACTER:

            System.in
                ↓
            InputStreamReader


        BUFFERED CHARACTER INPUT:

            System.in
                ↓
            InputStreamReader
                ↓
            BufferedReader


        EASY USER INPUT:

            System.in
                ↓
            Scanner
                ↓
            nextInt()
            nextDouble()
            next()
            nextLine()


        REMEMBER:

            Scanner
                = EASY

            BufferedReader
                = FAST / BUFFERED

            InputStreamReader
                = BYTE → CHARACTER

            System.in
                = INPUT

            System.out
                = NORMAL OUTPUT

            System.err
                = ERROR OUTPUT
        */
    }
}