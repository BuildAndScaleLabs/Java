public class StaticAndFinal {

    //static keywords
//    Execution Timing: It runs exactly ONCE when the Class is first loaded into memory by the ClassLoader-before any objects are created and before constructors run.
//    Memory Efficiency: It is NOT stored in the Heap inside the object. It is stored in a special shared memory area (the Metaspace/Method Area).
//    Only ONE copy exists, regardless of whether you create 1 object or 1,000,000 objects.
//    Shared State: All objects share this single variable. If s1 changes the static variable college to "IIT Kharagpur", s2 and s3 will immediately see "IIT Kharagpur".
//    Access: You do not need an object to access it. You access it via the Class name: Student.college = "IIT";
//    Can ONLY call other static methods: A static method cannot call a non-static method directly.
//    Can ONLY access static variables: A static method cannot use instance variables (like name or age) because those variables don't exist until an object is created.
//    Cannot use this: The "this" keyword refers to the "current object". Since static methods run at the class level, there is no "current object."

//    Immutability: Once a final variable is assigned a value, it cannot be reassigned. Attempting to do so causes a Compile-Time Error.
//    Initialization: A final variable must be initialized. You can do this at the time of declaration (final double PI = 3.14;) or inside a Constructor.
//    Naming Convention: Constants are written in UPPER_SNAKE_CASE (e.g., PI_VALUE).
//    The Power Combo (static final): Creating a variable that is static (shared across all objects) and final (unchangeable) is the industry standard way to define global constants.

}
