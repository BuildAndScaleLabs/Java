# Java Interfaces – Deep Dive

## 1. What is an Interface? → A Contract

* A **Class** is a blueprint for **objects**.
* An **Interface** is a blueprint for **behavior**.
* It defines a **contract**.

Any concrete class that implements an interface **must provide implementations for all abstract methods**. Otherwise, the implementing class itself must be declared `abstract`.

```java
interface Animal {
    void sound();
}

class Dog implements Animal {

    @Override
    public void sound() {
        System.out.println("Bark");
    }
}
```

If `Dog` does not implement `sound()`:

```java
abstract class Dog implements Animal {
}
```

---

## 2. Default Rules in an Interface

### Methods

Interface methods are implicitly:

```java
public abstract
```

So:

```java
void run();
```

Is internally treated as:

```java
public abstract void run();
```

### Variables

Interface variables are implicitly:

```java
public static final
```

So:

```java
int MAX_SPEED = 100;
```

Is internally:

```java
public static final int MAX_SPEED = 100;
```

Therefore:

* Interface variables are **constants**.
* You cannot have normal instance variables in an interface.

---

# 3. Types of Methods in an Interface

An interface can contain:

1. **Abstract methods**
2. **Default methods** → Java 8
3. **Static methods** → Java 8
4. **Private methods** → Java 9

---

## 4. Abstract Methods

By default, interface methods are `public` and `abstract`.

```java
interface Vehicle {
    void start();
}
```

The implementing class must provide an implementation:

```java
class Car implements Vehicle {

    @Override
    public void start() {
        System.out.println("Car Started");
    }
}
```

---

## 5. Default Methods → Java 8

### Problem Before Java 8

Suppose an existing interface has:

```java
interface A {
    void fun();
}
```

Later, a new method is added:

```java
void test();
```

Every existing class implementing `A` would break because it would now be required to implement `test()`.

### Solution: Default Methods

A default method has a method body:

```java
interface Vehicle {

    default void start() {
        System.out.println("Vehicle Started");
    }
}
```

Implementing classes automatically inherit it:

```java
class Car implements Vehicle {
}
```

```java
Car car = new Car();
car.start();
```

### Default Method Rules

* Has a method body.
* Is inherited by implementing classes.
* Can be overridden.
* Is **not required** to be overridden.

---

## 6. Static Methods → Java 8

Interfaces can contain static methods.

```java
interface Vehicle {

    static void stop() {
        System.out.println("Vehicle Stopped");
    }
}
```

Call it using the interface name:

```java
Vehicle.stop();
```

**Important:** Static interface methods are not inherited by implementing classes.

---

## 7. Private Methods → Java 9

Private methods allow common logic to be shared between methods inside the **same interface**.

```java
interface Vehicle {

    default void start() {
        validate();
        System.out.println("Started");
    }

    default void stop() {
        validate();
        System.out.println("Stopped");
    }

    private void validate() {
        System.out.println("Checking Vehicle");
    }
}
```

Private methods:

* Are helper methods.
* Can only be used inside the interface.
* Cannot be called from implementing classes.

---

# 8. Multiple Inheritance and the Diamond Problem

Java does **not allow multiple inheritance with classes**.

This is invalid:

```java
class C extends A, B {
}
```

The main reason is the **Diamond Problem**:

```text
        A
       / \
      B   C
       \ /
        D
```

If both `B` and `C` contain a method called `fun()`, Java would not know which method `D` should inherit.

However, Java allows multiple interface implementation:

```java
class D implements B, C {
}
```

---

## 9. Default Method Conflict

Consider:

```java
interface B {

    default void fun() {
        System.out.println("B");
    }
}

interface C {

    default void fun() {
        System.out.println("C");
    }
}
```

Now:

```java
class D implements B, C {
}
```

This causes a compiler error because both interfaces provide the same default method.

`D` must resolve the conflict explicitly:

```java
class D implements B, C {

    @Override
    public void fun() {
        B.super.fun();
    }
}
```

Or:

```java
class D implements B, C {

    @Override
    public void fun() {
        C.super.fun();
    }
}
```

### Important Rule

If two interfaces provide the same default method, the implementing class **must override the method and resolve the conflict**.

---

# 10. Interface vs Abstract Class

## Interface → Capability / "Can-Do"

Interfaces define what a class **can do**.

Examples:

* `Runnable` → Can run
* `Comparable` → Can compare
* `Payable` → Can be paid

A class can implement multiple capabilities:

```java
class Robot implements Runnable, Payable {
}
```

---

## Abstract Class → Identity / "Is-A"

Abstract classes represent a family of related objects that can share:

* Common state
* Instance variables
* Constructors
* Common behavior

Example:

```java
abstract class Animal {
    String name;
}

class Dog extends Animal {
}
```

### Important

A class can extend only **one class**:

```java
class Dog extends Animal {
}
```

But it can implement **multiple interfaces**:

```java
class Dog extends Animal
        implements Runnable, Comparable<Dog> {
}
```

### Simple Rule

> **Interface = What a class can do**
> **Abstract Class = What a class is**

---

# 11. When Should You Use an Interface?

Use an interface when different, unrelated classes need the same capability.

Example:

```java
interface Flyable {
    void fly();
}
```

Both can implement it:

```java
class Bird implements Flyable {
    public void fly() {
        System.out.println("Bird Flying");
    }
}

class Plane implements Flyable {
    public void fly() {
        System.out.println("Plane Flying");
    }
}
```

`Bird` and `Plane` are not part of the same object family, but both have the capability to **fly**.

---

# 12. Functional Interface

A **Functional Interface** has exactly **one abstract method**.

It can still contain:

* Multiple default methods
* Multiple static methods
* Private methods

Example:

```java
@FunctionalInterface
interface Calculator {

    int calculate(int a, int b);

    default void print() {
        System.out.println("Calculator");
    }

    static void info() {
        System.out.println("Utility Method");
    }
}
```

Functional interfaces enable **Lambda Expressions**:

```java
Calculator add = (a, b) -> a + b;

System.out.println(add.calculate(10, 20));
```

### Common Functional Interfaces

* `Runnable`
* `Comparator`
* `Callable`
* `Consumer`
* `Supplier`
* `Predicate`
* `Function`

---

## 13. `@FunctionalInterface`

`@FunctionalInterface` is optional but recommended.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
```

It tells the compiler:

> This interface should have only one abstract method.

If another abstract method is added:

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
    void test(); // ERROR
}
```

The compiler gives an error.

---

# 14. Marker Interface

A **Marker Interface** has **zero methods**.

It acts as a tag or metadata marker.

Examples:

* `Serializable`
* `Cloneable`

Example:

```java
class Student implements Cloneable {
}
```

`Cloneable` indicates that an object is allowed to be cloned.

Without it, calling `super.clone()` can result in:

```text
CloneNotSupportedException
```

### Marker Interface Summary

```text
ZERO methods
     ↓
Acts as a tag/marker
     ↓
Provides special meaning/behavior
```

---

# 15. Important: Interface Cannot Be Instantiated

This is invalid:

```java
Vehicle v = new Vehicle(); // ERROR
```

But an interface reference can refer to an implementing object:

```java
Vehicle v = new Car();
```

This is an example of **polymorphism**.

---

# 16. Interface Quick Memory Map

```text
INTERFACE
    |
    +-- Abstract Method
    |      -> public abstract by default
    |
    +-- Default Method
    |      -> Java 8
    |      -> Has method body
    |
    +-- Static Method
    |      -> Java 8
    |      -> Called using InterfaceName.method()
    |
    +-- Private Method
    |      -> Java 9
    |      -> Internal helper method
    |
    +-- Functional Interface
    |      -> Exactly ONE abstract method
    |      -> Supports Lambda Expressions
    |
    +-- Marker Interface
           -> ZERO methods
           -> Acts as a tag/marker
```

---

# 17. Most Important Interview Rules

1. An **interface defines a contract**.
2. A concrete implementing class must implement all abstract methods.
3. Otherwise, the implementing class must be declared `abstract`.
4. Interface methods are implicitly `public abstract`.
5. Interface variables are implicitly `public static final`.
6. Interface variables are constants.
7. Java 8 introduced **default methods**.
8. Java 8 introduced **static interface methods**.
9. Java 9 introduced **private interface methods**.
10. A class can implement multiple interfaces.
11. Java does not allow multiple inheritance of classes.
12. If two interfaces have the same default method, the implementing class must resolve the conflict.
13. Use interfaces for **capabilities / Can-Do behavior**.
14. Use abstract classes for **shared identity, state, and common behavior**.
15. A Functional Interface has exactly **one abstract method**.
16. A Functional Interface can still have default and static methods.
17. A Marker Interface has **zero methods**.
18. An interface cannot be instantiated directly.
19. An interface reference can point to an implementing object.
20. Functional Interfaces are the foundation for **Lambda Expressions**.
