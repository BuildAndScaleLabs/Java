public class EncapsulationAndInheritance {


//    Encapsulation is the mechanism of wrapping data (variables) and behavior (methods) together as a single unit (a Class), and restricting unauthorized access to that data.
//    private (Most Restrictive): Accessible ONLY within the exact same class where it is declared.
//    Default (No keyword): Accessible ONLY within classes in the same package (folder). Often called "Package-Private".
//    protected: Accessible within the same package, AND by child classes (inherited classes), even if the child class is in a different package.
//    public (Least Restrictive): Accessible from anywhere in the entire application.

//    Inheritance allows a new class (Child/Subclass) to inherit the variables and methods of an existing class (Parent/Superclass) using the extends keyword.
//    Primary Benefit: Code Reusability. If Vehicle has a start() method, Car extends Vehicle automatically gets the start() method without you writing it again.
//    Types of Inheritance Supported in Java:
//    Single: A -> B
//    Multilevel: A -> B -> C
//    Hierarchical: A -> B and A -> C
//
//    Type NOT Supported: Multiple Inheritance (A and B both -> C) is not supported via classes in Java.
//    The Diamond Problem (Interview Question): Why is Multiple Inheritance banned? If Class B and Class C both inherit from A and override a show() method,
//    and Class D inherits from both B and C... if you call d.show(),
//    the JVM wouldn't know whether to use B's version or C's version. To avoid this ambiguity, Java bans multiple inheritance with classes (it uses Interfaces instead).
}
