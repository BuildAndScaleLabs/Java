public class Constructor {

//    1. Instance Variables vs. Local Variables & Default ValuesBefore understanding constructors, you must understand how Java initializes memory.
//    Local Variables: Variables declared inside a method (e.g., int x; inside main()).
//    Memory: Stored in the Stack.
//    Initialization: Java does not provide default values for local variables to optimize CPU/Memory performance. If you try to print an uninitialized local variable,
//               you get a Compile-Time Error.Instance Variables: Variables declared inside a class but outside any method (e.g., String name; inside class Student).
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
}