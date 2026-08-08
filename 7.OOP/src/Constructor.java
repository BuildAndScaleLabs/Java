public class Constructor {

//    1. Instance Variables vs. Local Variables & Default ValuesBefore understanding constructors, you must understand how Java initializes memory.
//    Local Variables: Variables declared inside a method (e.g., int x; inside main()).
//    Memory: Stored in the Stack.
//    Initialization: Java does not provide default values for local variables to optimize CPU/Memory performance. If you try to print an uninitialized local variable,
//               you get a Compile-Time Error.
//    Instance Variables: Variables declared inside a class but outside any method (e.g., String name; inside class Student).
//    Memory: Stored in the Heap as part of the object.
//    Initialization: Java automatically assigns Default Values to instance variables when the object is created.
//    Integers (int, byte, short, long) -> 0
//    Floating points (float, double) -> 0.
//    0Booleans -> falseObjects / Non-Primitives (like String or Arrays) -> null



//2. What is a Constructor?
//A Constructor is a special method block invoked automatically during object creation (when the new keyword is used).
// Its primary purpose is to initialize the object's state (its instance variables).

//The 5 Rules of Constructors:
//Name: Must exactly match the Class name.
//Return Type: Has NO return type—not even void.
//Invocation: Called automatically only during object creation.
//Purpose: Used to initialize an object.
//Overloading: Can be overloaded (multiple constructors with different parameter lists).

//    Types of Constructors
//    The Default Constructor (Invisible): If you do not write a constructor, the Java Compiler silently injects an empty,
//    no-argument constructor during compilation. This is what sets your instance variables to 0 or null.
//    Warning: Once you write any constructor manually, Java stops providing the invisible default one.
//    Parameterized Constructor: A constructor that accepts arguments, allowing you to pass dynamic data the moment the object is created (e.g., new Student("Sahil", 28);).

//    The this Keyword (Two Major Uses)
//    The this keyword acts as a reference variable pointing to the current object being constructed or manipulated.
//    Use Case 1: Resolving Shadowing (Field vs. Parameter)
//    If your constructor parameter has the exact same name as your instance variable, the compiler gets confused (Variable Shadowing).
//    Use Case 2: Constructor Chaining
//    Instead of duplicating code across multiple overloaded constructors, one constructor can call another constructor within the same class using this(arguments).
//    Strict Rule: The this() call MUST be the very first line inside the constructor body. If it is on the second line, it results in a Compile-Time Error.
}