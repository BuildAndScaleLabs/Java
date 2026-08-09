public class Abstraction {

    // there is two type of abstraction
    // High level abstraction
    // low level abstraction
//    Abstraction is the process of hiding the internal implementation details ("How it works") and exposing only the essential functionalities ("What it does").

//    Low-Level Abstraction: Achieved simply by creating classes and methods. When you call car.brake(), you don't need to know the physics code inside the method.
//    High-Level Abstraction: Achieved using Abstract Classes and Interfaces to separate the definition of a capability from its actual implementation.
//    Abstraction vs. Encapsulation (Interview Question):
//    Encapsulation = Data Security (Using private to prevent unauthorized access).
//    Abstraction = Complexity Hiding (Using abstract or interface so the user only sees what they need to see).

//Abstraction
//    Definition: A class declared with the abstract keyword. It represents a broad concept (e.g., Car), not a concrete entity.
//    Instantiation: You CANNOT create an object of an abstract class (new Car() throws an error). You must inherit it.
//    Abstract Methods: Methods declared without a body (e.g., abstract void accelerate();). If a class has even one abstract method, the entire class must be marked abstract.
//    The Contract: Any child class that extends the abstract class is forced to provide the implementation for all abstract methods, or it too becomes abstract.


// interfaces
//    Definition: The ultimate level of abstraction (in older Java versions, "Pure Abstraction"). It defines a "Contract" or "Role".
//    Naming Convention: Often ends in -able (e.g., Runnable, Callable, Flyable) because it defines a capability, not a family of objects.
//    Methods: By default, all methods inside an interface are public and abstract (no body).
//    Implementation: Classes use the implements keyword to sign the contract. A class can implement multiple interfaces, which is how Java solves the Multiple Inheritance (Diamond) problem.

//    Polymorphism allows objects to be treated as instances of their parent class or interface, and allows a single action to behave differently based on the object performing it.

//    Compile-Time Polymorphism (Method Overloading)
//    Mechanism: Multiple methods in the same class share the same name but have different parameters (e.g., run(), run(int speed)).
//    Why Compile-Time? The Java compiler knows exactly which method you intend to call the moment you type the code, based on the arguments you pass.
//
//    Run-Time Polymorphism (Method Overriding)
//    Mechanism: A child class provides its own specific implementation of a method already defined in its parent class (e.g., Dog overrides run() from Animal).
//
//    Dynamic Method Dispatch: You create a parent reference pointing to a child object:
//    Animal myPet = new Dog();
//
//    Why Run-Time? When you call myPet.run(), the compiler only checks if Animal has a run() method. It is only at Runtime, when the JVM looks at the actual Heap memory, that it realizes myPet is specifically a Dog, and executes the Dog's version of run().
//    Polymorphism Rules/Traps:
//
//    Static Methods: Cannot be overridden. They belong to the class, not the object.
//    Private Methods: Cannot be overridden. They are invisible to child classes.
//    Variables/Fields: Do NOT participate in polymorphism. If Parent and Child both have int x, myPet.x will always return the Parent's x.
}
