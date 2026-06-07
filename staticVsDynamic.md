# Static Type vs Runtime Type

One of the most important Java concepts.

Used in:

* Polymorphism
* Method Overriding
* Casting
* instanceof
* Dynamic Dispatch

---

## Static Type

The type known by the compiler.

Example:

```java
Animal animal = new Dog();
```

Static Type:

```text
Animal
```

The compiler only sees:

```java
Animal animal
```

---

## Runtime Type

The actual object created in memory.

Example:

```java
Animal animal = new Dog();
```

Runtime Type:

```text
Dog
```

The JVM sees:

```java
new Dog()
```

---

## Example

```java
Animal animal = new Dog();
```

```text
Static Type  = Animal
Runtime Type = Dog
```

---

## Another Example

```java
Object obj = "Hello";
```

```text
Static Type  = Object
Runtime Type = String
```

---

## Method Overriding

```java
class Animal {
    void sound() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog");
    }
}
```

```java
Animal animal = new Dog();
animal.sound();
```

Output:

```text
Dog
```

Why?

Because overriding uses:

```text
Runtime Type
```

---

## instanceof Example

```java
Animal animal = new Dog();

System.out.println(animal instanceof Dog);
```

Output:

```text
true
```

Because instanceof checks:

```text
Runtime Type
```

---

## Casting Example

```java
Animal animal = new Dog();

Dog dog = (Dog) animal;
```

Works because runtime type is Dog.

---

## Invalid Cast

```java
Animal animal = new Animal();

Dog dog = (Dog) animal;
```

Output:

```text
ClassCastException
```

---

## Easy Memory Trick

```text
Static Type
=
Reference Variable Type

Runtime Type
=
Actual Object Type
```

---

## Final Summary

```java
Animal animal = new Dog();
```

```text
Static Type  = Animal
Runtime Type = Dog

Compiler Uses      → Static Type
JVM Uses           → Runtime Type
instanceof Uses    → Runtime Type
Overriding Uses    → Runtime Type
Casting Uses       → Runtime Type
```
