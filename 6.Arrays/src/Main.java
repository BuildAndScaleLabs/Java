public class Main {
    public static void main(String[] args) {

        // contigeous memory means continues memory allocation in heap means one after one memory has allocated
        int[] op = new int[5];

        int[] nums = {0,1,2,3,4,5,6};

        int[][] marks = new int[3][3];

        //in 2d array every element is array itself
        for(int row=0;row<marks.length;row++){
            for(int col=0;col<marks[row].length;col++){
                System.out.println( marks[row][col]);
            }
        }

//        Primitives (int, float, double, etc.): Stored directly in the Stack memory.
//        If you write int x = 4;, a 4-byte block of memory on the stack is allocated, and the binary value of 4 is placed directly inside it.
//        Non-Primitives (Arrays, Objects, Strings): Created using the new keyword. These are stored in the Heap memory. When you write int[] arr = new int[5];:
//        A continuous block of memory for 5 integers (5 x 4 bytes = 20 bytes) is reserved in the Heap.
//        A reference variable named arr is created in the Stack.
//        This Stack variable does not contain the array. It contains a memory address pointer to the start of the array in the Heap (the base address).
//        Arrays provide "Random Access", meaning accessing arr[2] takes the exact same amount of time as accessing arr[200].
//        The JVM doesn't count from zero to reach the index; it calculates the physical memory address instantly using a formula:
//        Target Address = Base_Address + (Data_Type_Size * Index)
//        If an integer array starts at memory address 100 (the Base Address):
//        arr[0]: 100 + (4 bytes * 0) = Address 100
//        arr[3]: 100 + (4 bytes * 3) = Address 112
//        The JVM jumps straight to address 112 and reads 4 bytes. This contiguous memory layout is also highly optimized for CPU Caching (fetching adjacent memory blocks in advance).

//        Multi-Dimensional Arrays: The "Array of Arrays" Illusion
//        In languages like C/C++, a 2D array is a single, flat contiguous block of memory mapped mathematically. In Java, 2D arrays do not exist.
//        Java implements 2D arrays as an "Array of Arrays".
//                When you declare int[][] arr = new int[3][4];:
//        Java creates a 1D array of size 3 in the Heap.
//                However, this array does not store integers. It stores reference variables (pointers, which take 4 bytes each).
//        Each of those 3 reference variables points to another completely separate 1D integer array of size 4 somewhere else in the Heap.
//                Because of this, the sub-arrays in a Java 2D array do not have to be contiguous with each other in memory, and they don't even have to be the same size (Jagged Arrays).

//        The Boolean Size Mystery
//        While int is strictly 4 bytes and double is 8 bytes, the official Java specification does not define a strict size for boolean.
//                Even though a boolean conceptually only needs 1 bit (0 or 1), most JVMs (like Oracle HotSpot) allocate 1 full byte (8 bits) for a boolean.
//                Why? CPU Optimization. CPUs fetch data from RAM in byte-sized chunks (or words). Forcing a CPU to extract a single bit from a byte requires extra bit-shifting instructions,
//                which slows down execution. Sacrificing 7 bits of memory is worth the speed tradeoff.

//        The Two-Step Math of 2D Array Random Access
//        We know a 1D integer array uses a simple formula: Base_Address + (4 bytes * index).
//        But because a 2D array in Java is an "Array of Arrays" (an array of reference variables pointing to other arrays), the JVM cannot find arr[1][2] in a single calculation. It requires two sequential memory jumps.
//        If you write arr[1][2]:
//        Jump 1: Find the Sub-Array
//        The JVM looks at the main array, which holds reference variables. (A reference variable usually takes 4 bytes).
//        It calculates the address of the 1st index: Main_Base_Address + (4 bytes * 1).
//        It jumps to that memory location and reads the 4 bytes. Those 4 bytes contain the Base Address of the new sub-array (let's say address 200).
//        Jump 2: Find the Actual Data
//        4. Now the JVM uses that new Base Address (200) to find the 2nd index.
//        5. It calculates: 200 + (4 bytes * 2) = 208.
//        6. It jumps to memory address 208 and fetches your integer.
//
//        The Pro Insight: In C/C++, a 2D array is one flat block of memory, requiring only one math calculation to find any element. In Java, a 2D array requires multiple calculations and memory jumps. This is why highly intensive computational tasks (like game engines or machine learning math) sometimes flatten 2D arrays into 1D arrays in Java for performance!
//
//         Arrays of Strings (Non-Primitive Arrays)
//        The video explicitly covers String[] names = new String[3];.
//        Strings are objects (Non-Primitives). Therefore, an Array of Strings behaves exactly like the first layer of a 2D array.
//                It is an Array of References: The array itself does not hold the text "Aditya" or "Rohit". It holds three 4-byte reference variables.
//                Separate Heap Allocations: The actual String objects ("Aditya", "Abhay", "Rohit") are stored completely separately in the Heap memory (specifically in the String Pool).
//        The Formula: If you ask for names[1], the JVM uses Base_Address + (4 bytes * 1) to find the reference, reads the memory address stored there,
//        and makes a second jump to actually read the word "Abhay".
    }
}