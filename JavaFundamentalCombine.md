# Java Fundamentals: JDK, JRE, JVM, Stack, Heap, and Threads

## Table of Contents

1. [Java Ecosystem: JDK, JRE, and JVM](#java-ecosystem-jdk-jre-and-jvm)
2. [How Java Code Runs](#how-java-code-runs)
3. [Why Java Is Platform Independent](#why-java-is-platform-independent)
4. [JVM Memory Structure](#jvm-memory-structure)
5. [Stack Memory](#stack-memory)
6. [Stack Frames](#stack-frames)
7. [Method Call Stack and LIFO](#method-call-stack-and-lifo)
8. [What Happens When a Method Ends](#what-happens-when-a-method-ends)
9. [Heap Memory](#heap-memory)
10. [Object Creation: Stack + Heap Together](#object-creation-stack--heap-together)
11. [Multiple References to the Same Object](#multiple-references-to-the-same-object)
12. [Garbage Collection](#garbage-collection)
13. [String Pool](#string-pool)
14. [Pass By Value in Java](#pass-by-value-in-java)
15. [Threads and Stack Memory](#threads-and-stack-memory)
16. [Which Thread Executes a Method?](#which-thread-executes-a-method)
17. [The Main Thread](#the-main-thread)
18. [Creating Another Thread](#creating-another-thread)
19. [Framework-Managed Threads](#framework-managed-threads)
20. [Stack vs Heap in Multithreading](#stack-vs-heap-in-multithreading)
21. [StackOverflowError](#stackoverflowerror)
22. [OutOfMemoryError](#outofmemoryerror)
23. [Production-Level Mental Model](#production-level-mental-model)
24. [Stack vs Heap Comparison](#stack-vs-heap-comparison)
25. [JDK, JRE, and JVM Quick Comparison](#jdk-jre-and-jvm-quick-comparison)
26. [Interview Questions and Answers](#interview-questions-and-answers)
27. [Golden Rules](#golden-rules)
28. [Final Revision Cheat Sheet](#final-revision-cheat-sheet)

---

# Java Ecosystem: JDK, JRE, and JVM

Before understanding Java memory and multithreading, it is useful to understand the three major terms in the Java ecosystem:

- **JVM** — Java Virtual Machine
- **JRE** — Java Runtime Environment
- **JDK** — Java Development Kit

A simplified conceptual relationship is:

```text
JDK
 ├── Runtime Environment
 │    ├── JVM
 │    └── Java Runtime Libraries
 │
 └── Development Tools
      ├── javac
      ├── jar
      ├── javadoc
      └── jdb
```

> **Modern Java note:** The classic relationship `JDK = JRE + development tools` is useful for learning the roles of these components. However, modern JDK distributions generally do not ship a separately installable JRE in the old Java 8-era sense. The important distinction is still that the JDK provides development capabilities, while the JVM is the engine that executes Java bytecode.

---

## JVM — Java Virtual Machine

The **JVM** is the runtime engine that executes Java bytecode.

It is responsible for important runtime activities such as:

- Loading classes
- Executing bytecode
- Managing memory
- Performing Garbage Collection
- Managing threads
- Providing the runtime environment required by Java applications

When you run:

```bash
java MyClass
```

the Java runtime launches the JVM and the JVM executes the application's bytecode.

Think of the JVM as the **engine that runs Java bytecode**.

---

## JRE — Java Runtime Environment

Conceptually:

```text
JRE
 ├── JVM
 └── Java Runtime Libraries
```

The runtime environment provides what is needed to **run** a Java application.

Examples of commonly used Java library classes include:

- `String`
- `ArrayList`
- `HashMap`
- `LocalDate`
- `Files`
- Stream APIs
- Collections APIs
- Threading APIs

For example:

```java
String name = "Sahil";

List<Integer> nums = new ArrayList<>();
```

These classes and APIs are provided by the Java platform's runtime libraries.

---

## JDK — Java Development Kit

The **JDK** is used to develop Java applications.

Conceptually:

```text
JDK
 ├── Runtime
 │    ├── JVM
 │    └── Java Libraries
 │
 └── Development Tools
      ├── javac
      ├── jar
      ├── javadoc
      └── jdb
```

Important development tools include:

- `javac` — Java compiler
- `jar` — creates and manages JAR files
- `javadoc` — generates API documentation
- `jdb` — Java debugger

---

# How Java Code Runs

Consider a Java source file:

```text
Test.java
```

## Step 1: Compile the Source Code

```bash
javac Test.java
```

The `javac` compiler is provided by the JDK.

The source code is compiled into bytecode:

```text
Test.java
    │
    ▼
javac
    │
    ▼
Test.class
```

---

## Step 2: Run the Bytecode

```bash
java Test
```

The Java runtime launches the JVM, which loads and executes the bytecode.

Conceptually:

```text
Test.java
    │
    ▼
javac (JDK)
    │
    ▼
Test.class
    │
    ▼
JVM
    │
    ▼
Native / machine-level execution
    │
    ▼
Program Runs
```

The exact execution path inside a modern JVM can involve interpretation and JIT compilation, but the important learning model is:

```text
Java Source
    ↓
Bytecode
    ↓
JVM
    ↓
Execution
```

---

# Why Java Is Platform Independent

Java source code is compiled into platform-neutral bytecode.

The JVM implementation for a particular operating system is responsible for executing that bytecode on that platform.

Conceptually:

```text
             Same Java Bytecode
                    │
        ┌───────────┼───────────┐
        ▼           ▼           ▼
 Windows JVM    Linux JVM    macOS JVM
        │           │           │
        ▼           ▼           ▼
Windows OS     Linux OS     macOS OS
```

Therefore, the same compiled Java application can run on different operating systems when a compatible JVM is available.

This idea is commonly summarized as:

```text
Write Once, Run Anywhere (WORA)
```

---

# Easy Memory Trick for JDK, JRE, and JVM

```text
JVM = Executes Java bytecode

JRE = Runtime environment
      JVM + runtime libraries
      (conceptual model)

JDK = Development kit
      Runtime + development tools
```

A simple way to remember the roles:

```text
JVM → Run the bytecode

JRE → Environment needed to run Java

JDK → Tools needed to develop Java
```

---

# JVM Memory Structure

A simplified view of JVM runtime memory is:

```text
JVM Memory

├── Heap
├── Thread Stack(s)
├── Method Area / Metaspace
├── PC Register
└── Native Method Stack
```

For most Java interviews and for understanding ordinary object creation and method execution, the two most important areas are:

```text
Stack
Heap
```

However, the JVM has more runtime memory areas than just these two.

---

## Core Thread and Memory Relationship

To understand Java memory and multithreading, remember these core ideas:

1. Every Java application starts execution with a **main thread**.
2. Every thread has its **own private JVM stack**.
3. Threads share the application's **heap**.
4. A method executes on the **thread that invokes it**.
5. A method invocation creates a stack frame for that thread.
6. Objects are generally allocated in the heap.
7. Local variables and references are associated with the executing method's stack frame when they are local variables.

A simplified JVM process can be visualized as:

```text
JVM Process
│
├── Heap
│     └── Shared by application threads
│
├── Main Thread Stack
│
├── Thread-1 Stack
│
├── Thread-2 Stack
│
└── Other JVM Thread Stacks
```

The important distinction is:

```text
Stack → Per Thread
Heap  → Shared
```

---

# Stack Memory

Each Java thread has its own private JVM stack.

The stack is used for method execution and contains stack frames associated with active method invocations.

A useful interview-level model is that stack frames contain:

- Method parameters
- Local variables
- Local object references
- Return information / execution state

> **Important precision:** The exact low-level layout of a stack frame is JVM-implementation dependent. The points above are the standard conceptual model used to understand Java programs.

---

## Why Does Every Thread Need Its Own Stack?

Suppose two threads execute the same method:

```java
void printName(String name) {
    int length = name.length();
}
```

Thread 1 calls:

```java
printName("John");
```

Thread 2 calls:

```java
printName("Alice");
```

The two executions need independent values:

```text
Thread-1 Stack
--------------
name   = John
length = 4

Thread-2 Stack
--------------
name   = Alice
length = 5
```

If both executions used the same stack frame, their local execution data could interfere with each other.

Instead, each thread has its own stack:

```text
Thread-1
   │
   └── Own Stack

Thread-2
   │
   └── Own Stack

Thread-3
   │
   └── Own Stack
```

This gives each thread its own execution context.

---

# What Is Stored Inside a Stack Frame?

Consider:

```java
static void test(int age) {

    int salary = 5000;

    Person p = new Person();
}
```

When `test()` is executing, a conceptual stack frame can contain:

```text
Stack Frame for test()
----------------------
age              = 25
salary           = 5000
p                = reference
return/execution information
```

The exact representation is JVM-specific, but conceptually the method's parameters, local variables, references, and execution state belong to that invocation.

---

## 1. Method Parameters

For:

```java
test(25);
```

the method parameter:

```java
int age
```

belongs to the `test()` invocation's stack frame.

Conceptually:

```text
Stack Frame
-----------
age = 25
```

---

## 2. Local Variables

For:

```java
int salary = 5000;
```

`salary` is a local variable associated with the method invocation.

Conceptually:

```text
Stack Frame
-----------
salary = 5000
```

---

## 3. Object References

Consider:

```java
Person p = new Person();
```

A useful mental model is:

```text
Stack                         Heap
-----                         ----

p  ----------------------->  Person Object
```

The local variable `p` is a reference associated with the stack frame.

The `Person` object itself is allocated in the heap.

So remember:

```text
Local reference → Stack frame
Actual object   → Heap
```

This distinction is extremely important.

---

## 4. Return Information / Execution State

Consider:

```java
main()
   |
   +--> test()
```

When `test()` finishes, execution must resume in the caller.

The stack frame represents the execution state needed to return from the method and continue the caller.

Conceptually:

```text
main()
  |
  +--> test()
          |
          +--> return to main()
```

---

# Stack Frames

Whenever a method is called, a new stack frame is created for that method invocation.

Example:

```java
public class Main {

    public static void main(String[] args) {
        int age = 25;
        display();
    }

    static void display() {
        int number = 10;
    }
}
```

While `display()` is executing, a conceptual stack is:

```text
STACK

-------------------------
display()
number = 10
-------------------------

main()
age = 25
-------------------------
```

When `display()` completes:

```text
display() frame removed
```

The `main()` frame remains active.

The important lifecycle is:

```text
Method call
    ↓
New stack frame
    ↓
Method executes
    ↓
Method returns
    ↓
Frame is removed
```

---

# Method Call Stack and LIFO

Consider:

```java
main()
  |
  +--> methodA()
          |
          +--> methodB()
```

The active call stack can be represented as:

```text
methodB()
methodA()
main()
```

`methodB()` is at the top because it is the currently executing method.

When `methodB()` finishes:

```text
methodA()
main()
```

When `methodA()` finishes:

```text
main()
```

This follows:

```text
LIFO
Last In, First Out
```

The last method invocation added to the call stack is the first one to return.

---

# What Happens When a Method Ends?

Consider:

```java
static void test() {
    int x = 10;
}
```

When `test()` completes:

- The `test()` stack frame is removed.
- Its local variables are no longer part of that active invocation.
- Its parameters are no longer part of that active invocation.
- Its local references disappear with the frame.
- Execution returns to the caller.

For example:

```text
main()
   |
   +--> test()
```

After `test()` returns:

```text
main()
```

The stack frame is gone.

---

## What About Heap Objects?

Suppose:

```java
static void test() {
    Person p = new Person();
}
```

While `test()` is running:

```text
Stack

p ──────────────► Heap
                  Person Object
```

When `test()` returns, the local reference `p` disappears with the stack frame.

If no other live reference points to the object, the object may become eligible for Garbage Collection.

Therefore:

```text
Stack frame removed
        ↓
Local reference disappears
        ↓
Object may become unreachable
        ↓
Object becomes eligible for GC
```

The object is **not necessarily deleted immediately**.

---

# Heap Memory

The heap is the shared runtime memory area used for objects and arrays.

A common interview-level model is:

```text
Heap stores:
- Objects
- Arrays
- Instance data contained in objects
```

Example:

```java
Person person = new Person();
```

Conceptually:

```text
STACK

person
   │
   ▼

HEAP

Person Object
```

The local reference and the object are different things.

```text
Reference → Stack frame
Object    → Heap
```

---

# Object Creation

Consider:

```java
Person p = new Person();
```

A useful step-by-step model is:

### Step 1: A local reference is associated with the current stack frame

```text
Stack

p
```

### Step 2: The `new` expression creates a `Person` object

```text
Heap

Person Object
```

### Step 3: The reference points to the object

```text
Stack                         Heap

p  ----------------------->  Person Object
```

This is the core stack-vs-heap picture to remember.

---

# Objects and Instance Variables

Consider:

```java
class Person {
    String name;
    int age;
}
```

When a `Person` object exists, its instance state is part of the object:

```text
HEAP

Person Object
 ├── name
 └── age
```

The object and its instance data belong to the heap allocation.

If the local variable is:

```java
Person p = new Person();
```

then:

```text
STACK                         HEAP

p  ----------------------->  Person Object
                              ├── name
                              └── age
```

---

# Why Are Objects Allocated in the Heap?

Consider:

```java
public Person createPerson() {

    Person p = new Person();

    return p;
}
```

The local reference `p` belongs to the method's execution frame.

When `createPerson()` returns:

```text
createPerson() stack frame
        ↓
      removed
```

But the returned object must still be available to the caller.

Conceptually:

```text
Before return:

createPerson() frame
p ───────────────► Person Object
                   (Heap)


After return:

Caller
returned reference ─────► Person Object
                          (Heap)
```

This illustrates why the lifetime of an object can extend beyond the lifetime of the stack frame that originally held its reference.

---

# Multiple References to the Same Object

Consider:

```java
Person p1 = new Person();

Person p2 = p1;
```

There is only one `Person` object.

Both references point to it:

```text
STACK

p1 ──┐
     │
     ├──────────────► HEAP
     │                Person Object
p2 ──┘
```

So:

```text
p1 == p2
```

is `true` because both references refer to the same object.

This also matters for Garbage Collection: an object remains reachable as long as a live reference path can reach it.

---

# Garbage Collection

Java automatically manages heap memory through Garbage Collection.

Consider:

```java
Person p = new Person();

p = null;
```

After:

```java
p = null;
```

the local variable no longer points to the object:

```text
STACK

p = null
```

The previously created object may now look like:

```text
HEAP

Person Object
(no reference from p)
```

If there are no other reachable references to that object, it becomes **eligible for Garbage Collection**.

---

## Eligible for GC Does Not Mean Immediately Deleted

This is an important distinction.

When an object becomes unreachable:

```text
Object becomes eligible for GC
```

It does **not** mean:

```text
Object is immediately deleted
```

Instead:

```text
Eligible for GC
        ↓
Garbage Collector may reclaim it later
```

The exact timing of collection is controlled by the JVM and its garbage collector.

---

# String Pool

String literals are handled specially by the Java runtime through the **String Pool**, which is associated with the heap.

Consider:

```java
String s1 = "Java";
String s2 = "Java";
```

The same interned string value can be reused:

```text
String Pool

"Java"
  ▲
  │
  ├──────── s1
  │
  └──────── s2
```

Conceptually:

```text
s1 ──┐
     ├──► "Java"
s2 ──┘
```

Both references can point to the same pooled string object.

---

## Using `new String()`

Consider:

```java
String s1 = new String("Java");
```

Conceptually:

```text
String Pool
 └── "Java"

Heap
 └── New String Object
```

The string literal `"Java"` is pooled, while `new String("Java")` explicitly creates a separate `String` object.

This is why `new String(...)` should not be confused with using a string literal.

---

# Pass By Value in Java

One of the most important Java interview topics is parameter passing.

## Java Is Always Pass By Value

Java is always **pass by value**.

This applies to:

- Primitive values
- Object references

For objects, Java passes a **copy of the reference value**.

Java does not pass the object itself by reference in the C++-style sense of pass-by-reference semantics.

---

## Example

```java
public static void main(String[] args) {

    Person p = new Person();

    update(p);
}

static void update(Person p) {
    p = new Person();
}
```

When calling:

```java
update(p);
```

the reference value is copied.

Conceptually:

```text
main() stack frame

p ─────────► Person A


update() stack frame

p ─────────► Person A
```

The two `p` variables are different local variables.

Initially, they contain equivalent reference values, so both point to `Person A`.

Inside `update()`:

```java
p = new Person();
```

the `update()` parameter is changed to point to another object.

That does not replace the caller's `p`.

Conceptually:

```text
main() frame

p ─────────────► Person A


update() frame

p ─────────────► Person B
```

The caller's reference remains unchanged.

---

## The Core Rule

Remember:

```text
Primitive argument:
Copy of primitive value

Object argument:
Copy of reference value
```

Therefore:

```text
Java → Always Pass By Value
```

This distinction is essential when explaining Java method calls.

---

# Threads and Stack Memory

Now connect stack and heap memory to multithreading.

The core relationship is:

```text
Each Thread
     ↓
Own Stack

All Application Threads
     ↓
Shared Heap
```

For example:

```text
Thread-1
   │
   ▼
Own Stack
   │
   └─────────────┐
                 │
Thread-2         │
   │             │
   ▼             │
Own Stack        │
                 ▼
              Shared Heap
```

The stack is thread-specific because each thread needs an independent method-execution context.

The heap is shared so objects can be accessed by multiple threads when references to those objects are shared.

---

# The Main Thread

Every ordinary Java application begins execution with the `main` thread.

The entry point is:

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

Execution can be visualized as:

```text
Main Thread
    |
    +--> main()
            |
            +--> methodA()
```

`methodA()` runs on the main thread because the main thread called it.

---

# Which Thread Executes a Method?

One of the most important rules in Java is:

> **A method runs on the thread that calls it.**

Consider:

```java
methodA();
```

If the main thread calls it:

```text
Main Thread
    │
    └──► methodA()
```

then `methodA()` executes on the main thread.

The method itself does not automatically create a new thread.

---

## Plain Java Example

Consider:

```java
public static void main(String[] args) {
    methodA();
    methodB();
}
```

If no other thread is explicitly involved:

```text
Main Thread
    │
    ├──► methodA()
    │
    └──► methodB()
```

Therefore:

```text
methodA() → main thread
methodB() → main thread
```

Simply having multiple methods does **not** mean multiple threads are being used.

---

# Creating Another Thread

A new thread can be created explicitly.

Example:

```java
Thread t = new Thread(() -> {
    methodA();
});

t.start();
```

The important operation here is:

```java
t.start();
```

The JVM schedules the new thread, and its task runs on that worker thread.

Conceptually:

```text
Main Thread
-----------
main()


Worker Thread
-------------
run()
methodA()
```

Now:

```text
methodA() → Worker Thread
```

because the worker thread executes the lambda passed to the `Thread`.

---

## `start()` vs Calling a Method Directly

This distinction is important.

Direct method call:

```java
methodA();
```

means:

```text
Current Thread
      │
      └──► methodA()
```

Starting a thread:

```java
t.start();
```

means a separate thread is started to execute the thread's task.

So:

```text
methodA()
→ runs on caller's thread


t.start()
→ starts another thread
```

The key principle remains:

```text
A method runs on the thread that invokes it.
```

---

# Framework-Managed Threads

In production applications, you may not explicitly create every thread yourself.

Frameworks and application servers often manage thread pools and worker threads.

For example, consider a Spring-style HTTP endpoint:

```java
@GetMapping("/users")
public String getUsers() {
    return "users";
}
```

When an HTTP request arrives, a server-managed worker thread may execute the controller method:

```text
HTTP Request
     │
     ▼
Server / Framework Worker Thread
     │
     └──► getUsers()
```

So even though your code did not contain:

```java
new Thread(...)
```

the method can still execute on a non-main thread.

The general rule does not change:

```text
Framework-managed thread
        │
        └──► Your method
```

Therefore, when debugging production applications, it is important to identify **which thread is executing the code**, not just which class or method contains the code.

---

# Stack vs Heap in Multithreading

This is where the memory model and multithreading concepts connect directly.

Suppose two threads access a shared object:

```text
Thread-1 Stack
      │
      │ reference
      ▼
   Shared Object
      ▲
      │ reference
      │
Thread-2 Stack
```

The stacks are separate:

```text
Thread-1 → Own Stack
Thread-2 → Own Stack
```

But the object can be shared through the heap:

```text
Shared Heap Object
```

This means:

```text
Thread-specific execution data
        +
Shared object state
```

can exist at the same time.

---

## Why Shared Heap State Can Cause Race Conditions

Suppose multiple threads operate on shared mutable state:

```java
count++;
```

If `count` is shared between threads, the operation can involve multiple underlying steps such as:

```text
Read count
    ↓
Add 1
    ↓
Write count
```

Two threads can interleave those operations.

Conceptually:

```text
Thread-1        Thread-2

Read count
                Read count
Add 1
                Add 1
Write count
                Write count
```

Depending on the timing, an update can be lost.

This is a **race condition**.

Therefore, shared heap state often requires appropriate concurrency mechanisms such as synchronization, locks, atomic classes, or other thread-safe designs.

The key memory connection is:

```text
Stack → private execution context
Heap  → potentially shared object state
```

---

# Stack vs Heap: Complete Comparison

| Feature | Stack | Heap |
|---|---|---|
| Main purpose | Method execution | Object and array storage |
| Ownership | Each thread has its own stack | Shared by application threads |
| Typical contents | Stack frames, local variables, parameters, local references, execution state | Objects, arrays, instance data |
| Method calls | Yes | No |
| Local variables | Associated with stack frames | Not generally stored as independent heap variables |
| Local object references | Associated with stack frames | The referenced object is in the heap |
| Objects | Object itself is not stored in the local stack frame | Objects are generally allocated here |
| Arrays | Reference may be local; array object is in heap | Array objects |
| Instance variables | Part of heap objects | Stored as part of their objects |
| Cleanup | Frames are removed when invocations return | Objects are reclaimed by GC when no longer reachable |
| Thread relationship | Thread-specific | Shared |
| Typical access model | Very fast stack-frame operations | Managed object allocation/access |
| Common error | `StackOverflowError` | `OutOfMemoryError` such as heap exhaustion |
| Typical size | Usually smaller and thread-specific | Usually much larger and shared |

> **Important:** Saying "stack is faster than heap" is a useful interview-level simplification, but actual performance depends on JVM implementation, allocation strategy, JIT optimization, object lifetime, cache behavior, and workload.

---

# Stack Memory: Important Characteristics

## 1. Stores Method Execution Data

A stack is associated with method invocations:

```text
main()
  ↓
methodA()
  ↓
methodB()
```

Each active invocation has its own frame.

---

## 2. Stores Local Variables and Parameters

Example:

```java
void calculate(int age) {
    int salary = 5000;
}
```

Conceptually:

```text
calculate() frame
-----------------
age
salary
```

---

## 3. Stores Local References

Example:

```java
Person p = new Person();
```

Conceptually:

```text
Stack frame

p ─────────► Heap object
```

The local reference is associated with the frame.

---

## 4. Thread Specific

Each thread gets its own stack:

```text
Thread-1 → Stack-1
Thread-2 → Stack-2
Thread-3 → Stack-3
```

This prevents one thread's stack frames from being directly shared with another thread.

---

## 5. Automatic Frame Cleanup

When a method returns:

```text
Method returns
     ↓
Stack frame removed
```

No Garbage Collector is required to remove the stack frame itself.

---

# Heap Memory: Important Characteristics

The heap is shared and is used for dynamically allocated objects and arrays.

Typical examples include:

```java
new Person();
new int[100];
new ArrayList<>();
new HashMap<>();
```

Conceptually:

```text
Heap
├── Person object
├── int array
├── ArrayList object
└── HashMap object
```

The garbage collector manages reclaiming memory occupied by objects that are no longer reachable.

---

# StackOverflowError

A `StackOverflowError` occurs when a thread's stack cannot accommodate additional stack frames.

A common cause is infinite or excessively deep recursion.

Example:

```java
void test() {
    test();
}
```

Each call creates another conceptual frame:

```text
test()
test()
test()
test()
test()
...
```

Eventually, the stack is exhausted:

```text
StackOverflowError
```

The important cause is:

```text
Too many / too deeply nested stack frames
```

Infinite recursion is one of the most common causes.

---

# OutOfMemoryError

An `OutOfMemoryError` can occur when the JVM cannot satisfy a memory allocation.

One common example is exhausting the Java heap.

Consider:

```java
List<String> list = new ArrayList<>();

while (true) {
    list.add("Java");
}
```

The list continues to retain more elements, causing memory usage to grow.

Eventually, the JVM may report:

```text
OutOfMemoryError: Java heap space
```

The important interview-level distinction is:

```text
Too many active/deep stack frames
        ↓
StackOverflowError

Heap cannot satisfy required allocation
        ↓
OutOfMemoryError
```

> `OutOfMemoryError` is broader than only heap exhaustion; Java has multiple runtime memory areas, and different memory-exhaustion situations can produce different messages. `Java heap space` specifically indicates heap exhaustion.

---

# Memory Leak Concept in Java

Java has Garbage Collection, but that does not mean an application can never have a memory leak.

A practical Java memory leak occurs when an application unintentionally keeps references to objects that it no longer needs.

For example, if a long-lived collection continually retains objects:

```java
static List<Person> cache = new ArrayList<>();

void addPerson(Person person) {
    cache.add(person);
}
```

If entries are never removed and the collection remains reachable, the referenced objects remain reachable too.

Conceptually:

```text
Long-lived object
      │
      └──► Collection
              │
              ├──► Unneeded Object A
              ├──► Unneeded Object B
              └──► Unneeded Object C
```

The garbage collector cannot reclaim reachable objects merely because the application no longer logically needs them.

This is why understanding references, heap memory, and Garbage Collection helps explain **memory leaks**.

---

# How Stack and Heap Explain Common Java Topics

Stack and heap are not isolated interview topics. They help explain several important Java behaviors.

## Object Creation

```java
Person p = new Person();
```

```text
Reference → Stack frame
Object    → Heap
```

---

## Method Calls

```java
methodA();
```

creates an active invocation frame for `methodA()` on the calling thread.

---

## Garbage Collection

Objects in the heap can become eligible for GC when they are no longer reachable.

---

## Pass By Value

When an object reference is passed to a method:

```text
Reference value is copied
```

The caller and callee can have separate local reference variables pointing to the same object.

---

## Multithreading

```text
Each thread → own stack
All threads → shared heap
```

This explains why local method state is naturally isolated per thread while shared mutable objects can require synchronization.

---

## StackOverflowError

Too many stack frames can exhaust a thread's stack.

---

## OutOfMemoryError

Exhausting heap capacity can result in:

```text
OutOfMemoryError: Java heap space
```

---

# Production-Level Mental Model

A useful way to reason about a Java application is to separate four questions:

## Question 1: Which Thread Is Running This Code?

Ask:

```text
Which thread called this method?
```

Examples:

```text
main thread
worker thread
executor thread
server request thread
scheduler thread
```

A method runs on the thread that invokes it.

---

## Question 2: What Is the Current Method Call Stack?

For:

```text
main()
  ↓
service()
  ↓
repository()
```

the current thread may have:

```text
repository()
service()
main()
```

Each active method invocation contributes to the thread's call stack.

---

## Question 3: Which Data Is Local to the Thread?

Local variables and parameters belong to the current method invocation.

For example:

```java
void process(int id) {
    String result = "done";
}
```

Conceptually:

```text
Thread-1 Stack
----------------
id
result
```

Another thread executing the same method has its own frame:

```text
Thread-2 Stack
----------------
id
result
```

Their local execution data is independent.

---

## Question 4: Which Objects Are Shared?

If two threads hold references to the same heap object:

```text
Thread-1 Stack
      │
      └──────┐
             ▼
          Heap Object
             ▲
      ┌──────┘
      │
Thread-2 Stack
```

then both threads can potentially observe or modify the same object state.

This is where concurrency concerns such as:

- Race conditions
- Synchronization
- Visibility
- Atomicity
- Thread safety

become important.

---

# Example: Putting Everything Together

Consider:

```java
class Person {
    String name;
    int age;
}

public class Main {

    public static void main(String[] args) {

        Person person = new Person();

        person.name = "John";
        person.age = 30;

        printPerson(person);
    }

    static void printPerson(Person person) {
        System.out.println(person.name);
    }
}
```

A simplified memory model while `printPerson()` executes is:

```text
MAIN THREAD STACK

main()
--------------------------------
person ──────────────────────┐
                             │
                             ▼
                          HEAP
                     Person Object
                     ├── name = "John"
                     └── age  = 30
                             ▲
                             │
printPerson() frame          │
--------------------------------
person ──────────────────────┘
```

Important observations:

1. `main()` is running on the main thread.
2. `main()` has a local reference named `person`.
3. The `Person` object is in the heap.
4. `printPerson()` creates another stack frame.
5. The `person` parameter in `printPerson()` receives a copy of the reference value.
6. Both references point to the same `Person` object.
7. When `printPerson()` returns, its frame is removed.
8. The object remains in the heap because `main()` still has a reference to it.
9. When the object eventually becomes unreachable, it can become eligible for Garbage Collection.

---

# Example: Two Threads Sharing One Object

Consider:

```java
class Counter {
    int count;
}

Counter counter = new Counter();
```

Suppose two threads have access to `counter`.

Conceptually:

```text
Thread-1 Stack
counter reference ─────┐
                       │
                       ▼
                    Heap
                 Counter Object
                    count
                       ▲
                       │
Thread-2 Stack         │
counter reference ─────┘
```

The stacks remain separate, but both threads can access the same heap object.

If both threads execute:

```java
counter.count++;
```

without appropriate synchronization, the shared mutable state can create a race condition.

The important lesson is:

```text
Separate stacks do NOT mean the entire program state is isolated.

Heap objects can still be shared.
```

---

# Example: Method Execution Across Threads

Consider:

```java
static void methodA() {
    methodB();
}

static void methodB() {
    System.out.println("Hello");
}
```

If the main thread executes:

```java
methodA();
```

the call stack is conceptually:

```text
Main Thread Stack

methodB()
methodA()
main()
```

If a worker thread executes:

```java
methodA();
```

then the worker has its own stack:

```text
Worker Thread Stack

methodB()
methodA()
run()
```

The same Java method can therefore execute on different threads at different times.

The method's code is the same, but the **thread and stack frame are different**.

---

# Example: Same Method, Two Threads

Consider:

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

Conceptually:

```text
Thread-1 Stack
----------------
printName()
name   = John
length = 4
----------------


Thread-2 Stack
----------------
printName()
name   = Alice
length = 5
----------------
```

Both threads execute the same method code.

However, each invocation has its own stack frame and local values.

---

# Interview Questions and Answers

## Q1. Does every thread have its own stack?

**Yes.**

Each thread has its own private JVM stack used for its method execution.

```text
Thread-1 → Stack-1
Thread-2 → Stack-2
Thread-3 → Stack-3
```

---

## Q2. Is the heap shared?

**Yes.**

The heap is shared among the application's threads.

```text
Thread-1 ──┐
Thread-2 ──┼──► Shared Heap
Thread-3 ──┘
```

---

## Q3. Where are objects stored?

Objects are generally allocated in the **heap**.

Example:

```java
new Person();
```

```text
Heap → Person Object
```

---

## Q4. Where are object references stored?

For a local variable, the reference is associated with the current method's stack frame.

Example:

```java
Person p = new Person();
```

Conceptually:

```text
Stack frame → p (reference)
Heap        → Person object
```

> Do not generalize this to every reference in Java. For example, an object field such as `person.address` is part of an object on the heap. The interview-safe distinction is that **local references belong to stack frames, while referenced objects are on the heap**.

---

## Q5. Where are local variables stored?

At the conceptual interview level, local variables belong to the executing method's stack frame.

Example:

```java
int age = 25;
```

```text
Current stack frame → age
```

The exact JVM/JIT implementation can optimize where values physically reside, so this is a conceptual model rather than a guarantee about physical machine storage.

---

## Q6. What happens when a method finishes?

Its active stack frame is removed.

Therefore:

- Local variables associated with that invocation disappear.
- Parameters associated with that invocation disappear.
- Local references disappear with the frame.
- Execution returns to the caller.

Any heap object previously referenced by the frame may remain alive if another reachable reference still points to it.

---

## Q7. Which thread executes a method?

The **thread that invokes the method** executes it.

```text
Caller Thread
     ↓
  method()
```

---

## Q8. If I don't create another thread, which thread executes my code?

In ordinary application startup, your code begins on the **main thread**.

If the main thread calls:

```java
methodA();
methodB();
```

then both methods execute on the main thread unless some other concurrency mechanism is introduced.

---

## Q9. Does calling a method create a new thread?

**No.**

A normal method call:

```java
methodA();
```

runs on the current thread.

A separate thread must be created or supplied by some concurrency mechanism, such as:

```java
new Thread(...)
```

or an executor/thread pool/framework-managed worker.

---

## Q10. What is the difference between `start()` and a normal method call?

A normal call:

```java
methodA();
```

executes on the current thread.

Starting a `Thread`:

```java
t.start();
```

causes the thread to begin execution independently of the caller's current call stack.

---

## Q11. Which memory is thread-specific?

**Stack memory.**

Each thread has its own stack.

---

## Q12. Which memory is shared?

The **heap** is shared among application threads.

---

## Q13. Which memory is managed by Garbage Collection?

The heap is the primary memory area managed for object reclamation by the Garbage Collector.

Stack frames are not removed by the Garbage Collector; they are associated with method execution and disappear when method invocations return.

---

## Q14. Which memory is faster?

The stack is often described as faster for basic stack-frame operations because its allocation and lifetime are structured around method calls.

However, avoid treating:

```text
Stack = always fast
Heap = always slow
```

as an absolute JVM performance law.

Modern JVMs use sophisticated allocation, optimization, JIT compilation, escape analysis, and garbage collection techniques.

---

## Q15. What causes `StackOverflowError`?

Typically:

```text
Too many / excessively deep stack frames
```

A common cause is infinite recursion:

```java
void test() {
    test();
}
```

---

## Q16. What causes `OutOfMemoryError`?

An `OutOfMemoryError` occurs when the JVM cannot satisfy a required memory allocation.

A common example is heap exhaustion:

```text
OutOfMemoryError: Java heap space
```

It can happen when an application retains too many objects or otherwise requires more heap than the JVM can provide.

---

## Q17. Is Java pass by reference?

**No.**

Java is always pass by value.

For objects, the value being copied is the reference value.

```text
Object parameter
       ↓
Copy of reference value
       ↓
Both references may point to same object
```

---

## Q18. If two variables reference the same object, is there one object or two?

There is **one object** and multiple references.

Example:

```java
Person p1 = new Person();
Person p2 = p1;
```

Conceptually:

```text
p1 ──┐
     ├──► One Person Object
p2 ──┘
```

---

## Q19. Does an unreachable object get deleted immediately?

**No.**

It becomes eligible for Garbage Collection.

```text
Unreachable
    ↓
Eligible for GC
    ↓
May be reclaimed later
```

---

## Q20. Can Java applications have memory leaks even with Garbage Collection?

**Yes.**

If an application unintentionally keeps objects reachable, the Garbage Collector cannot reclaim them.

A common example is an ever-growing long-lived collection:

```java
static List<Person> cache = new ArrayList<>();
```

If objects are continually added and never removed, memory usage can grow even though GC is working correctly.

---

## Q21. If two threads use the same method, do they share local variables?

**No.**

Each invocation has its own stack frame.

For example:

```text
Thread-1
printName("John")
    ↓
Own frame


Thread-2
printName("Alice")
    ↓
Own frame
```

Their local parameters and local variables are independent.

However, if those local variables contain references to the same heap object, both threads can still access shared object state.

---

## Q22. Why can shared heap objects cause race conditions?

Because multiple threads can access and modify the same object state.

For example:

```java
count++;
```

If `count` is shared mutable state, multiple threads can interleave their operations.

This is why shared state often needs proper concurrency control.

---

# JDK, JRE, and JVM Quick Comparison

| Component | Main Role | Important Contents / Responsibilities |
|---|---|---|
| JVM | Executes Java bytecode | Class loading, bytecode execution, memory management, GC, thread management |
| JRE | Runtime environment | JVM + Java runtime libraries in the conceptual model |
| JDK | Java development kit | Runtime capabilities + development tools such as `javac`, `jar`, `javadoc`, `jdb` |

Easy way to remember:

```text
JVM → Executes
JRE → Runs
JDK → Develops
```

---

# Stack vs Heap Quick Comparison

| Stack | Heap |
|---|---|
| Per thread | Shared |
| Method execution | Object storage |
| Stack frames | Objects |
| Local variables | Arrays |
| Method parameters | Instance data |
| Local references | Objects referenced by those references |
| Frame removed when method returns | Objects reclaimed when no longer reachable and GC runs |
| Can produce `StackOverflowError` | Can produce `OutOfMemoryError: Java heap space` |

---

# JDK → JVM → Memory: Connecting the Whole Picture

All of these topics fit together.

Start with source code:

```text
Java Source
    │
    ▼
JDK tools
    │
    ▼
Bytecode
    │
    ▼
JVM
    │
    ├── Creates/manages runtime threads
    │
    ├── Provides thread stacks
    │
    ├── Manages heap
    │
    ├── Loads classes
    │
    └── Executes bytecode
```

During application execution:

```text
JVM Process
│
├── Main Thread
│     └── Main Thread Stack
│
├── Worker Thread
│     └── Worker Thread Stack
│
├── Other Threads
│     └── Their own stacks
│
└── Shared Heap
      ├── Objects
      ├── Arrays
      └── Shared application state
```

This gives a complete conceptual chain:

```text
JDK
 ↓
Compile Java source
 ↓
Bytecode
 ↓
JVM
 ↓
Threads execute bytecode
 ↓
Each thread gets its own stack
 ↓
Threads can share heap objects
 ↓
Garbage Collector manages unreachable heap objects
```

---

# Golden Rules

Keep these rules in mind for interviews and production debugging:

1. **Every Java application starts execution with a main thread.**
2. **Every thread has its own private JVM stack.**
3. **The heap is shared among application threads.**
4. **Method calls create stack frames for the executing thread.**
5. **Each active method invocation has its own execution frame.**
6. **Local variables and method parameters belong conceptually to the current stack frame.**
7. **Local object references are associated with the current stack frame.**
8. **The object referred to by a local reference is generally allocated in the heap.**
9. **Objects and arrays are generally allocated in the heap.**
10. **Instance state is part of the corresponding heap object.**
11. **When a method returns, its stack frame is removed.**
12. **Removing a stack frame does not automatically delete heap objects referenced by that frame.**
13. **An object becomes eligible for GC when it is no longer reachable.**
14. **Eligible for GC does not mean immediately deleted.**
15. **Java is always pass by value.**
16. **For objects, Java passes a copy of the reference value.**
17. **A normal method call does not create a new thread.**
18. **A method executes on the thread that invokes it.**
19. **If the main thread calls a method directly, that method runs on the main thread.**
20. **Frameworks and application servers can execute your methods on managed worker threads.**
21. **Two threads can execute the same method while using different stack frames.**
22. **Separate stacks do not prevent threads from sharing heap objects.**
23. **Shared mutable heap state can cause race conditions.**
24. **`StackOverflowError` commonly results from excessively deep or infinite recursion.**
25. **`OutOfMemoryError: Java heap space` can result from heap exhaustion.**
26. **Garbage Collection manages heap object reclamation; it does not remove ordinary stack frames.**
27. **JVM is the engine that executes Java bytecode.**
28. **JDK provides Java development tools.**
29. **The JRE is best understood as the Java runtime environment; modern JDKs generally do not provide a separate installable JRE in the old Java 8 model.**
30. **Java's platform independence comes from running bytecode on a platform-specific JVM.**

---

# Final Revision Cheat Sheet

## JDK / JRE / JVM

```text
JDK
│
├── Development Tools
│   ├── javac
│   ├── jar
│   ├── javadoc
│   └── jdb
│
└── Runtime
    ├── JVM
    └── Java Runtime Libraries
```

Remember:

```text
JDK → Develop
JRE → Run
JVM → Execute
```

---

## Stack

```text
STACK
│
├── Method Calls
├── Stack Frames
├── Method Parameters
├── Local Variables
├── Local Object References
└── Execution / Return Information
```

Properties:

```text
One Stack Per Thread
Thread Specific
Frames Removed When Methods Return
```

---

## Heap

```text
HEAP
│
├── Objects
├── Arrays
├── Instance Data
└── Shared Application State
```

Properties:

```text
Shared Between Application Threads
Managed by Garbage Collection
Objects Become Eligible for GC When Unreachable
```

---

## Object Creation

For:

```java
Person person = new Person();
```

remember:

```text
STACK                         HEAP

person ───────────────────► Person Object
(reference)                  (actual object)
```

---

## Method Calls

For:

```text
main()
  ↓
methodA()
  ↓
methodB()
```

the conceptual stack is:

```text
methodB()
methodA()
main()
```

When `methodB()` returns:

```text
methodA()
main()
```

When `methodA()` returns:

```text
main()
```

This is:

```text
LIFO
Last In, First Out
```

---

## Threads

```text
Main Thread
    ↓
Own Stack

Worker Thread
    ↓
Own Stack

Another Thread
    ↓
Own Stack

All of them
    ↓
Shared Heap
```

---

## Which Thread Runs the Method?

```text
Thread A
   │
   └──► method()
          ↓
       Runs on Thread A
```

If another thread invokes the same method:

```text
Thread B
   │
   └──► method()
          ↓
       Runs on Thread B
```

The method runs on the thread that invokes it.

---

## Plain Java

```java
public static void main(String[] args) {
    methodA();
    methodB();
}
```

Without another thread:

```text
methodA() → Main Thread
methodB() → Main Thread
```

---

## New Thread

```java
Thread t = new Thread(() -> {
    methodA();
});

t.start();
```

Conceptually:

```text
Main Thread
    └── main()

Worker Thread
    └── run()
         └── methodA()
```

---

## Framework Application

```text
HTTP Request
     ↓
Framework / Server Worker Thread
     ↓
Controller Method
     ↓
Service Method
     ↓
Repository Method
```

Your code does not need to explicitly create the worker thread for this to happen.

---

## Errors

### StackOverflowError

```text
Too many stack frames
        ↓
Usually deep/infinite recursion
        ↓
StackOverflowError
```

Example:

```java
void test() {
    test();
}
```

---

### OutOfMemoryError

```text
JVM cannot satisfy memory allocation
        ↓
One possible case: heap exhausted
        ↓
OutOfMemoryError: Java heap space
```

Example:

```java
List<String> list = new ArrayList<>();

while (true) {
    list.add("Java");
}
```

---

# One Final Mental Model

If you remember only one diagram, remember this:

```text
                         JVM
                          │
          ┌───────────────┼────────────────┐
          │               │                │
          ▼               ▼                ▼
     Main Thread      Worker Thread     Other Thread
          │               │                │
          ▼               ▼                ▼
      Own Stack       Own Stack         Own Stack
          │               │                │
          └───────────────┼────────────────┘
                          │
                          ▼
                    Shared Heap
                          │
              ┌───────────┼───────────┐
              ▼           ▼           ▼
           Object       Array       Object
```

And for an individual object:

```text
Current Thread Stack
        │
        │ local reference
        ▼
      ┌───────────────┐
      │ Person Object │
      │   name        │
      │   age         │
      └───────────────┘
             │
             ▼
            Heap
```

The complete chain is:

```text
JDK
 ↓
Compile Java source
 ↓
Bytecode
 ↓
JVM
 ↓
Threads execute the bytecode
 ↓
Each thread has its own stack
 ↓
Method calls create stack frames
 ↓
Local execution data belongs to those frames
 ↓
Objects and arrays are generally allocated in the heap
 ↓
The heap is shared between application threads
 ↓
Shared mutable objects can create concurrency problems
 ↓
Unreachable heap objects become eligible for Garbage Collection
```

If you can explain this chain clearly, you can connect **JDK/JRE/JVM, bytecode execution, stack frames, heap objects, Garbage Collection, pass-by-value, threads, race conditions, `StackOverflowError`, and `OutOfMemoryError`** instead of treating them as separate interview questions.
