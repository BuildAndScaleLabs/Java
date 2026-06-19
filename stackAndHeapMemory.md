# Stack vs Heap Memory in Java (Production-Level Notes)

## Why This Topic Matters

Understanding Stack and Heap memory helps explain:

* Object Creation
* Method Calls
* Garbage Collection
* Memory Leaks
* Pass By Value
* Multithreading
* StackOverflowError
* OutOfMemoryError

This is one of the most frequently asked Java interview topics.

---

# JVM Memory Overview

Simplified JVM memory:

```text
JVM Memory

├── Stack Memory
├── Heap Memory
├── Method Area (Metaspace)
├── PC Register
└── Native Method Stack
```

For most Java interviews, focus on:

```text
Stack
Heap
```

---

# Stack Memory

Stack memory stores:

* Method calls
* Local variables
* Method parameters
* Object references

Each thread gets its own stack.

---

# Stack Frame

Whenever a method is called, JVM creates a Stack Frame.

Example:

```java
public class 4.OperatorsALL.OperatorsALL.Main {

    public static void main(String[] args) {
        int age = 25;
        display();
    }

    static void display() {
        int number = 10;
    }
}
```

Memory:

```text
STACK

-------------------
display()
number = 10
-------------------

main()
age = 25
-------------------
```

When `display()` completes:

```text
display frame removed
```

Memory is automatically released.

---

# Characteristics of Stack Memory

## 1. Stores Local Variables

```java
int age = 25;
double salary = 50000;
```

Stored in stack.

---

## 2. Stores Method Parameters

```java
void print(String name)
```

`name` is stored in stack.

---

## 3. Stores Object References

```java
Person p = new Person();
```

Memory:

```text
STACK

p
```

Reference stored in stack.

Actual object stored in heap.

---

## 4. Thread Specific

Every thread gets its own stack.

```text
Thread-1 Stack

Thread-2 Stack

Thread-3 Stack
```

No sharing.

This makes stack operations very fast.

---

## 5. Automatic Cleanup

When a method finishes:

```java
public void test() {
    int x = 10;
}
```

The variable automatically disappears.

No Garbage Collector required.

---

# Heap Memory

Heap memory stores:

* Objects
* Arrays
* Instance Variables

---

# Object Creation

```java
Person person = new Person();
```

Memory:

```text
STACK

person
  │
  ▼

HEAP

Person Object
```

Reference → Stack

Object → Heap

---

# Example with Fields

```java
class Person {
    String name;
    int age;
}
```

Memory:

```text
HEAP

Person Object
 ├── name
 └── age
```

Instance variables live inside the object.

Objects live in Heap.

---

# Why Objects Are Stored in Heap

Suppose:

```java
public Person createPerson() {

    Person p = new Person();

    return p;
}
```

When the method ends:

```text
Stack frame removed
```

But returned object should still exist.

Therefore:

```text
Objects → Heap
References → Stack
```

---

# Multiple References

```java
Person p1 = new Person();

Person p2 = p1;
```

Memory:

```text
STACK

p1 ──┐
     │
     ▼

p2 ──┘

HEAP

Person Object
```

Both references point to the same object.

---

# Garbage Collection

Example:

```java
Person p = new Person();

p = null;
```

Memory:

```text
STACK

p = null
```

Object:

```text
HEAP

Person Object
```

No references exist.

Object becomes eligible for Garbage Collection.

---

# Important Point

Eligible for GC does NOT mean immediately deleted.

It means:

```text
Garbage Collector may remove it later.
```

---

# String Pool

String literals are stored in a special area called the String Pool.

Example:

```java
String s1 = "Java";
String s2 = "Java";
```

Memory:

```text
String Pool

"Java"
```

Both references point to the same object.

---

## Using new String()

```java
String s1 = new String("Java");
```

Memory:

```text
String Pool
 └── "Java"

Heap
 └── New String Object
```

A separate object is created in heap.

---

# Stack vs Heap During Object Creation

Example:

```java
Person p = new Person();
```

Step 1:

```text
Reference p created in Stack
```

Step 2:

```text
Person Object created in Heap
```

Step 3:

```text
Reference points to Object
```

Memory:

```text
STACK

p
│
▼

HEAP

Person Object
```

---

# Pass By Value in Java

Very important interview question.

Java is always Pass By Value.

Example:

```java
public static void main(String[] args) {

    Person p = new Person();

    update(p);
}

static void update(Person p) {
    p = new Person();
}
```

Many developers think original object changes.

Actually:

```text
Reference is copied.
```

Memory:

```text
main() stack frame

p ─────► Person A

update() stack frame

p ─────► Person A
```

A copy of the reference is passed.

Java never passes objects by reference.

Java passes:

```text
Copy of Primitive Value
Copy of Reference Value
```

---

# StackOverflowError

Occurs when Stack memory becomes full.

Most common cause:

Infinite recursion.

Example:

```java
void test() {
    test();
}
```

Memory:

```text
test()
test()
test()
test()
test()
...
```

New stack frame added every time.

Eventually:

```text
StackOverflowError
```

---

# OutOfMemoryError

Occurs when Heap memory becomes full.

Example:

```java
List<String> list = new ArrayList<>();

while(true) {
    list.add("Java");
}
```

Heap keeps growing.

Eventually:

```text
OutOfMemoryError: Java heap space
```

---

# Stack vs Heap in Multithreading

Stack:

```text
Thread Specific
```

Heap:

```text
Shared Between Threads
```

Example:

```text
Thread-1
      │
      ▼

      Heap

      ▲
      │

Thread-2
```

Because heap is shared:

```java
count++;
```

can create race conditions.

This is why synchronization is required.

---

# Interview Questions

## Where are Local Variables Stored?

```java
int age = 25;
```

Answer:

```text
Stack
```

---

## Where are Objects Stored?

```java
new Person()
```

Answer:

```text
Heap
```

---

## Where are References Stored?

```java
Person p = new Person();
```

Answer:

```text
Stack
```

---

## Which Memory is Thread Specific?

Answer:

```text
Stack
```

---

## Which Memory is Shared?

Answer:

```text
Heap
```

---

## Which Memory is Managed by GC?

Answer:

```text
Heap
```

---

## Which Memory is Faster?

Answer:

```text
Stack
```

Reason:

```text
Push / Pop operations
```

are extremely fast.

---

## What Causes StackOverflowError?

Answer:

```text
Too many stack frames
Usually recursion
```

---

## What Causes OutOfMemoryError?

Answer:

```text
Heap exhausted
Too many objects
```

---

# Stack vs Heap Comparison

| Feature       | Stack                       | Heap              |
| ------------- | --------------------------- | ----------------- |
| Stores        | Local Variables, References | Objects, Arrays   |
| Thread Safety | Thread Specific             | Shared            |
| Speed         | Faster                      | Slower            |
| Cleanup       | Automatic                   | Garbage Collector |
| Size          | Smaller                     | Larger            |
| Error         | StackOverflowError          | OutOfMemoryError  |

---

# Easy Memory Trick

```text
STACK

Method Calls
Local Variables
References

FAST
THREAD SPECIFIC

------------------------

HEAP

Objects
Arrays
Instance Variables

GC MANAGED
SHARED
```

---

# Final Summary

```java
Person person = new Person();
```

Memory:

```text
STACK

person (reference)
     │
     ▼

HEAP

Person Object
```

Remember:

```text
Stack → Method Calls, Local Variables, References

Heap → Objects, Arrays, Instance Variables

Stack Overflow → Too Many Method Calls

Heap OOM → Too Many Objects

Stack → One Per Thread

Heap → Shared Across Threads

Java → Always Pass By Value
```
