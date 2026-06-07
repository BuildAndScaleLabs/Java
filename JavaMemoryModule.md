# Java Stack, Heap, and Threads - Complete Notes

## Overview

To understand Java memory and multithreading, remember three core ideas:

1. Every Java application starts with a **main thread**.
2. Every thread gets its **own stack memory**.
3. All threads share the **same heap memory**.

---

# JVM Memory Structure

```text
JVM Process
│
├── Heap (Shared by all threads)
│
├── Main Thread Stack
│
├── Thread-1 Stack
│
├── Thread-2 Stack
│
└── Other JVM Thread Stacks
```

## Heap Memory

The heap is shared by all threads.

Objects created using `new` are stored here.

Example:

```java
Person p = new Person();
```

Memory:

```text
Stack                    Heap
-----                    ----

p ------------->     Person Object
```

The actual object lives in the heap.

---

## Stack Memory

Each thread gets its own private stack.

The stack stores:

* Method calls
* Method parameters
* Local variables
* Object references
* Return information

Each method call creates a new **stack frame**.

---

# Why Does Every Thread Need Its Own Stack?

Suppose two threads execute the same method:

```java
void printName(String name) {
    int length = name.length();
}
```

Thread 1:

```java
printName("John");
```

Thread 2:

```java
printName("Alice");
```

Both threads need their own values.

```text
Thread 1 Stack
--------------
name = John
length = 4

Thread 2 Stack
--------------
name = Alice
length = 5
```

If they shared a stack, their data would overwrite each other.

That is why every thread gets its own stack.

---

# What Is Stored Inside a Stack Frame?

Consider:

```java
static void test(int age) {

    int salary = 5000;

    Person p = new Person();
}
```

When `test()` executes:

```text
Stack Frame
-----------
age = 25
salary = 5000
p = reference
return information
```

---

## 1. Method Parameters

```java
test(25);
```

Parameter:

```java
int age
```

Stored inside the stack frame.

---

## 2. Local Variables

```java
int salary = 5000;
```

Stored inside the stack frame.

---

## 3. Object References

```java
Person p = new Person();
```

The object is NOT stored in the stack.

The stack stores only a reference.

```text
Stack                    Heap
-----                    ----

p ----------->      Person Object
```

Think of the reference as a way to locate the object.

---

## 4. Return Information

```java
main()
   |
   +--> test()
```

When `test()` finishes, Java must know where to continue execution.

This return information is stored in the stack frame.

---

# Method Call Stack

Example:

```java
main()
  |
  +--> methodA()
          |
          +--> methodB()
```

Stack:

```text
methodB()
methodA()
main()
```

When `methodB()` finishes:

```text
methodA()
main()
```

When `methodA()` finishes:

```text
main()
```

This is called LIFO (Last In, First Out).

---

# What Happens When a Method Ends?

Example:

```java
static void test() {
    int x = 10;
}
```

When the method completes:

* Stack frame is removed.
* Local variables disappear.
* Parameters disappear.
* References disappear.

The heap object may remain until Garbage Collection removes it.

---

# Main Thread

Every Java application starts with:

```java
public static void main(String[] args)
```

This method executes on the main thread.

Example:

```java
public static void main(String[] args) {
    methodA();
}
```

Execution:

```text
main thread
    |
    +--> main()
            |
            +--> methodA()
```

`methodA()` runs on the main thread.

---

# Which Thread Executes a Method?

Most important rule:

> A method runs on the thread that calls it.

Example:

```java
methodA();
```

If the main thread calls it:

```text
main thread -> methodA()
```

Then `methodA()` runs on the main thread.

---

# Creating Another Thread

```java
Thread t = new Thread(() -> {
    methodA();
});

t.start();
```

Execution:

```text
Main Thread
-----------
main()

Worker Thread
-------------
run()
methodA()
```

Now `methodA()` runs on the worker thread.

---

# Plain Java Rule

If you do NOT create a new thread:

```java
public static void main(String[] args) {
    methodA();
    methodB();
}
```

Then:

```text
methodA() -> main thread
methodB() -> main thread
```

Everything executes on the main thread.

---

# Frameworks Like Spring Boot

Frameworks often create threads internally.

Example:

```java
@GetMapping("/users")
public String getUsers() {
    return "users";
}
```

When an HTTP request arrives:

```text
Tomcat Worker Thread
        |
        +--> getUsers()
```

Even though you did not create a thread manually, the framework did.

Therefore, your method may execute on a framework-managed thread instead of the main thread.

---

# Heap vs Stack Summary

| Stack                                      | Heap                         |
| ------------------------------------------ | ---------------------------- |
| Per Thread                                 | Shared                       |
| Stores method calls                        | Stores objects               |
| Stores local variables                     | Stores object data           |
| Stores method parameters                   | Stores arrays                |
| Stores references                          | Stores instance variables    |
| Automatically cleaned after method returns | Cleaned by Garbage Collector |

---

# Interview Questions

### Q1. Does every thread have its own stack?

Yes.

Each thread gets a separate private stack.

---

### Q2. Is heap shared?

Yes.

All threads share the same heap.

---

### Q3. Where are objects stored?

Heap.

---

### Q4. Where are object references stored?

Usually inside stack frames (if they are local variables).

---

### Q5. Where are local variables stored?

Stack.

---

### Q6. What happens when a method finishes?

Its stack frame is removed.

---

### Q7. Which thread executes a method?

The thread that calls the method.

---

### Q8. If I don't create any thread, which thread executes my code?

The main thread.

---

# Golden Rules

1. Every thread has its own stack.
2. All threads share the heap.
3. Objects live in the heap.
4. References usually live in stack frames.
5. Method calls create stack frames.
6. When a method returns, its stack frame is removed.
7. A method executes on the thread that invokes it.
8. In plain Java, if you don't create another thread, your code runs on the main thread.
